package com.horcu.apps.peez.model;

import com.horcu.apps.peez.common.utilities.consts;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MessageEntry extends RealmObject
{
    @Required
    private  String id;
    private  String datetime;
    private  String message;
    private  String status;
    private String from;
    private String to;

    public MessageEntry(){}

    public MessageEntry(String messageId, String to,String from, String datetime, String message)
    {
        this.datetime = datetime;
        this.message = message;
        this.from = from;
        this.to = to;
        this.status = Status.NOT_SENT.toString();
        this.id = messageId;
    }

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Status getStatus() {
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public enum Status{
        SEEN,NOT_SEEN, SENT,NOT_SENT
    }
}
