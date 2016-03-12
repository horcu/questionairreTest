package com.horcu.apps.peez.model;

import com.horcu.apps.peez.common.utilities.consts;

public class MessageEntry
{
    private  String id;
    private  String datetime;
    private  String message;
    private  String status;
    private String imgUrl;

    public MessageEntry(String datetime, String message, String messageId, String imgUrl)
    {
        this.datetime = datetime;
        this.message = message;
        this.imgUrl = imgUrl;
        this.status = consts.STATUS_NOT_SENT;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
