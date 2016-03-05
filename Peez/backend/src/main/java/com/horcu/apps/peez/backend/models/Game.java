package com.horcu.apps.peez.backend.models;

/**
 * Created by hacz on 2/11/2016.
 */
public class Game {

    private String gameId;

    private String [] userEmails;
    private final com.horcu.apps.peez.backend.models.User[] emails;
    private String boardKey;
    private String inviteKey;

    public Game(com.horcu.apps.peez.backend.models.User[] emails, String boardKey, String inviteKey) {

        this.emails = emails;
        this.boardKey = boardKey;
        this.inviteKey = inviteKey;
    }
}
