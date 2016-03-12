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
    private final Player[] players;
    private String boardKey;
    private String inviteKey;
    private Player PlayerTurn;

    public Game(String gameId, Player[] players, String boardKey, String inviteKey, Player playerTurn) {

        this.gameId = gameId;
        this.players = players;
        this.boardKey = boardKey;
        this.inviteKey = inviteKey;

        PlayerTurn = playerTurn;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Player[] getPlayers() {
        return players;
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

    public Player getPlayerTurn() {
        return PlayerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        PlayerTurn = playerTurn;
    }
}
