package com.horcu.apps.peez.model;

import com.horcu.apps.peez.enums.MoveType;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Horatio on 3/27/2016.
 */
public class MoveEntry extends RealmObject {

    private String Id;
    @Required
    private String gameId;
    private String Sender;
    private String Receiver;
    private String timeStamp;
    private String moveType;

    private String MoveFromIndex;
    private String MoveToIndex;

    private Boolean Made;

    public MoveEntry(){}

    public MoveEntry(String gameId, String moveId, String senderEmail, String receiverEmail, String timeStamp, MoveType moveType, String moveFromIndex, String moveToIndex, Boolean made) {
        this.gameId = gameId;
        Sender = senderEmail;
        Receiver = receiverEmail;
        this.timeStamp = timeStamp;
        this.moveType = moveType.toString();
        MoveFromIndex = moveFromIndex;
        MoveToIndex = moveToIndex;
        this.Id = moveId;
        Made = made;
    }

    public Boolean getMade() {
        return Made;
    }

    public void setMade(Boolean made) {
        Made = made;
    }

    public String getMoveToIndex() {
        return MoveToIndex;
    }

    public void setMoveToIndex(String moveToIndex) {
        MoveToIndex = moveToIndex;
    }

    public String getMoveFromIndex() {
        return MoveFromIndex;
    }

    public void setMoveFromIndex(String moveFromIndex) {
        MoveFromIndex = moveFromIndex;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }
}
