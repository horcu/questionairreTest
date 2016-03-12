package com.horcu.apps.peez.backend.models;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by hacz on 9/29/2015.
 */

@Entity
public class Player { //extends Player - extend the google gsm player object??

    @Id
    public String email;
    public String userName;
    public String alias;
    public double cash;
    public String imageUri;
    public String phone;
    public String joined;
    public Long rank;
    public String token;
    private  Boolean currentlyOnLine;
    private  Boolean currentlyPlaying;
    private  Boolean InGameWithUser;
    private  Boolean CanBeMessaged;

    public Player() {
    }

    public String getUserName() {
        return userName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Boolean getCurrentlyOnLine() {
        return currentlyOnLine;
    }

    public void setCurrentlyOnLine(Boolean currentlyOnLine) {
        this.currentlyOnLine = currentlyOnLine;
    }

    public Boolean getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(Boolean currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public Boolean getInGameWithUser() {
        return InGameWithUser;
    }

    public void setInGameWithUser(Boolean inGameWithUser) {
        InGameWithUser = inGameWithUser;
    }

    public Boolean getCanBeMessaged() {
        return CanBeMessaged;
    }

    public void setCanBeMessaged(Boolean canBeMessaged) {
        CanBeMessaged = canBeMessaged;
    }
}
