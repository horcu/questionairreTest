package com.horcu.apps.peez.model.nfl.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hacz on 10/10/2015.
 */
public class nflSeason {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("season")
    @Expose
    private String season;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("weeks")
    @Expose
    private List<NflWeek> weeks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NflWeek> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<NflWeek> weeks) {
        this.weeks = weeks;
    }
}
