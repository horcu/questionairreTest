package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


/**
 * Created by hacz on 2/11/2016.
 */

@Entity
public class Invitation {

    @Id
    private String invitationKey;
    private GameInvite invite;
    private Player[] users;
    private Player maker;

    public Invitation(GameInvite invite, Player[] users, Player player){
        this.invite = invite;
        this.users = users;
        this.maker = player;
    }

    public Invitation Send(){
        return this;
    }

    public GameInvite getInvite() {
        return invite;
    }

    public void setInvite(GameInvite invite) {
        this.invite = invite;
    }

    public Player[] getUsers() {
        return users;
    }

    public void setUsers(Player[] users) {
        this.users = users;
    }

    public Player getMaker() {
        return maker;
    }

    public void setMaker(Player maker) {
        this.maker = maker;
    }
}
