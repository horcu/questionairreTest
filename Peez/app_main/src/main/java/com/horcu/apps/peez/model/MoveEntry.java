package com.horcu.apps.peez.model;

import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.custom.Gameboard.GameDialogHelper;
import com.horcu.apps.peez.enums.MoveType;

/**
 * Created by Horatio on 3/27/2016.
 */
public class MoveEntry {
    private String Sender;
    private String Receiver;
    private String timeStamp;
    private MoveType moveType;

    private int MoveFromIndex;
    private int MoveToIndex;

    private Boolean Made;

    public MoveEntry(String senderEmail, String receiverEmail, String timeStamp, MoveType moveType, int moveFromIndex, int moveToIndex, Boolean made) {
        Sender = senderEmail;
        Receiver = receiverEmail;
        this.timeStamp = timeStamp;
        this.moveType = moveType;
        MoveFromIndex = moveFromIndex;
        MoveToIndex = moveToIndex;
        Made = made;
    }

    public Boolean getMade() {
        return Made;
    }

    public void setMade(Boolean made) {
        Made = made;
    }

    public int getMoveToIndex() {
        return MoveToIndex;
    }

    public void setMoveToIndex(int moveToIndex) {
        MoveToIndex = moveToIndex;
    }

    public int getMoveFromIndex() {
        return MoveFromIndex;
    }

    public void setMoveFromIndex(int moveFromIndex) {
        MoveFromIndex = moveFromIndex;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
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
