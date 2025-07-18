package com.attendance.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Uncomment the @WebFilter annotation if not using web.xml for filter mapping
// @WebFilter(urlPatterns = {"/admin/*", "/teacher/*", "/student/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Don't create session if it doesn't exist

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Define public paths that do not require authentication
        // This should include login pages, static resources (css, js, images), etc.
        // Ensure these paths match your application structure and web.xml mappings
        boolean isPublicPath = path.equals("/index.jsp") ||
                path.equals("/login.jsp") ||
                path.equals("/admin/login") ||
                path.equals("/teacher/login") ||
                path.equals("/student/login") ||
                path.equals("/admin/dashboard")||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/");
// Login servlet path

        // Check if the user is authenticated
        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);

        // If the path is protected and the user is not authenticated, redirect to login page
        // The login servlet mapping in web.xml handles specific role logins (/admin/login, etc.)
        if (!isPublicPath && !isAuthenticated) {
            System.out.println("AuthenticationFilter: Unauthorized access to " + path + ", redirecting to login.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp"); // Redirect to generic login page
        } else if (isAuthenticated && (path.startsWith("/admin/") || path.startsWith("/teacher/") || path.startsWith("/student/"))) {
            // Optional: Role-based authorization check for authenticated users
            // Assuming the user object in session has a getRole() method
            Object user = session.getAttribute("user");
            String requiredRole = null;

            if (path.startsWith("/admin/")) {
                requiredRole = "admin";
            } else if (path.startsWith("/teacher/")) {
                requiredRole = "teacher";
            } else if (path.startsWith("/student/")) {
                requiredRole = "student";
            }

            if (requiredRole != null) {
                // This part assumes a 'role' property/method on your user object
                // You might need to adjust this based on your actual User/Admin/Teacher/Student models
                String userRole = null;
                try {
                    // Attempt to get role using reflection or a common interface/base class
                    // This is a placeholder. Replace with your actual user object role retrieval.
                    if (user instanceof com.attendance.model.Admin) userRole = "admin";
                    else if (user instanceof com.attendance.model.Teacher) userRole = "teacher";
                    else if (user instanceof com.attendance.model.Student) userRole = "student";

                    if (userRole == null || !userRole.equals(requiredRole)) {
                        System.out.println("AuthenticationFilter: User role " + userRole + " not authorized for " + path + ", redirecting to error/unauthorized.");
                        // Redirect to an unauthorized access error page or the dashboard
                        if(userRole != null) {
                            httpResponse.sendRedirect(httpRequest.getContextPath() + "/" + userRole + "/dashboard.jsp");
                        } else {
                            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp"); // Generic error
                        }
                        return; // Stop the filter chain
                    }
                } catch (Exception e) {
                    // Log the error and potentially redirect to a generic error page
                    System.err.println("Error during role check in AuthenticationFilter: " + e.getMessage());
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp"); // Generic error
                    return;
                }
            }
            // If authenticated and authorized (or no specific role required for this path), proceed
            chain.doFilter(request, response);
        } else {
            // If authenticated or path is public, proceed
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }

    // Helper method to check if a path requires authentication
    // This is an alternative to checking path directly in doFilter
    // private boolean requiresAuthentication(String path) {
    //     // Implement logic to check if path is in protected areas
    //     return path.startsWith("/admin/") || path.startsWith("/teacher/") || path.startsWith("/student/");
    // }
}

