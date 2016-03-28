package com.horcu.apps.peez.model;


import io.realm.RealmObject;
import io.realm.annotations.Required;

public class GameEntry extends RealmObject
{
    private  String id;
    private  String datetime;
    @Required
    private  String gameId;
    private  Boolean inProgress;

    public GameEntry(String datetime, String gameId, Boolean inProgress)
    {
        this.datetime = datetime;

        this.gameId = gameId;
        this.inProgress = inProgress;
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

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }
}
