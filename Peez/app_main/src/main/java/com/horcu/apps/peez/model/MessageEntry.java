package com.horcu.apps.peez.model;

import com.horcu.apps.peez.common.utilities.consts;

public class MessageEntry extends com.horcu.apps.peez.common.models.User
{
    private  String datetime;
    private  String message;
    private  String status;

    public MessageEntry(String datetime, String message)
    {
        this.datetime = datetime;
        this.message = message;
        this.status = consts.STATUS_NOT_SENT;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
