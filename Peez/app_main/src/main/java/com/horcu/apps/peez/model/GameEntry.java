package com.horcu.apps.peez.model;


import io.realm.RealmObject;

public class GameEntry extends RealmObject
{
    private  String id;
    private  String datetime;
    private  String gameId;
    private Boolean inprogress;

    public GameEntry(String datetime, String gameId, Boolean inProgress)
    {
        this.datetime = datetime;

        this.gameId = gameId;
        this.inprogress = inProgress;
    }

    public GameEntry() {

    }

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Boolean getInprogress() {
        return inprogress;
    }

    public void setInprogress(Boolean inprogress) {
        this.inprogress = inprogress;
    }
}
