package com.horcu.apps.peez.common.models.gameboard;


import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.horcu.apps.peez.common.models.User;

/**
 * Created by hacz on 2/11/2016.
 */
@Entity
public class Game {

    @Id
    @Index
    @SerializedName("id")
    @Expose
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
