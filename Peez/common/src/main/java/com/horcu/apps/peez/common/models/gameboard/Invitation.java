package com.horcu.apps.peez.common.models.gameboard;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by hacz on 2/11/2016.
 */
@Entity
public class Invitation {

    @Id
    @Index
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
