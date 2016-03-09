package com.horcu.apps.peez.gcm;

import com.horcu.apps.peez.service.LoggingService;

import io.realm.RealmObject;

/**
 * Created by Horatio on 3/6/2016.
 */
public class MoveMessage extends RealmObject {

    private String type;
    private String moveTo;
    private String moveFrom;
    private String message;
    private String senderToken;
    private String receiverToken;
    private String dateTime;
    private String senderUrl;

    public MoveMessage(){}

    public MoveMessage(String moveFrom,String moveTo,String message, String dateTime, String senderToken, String receiverToken, String senderUrl){
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.message = message;
        this.dateTime = dateTime;
        this.senderToken = senderToken;
        this.receiverToken = receiverToken;
        this.senderUrl = senderUrl;

        type = LoggingService.MESSAGE_TYPE_MOVE;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(String moveTo) {
        this.moveTo = moveTo;
    }

    public String getMoveFrom() {
        return moveFrom;
    }

    public void setMoveFrom(String moveFrom) {
        this.moveFrom = moveFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getReceiverToken() {
        return receiverToken;
    }

    public void setReceiverToken(String receiverToken) {
        this.receiverToken = receiverToken;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSenderUrl() {
        return senderUrl;
    }

    public void setSenderUrl(String senderUrl) {
        this.senderUrl = senderUrl;
    }
}
