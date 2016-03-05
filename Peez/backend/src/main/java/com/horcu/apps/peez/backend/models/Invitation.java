package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by hacz on 2/11/2016.
 */

@Entity
public class Invitation {

    @Id
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
