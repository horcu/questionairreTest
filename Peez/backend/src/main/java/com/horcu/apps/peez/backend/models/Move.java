package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Horatio on 3/28/2016.
 */
@Entity
public class Move {

    @Id
    private String moveId;

    private String gameId;
    private String boardId;
    private String moveFrom;
    private String moveTo;
    private String DateTime;
    private String playedBy;
    private String receivedBy;
    private String moveType;

    public Move(String moveId, String gameId, String boardId, String moveFrom, String moveTo, String dateTime, String playedBy, String receivedBy, String moveType) {
        this.moveId = moveId;
        this.gameId = gameId;
        this.boardId = boardId;
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        DateTime = dateTime;
        this.playedBy = playedBy;
        this.receivedBy = receivedBy;
        this.moveType = moveType;
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

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getMoveId() {
        return moveId;
    }

    public void setMoveId(String moveId) {
        this.moveId = moveId;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(String playedBy) {
        this.playedBy = playedBy;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }
}
