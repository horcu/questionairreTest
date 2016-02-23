package com.horcu.apps.peez.custom;

/**
 * Created by Horatio on 2/16/2016.
 */
public class IM {

    private String senderName;
    private String senderId;
    private String message;

    public IM(String senderName, String senderId, String message) {
        this.senderName = senderName;
        this.senderId = senderId;
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
