package com.horcu.apps.peez.Dtos;

public class MMDto {
    private final String moveFrom;
    private final String moveTo;
    private final String message;
    private final String dateTime;
    private final String senderToken;
    private final String receiverToken;
    private final String senderUrl;
    private final int color;
    private final String collapseKey;

    public MMDto(String moveFrom, String moveTo, String message, String dateTime, String senderToken, String receiverToken, String senderUrl, int color, String collapseKey) {
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.message = message;
        this.dateTime = dateTime;
        this.senderToken = senderToken;
        this.receiverToken = receiverToken;
        this.senderUrl = senderUrl;
        this.color = color;
        this.collapseKey = collapseKey;
    }

    public String getMoveFrom() {
        return moveFrom;
    }

    public String getMoveTo() {
        return moveTo;
    }

    public String getMessage() {
        return message;
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
}
