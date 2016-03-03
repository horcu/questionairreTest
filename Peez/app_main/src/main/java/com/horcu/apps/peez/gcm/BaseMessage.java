package com.horcu.apps.peez.gcm;

import io.realm.RealmObject;

/**
 * Created by hcummings on 3/3/2016.
 */
public class BaseMessage extends RealmObject {

    private String from;
    private String to;
    private String message;
    private String dateTime;

    public BaseMessage(String from, String to, String message, String dateTime){
        this.from = from;
        this.to = to;
        this.message = message;
        this.dateTime = dateTime;
    }

    public BaseMessage() {
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
}
