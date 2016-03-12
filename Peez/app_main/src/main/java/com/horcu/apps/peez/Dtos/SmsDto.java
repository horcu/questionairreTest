package com.horcu.apps.peez.Dtos;

import com.horcu.apps.peez.gcm.message.Message;

/**
 * Created by Horatio on 3/11/2016.
 */
public class SmsDto{

    private final String from;
    private final String to;
    private final String message;
    private final String dateTime;
    private final String senderUrl;

    public SmsDto(String from, String to, String message, String dateTime, String senderUrl) {

        this.from = from;
        this.to = to;
        this.message = message;
        this.dateTime = dateTime;
        this.senderUrl = senderUrl;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSenderUrl() {
        return senderUrl;
    }
}
