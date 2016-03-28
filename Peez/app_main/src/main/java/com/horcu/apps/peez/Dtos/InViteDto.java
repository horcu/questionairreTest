package com.horcu.apps.peez.Dtos;

import android.graphics.Color;

import com.horcu.apps.peez.backend.models.moveApi.model.Move;

public class InviteDto {
    private final String message;
    private final String dateTime;
    private final String senderToken;
    private final String receiverToken;
    private final String senderUrl;
    private final String invitationTken;
    private String move;
    private final int color;
    private final String collapseKey;

    public InviteDto(String message, String dateTime, String senderToken, String receiverToken,String invitationToken, String senderUrl, String move, int color, String collapseKey) {
        this.message = message;
        this.dateTime = dateTime;
        this.move = move;
        this.invitationTken = invitationToken;
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

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getInvitationToken() {
        return invitationTken;
    }
}
