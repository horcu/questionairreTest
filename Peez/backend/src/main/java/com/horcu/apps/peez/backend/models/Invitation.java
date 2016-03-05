package com.horcu.apps.peez.backend.models;

/**
 * Created by hacz on 2/11/2016.
 */

public class Invitation {

    private GameInvite invite;
    private final String[] users;
    private final String senderId;

    public Invitation(GameInvite invite, String [] users, String senderId){
        this.invite = invite;
        this.users = users;
        this.senderId = senderId;
    }

    public Invitation Send(){

        return this;
    }
}
