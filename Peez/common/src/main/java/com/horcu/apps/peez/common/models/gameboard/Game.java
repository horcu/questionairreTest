package com.horcu.apps.peez.common.models.gameboard;

import com.horcu.apps.peez.common.models.User;

/**
 * Created by hacz on 2/11/2016.
 */
public class Game {

    private String gameId;

    private String [] userEmails;
    private final User[] emails;
    private String boardKey;
    private String inviteKey;

    public Game(User[] emails, String boardKey, String inviteKey) {

        this.emails = emails;
        this.boardKey = boardKey;
        this.inviteKey = inviteKey;
    }
}
