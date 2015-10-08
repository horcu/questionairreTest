package com.horcu.apps.peez.model.nfl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacz on 10/7/2015.
 */
public class GeneralInfo {
    @SerializedName("leagueName")
    @Expose
    private String leagueName;
    @SerializedName("conferences")
    @Expose
    private List<Conference> conferences = new ArrayList<Conference>();

    /**
     * @return The leagueName
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     * @param leagueName The leagueName
     */
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public GeneralInfo withLeagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    /**
     * @return The conferences
     */
    public List<Conference> getConferences() {
        return conferences;
    }

    /**
     * @param conferences The conferences
     */
    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }

    public GeneralInfo withConferences(List<Conference> conferences) {
        this.conferences = conferences;
        return this;
    }

}
