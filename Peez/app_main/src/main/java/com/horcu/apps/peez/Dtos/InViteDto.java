package com.horcu.apps.peez.Dtos;

public class InviteDto {
    private final String message;
    private final String dateTime;
    private final String senderToken;
    private final String receiverToken;
    private final String senderUrl;
    private final int color;
    private final String collapseKey;

    public InviteDto(String message, String dateTime, String senderToken, String receiverToken, String senderUrl, int color, String collapseKey) {
        this.message = message;
        this.dateTime = dateTime;
        this.senderToken = senderToken;
        this.receiverToken = receiverToken;
        this.senderUrl = senderUrl;
        this.color = color;
        this.collapseKey = collapseKey;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public String getReceiverToken() {
        return receiverToken;
    }

    public String getSenderUrl() {
        return senderUrl;
    }

    public int getColor() {
        return color;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public String getMessage() {
        return message;
    }
}
