package com.attendance.util;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConnection
{
    private static BasicDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");
            }
            props.load(input);

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(props.getProperty("db.driver"));
            dataSource.setUrl(props.getProperty("db.url"));
            dataSource.setUsername(props.getProperty("db.username"));
            dataSource.setPassword(props.getProperty("db.password"));

            // Connection pool settings
            dataSource.setInitialSize(Integer.parseInt(props.getProperty("db.pool.initialSize", "5")));
            dataSource.setMaxTotal(Integer.parseInt(props.getProperty("db.pool.maxTotal", "20")));
            dataSource.setMaxIdle(Integer.parseInt(props.getProperty("db.pool.maxIdle", "10")));
            dataSource.setMinIdle(Integer.parseInt(props.getProperty("db.pool.minIdle", "5")));
            dataSource.setMaxWaitMillis(Long.parseLong(props.getProperty("db.pool.maxWaitMillis", "10000").trim()));


        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

