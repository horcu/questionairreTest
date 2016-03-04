package com.horcu.apps.peez.model;

import io.realm.RealmObject;

public class Player extends RealmObject
{
    private  String name;
    private  String token;
    private  String rank;
    private  Boolean currentlyOnLine;
    private  Boolean currentlyPlaying;
    private  Boolean InGameWithUser;
    private  Boolean CanBeMessaged;
    private String imageUrl;

    public Player(){}
    public Player(String name, String rank)
    {
        this.name = name;
        this.rank = rank;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
