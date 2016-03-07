package com.horcu.apps.peez.gcm;

import com.horcu.apps.peez.service.LoggingService;

import io.realm.RealmObject;

/**
 * Created by hcummings on 3/3/2016.
 */
public class SmsMessage extends RealmObject {

    private String from;
    private String to;
    private String message;
    private String dateTime;
    private String type;
    private String senderUrl;

    public SmsMessage(String from, String to, String message, String dateTime, String senderUrl){
        this.from = from;
        this.to = to;
        this.message = message;
        this.dateTime = dateTime;
        this.senderUrl = senderUrl;
        this.type = LoggingService.MESSAGE_TYPE_MSG;
    }

    public SmsMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderUrl() {
        return senderUrl;
    }

    public void setSenderUrl(String senderUrl) {
        this.senderUrl = senderUrl;
    }


}
