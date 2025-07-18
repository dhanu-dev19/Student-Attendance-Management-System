package com.attendance.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsUtil {
    // Replace with your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "ACf09596b3d409a312f813335d82298b4c";
    public static final String AUTH_TOKEN = "aeeff239bd8611497eeda2b52f25cf8a";
    public static final String FROM_PHONE = "+15734923988"; // e.g., "+1234567890"

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendSms(String toPhone, String messageBody) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toPhone),
                new com.twilio.type.PhoneNumber(FROM_PHONE),
                messageBody
        ).create();
        System.out.println("SMS sent: " + message.getSid());
    }
}
