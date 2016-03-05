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
