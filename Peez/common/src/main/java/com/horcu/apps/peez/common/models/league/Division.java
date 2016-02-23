package com.horcu.apps.peez.common.models.league;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacz on 10/7/2015.
 */
public class Division {
    private String id;

    private String name;

    private List<Team> teams = new ArrayList<Team>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Division withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Division withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams The teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Division withTeams(List<Team> teams) {
        this.teams = teams;
        return this;
    }

}
