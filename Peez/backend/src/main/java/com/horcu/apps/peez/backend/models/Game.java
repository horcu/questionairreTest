package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


/**
 * Created by Horatio on 2/11/2016.
 */
@Entity
public class Game {

    @Id
    private String gameId;
    private final String sender;
    private final String receiver;
    private String boardKey;
    private String inviteKey;
    private String PlayerTurn;

    public Game(String gameId, String senderEmail, String receiverEmail, String boardKey, String inviteKey, String playerTurn) {

        this.gameId = gameId;
        this.boardKey = boardKey;
        this.inviteKey = inviteKey;
        this.sender = senderEmail;
        this.receiver = receiverEmail;
        this.PlayerTurn = playerTurn;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getBoardKey() {
        return boardKey;
    }

    public void setBoardKey(String boardKey) {
        this.boardKey = boardKey;
    }

    public String getInviteKey() {
        return inviteKey;
    }

    public void setInviteKey(String inviteKey) {
        this.inviteKey = inviteKey;
    }


    public String getPlayerTurn() {
        return PlayerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        PlayerTurn = playerTurn;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
