package com.horcu.apps.peez.Dtos;

import android.graphics.Color;

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
        this.senderToken = senderToken == null ? "" : senderToken;
        this.receiverToken = receiverToken == null ? "" : receiverToken;
        this.senderUrl = senderUrl == null ? "" : senderUrl;
        this.color = color == 0 ? Color.LTGRAY : color;
        this.collapseKey = collapseKey == null ? "987654321" : collapseKey;
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
