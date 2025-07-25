package com.attendance.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsUtil {
    // Replace with your Twilio Account SID and Auth Token


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
