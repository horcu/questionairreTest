package com.horcu.apps.peez.common.models.league;
/**
 * Created by hacz on 10/7/2015.
 */

public class Team {

    private String id;

    private String name;

    private String market;

    private TeamColors teamColors;
    private Venue venue;

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

    public Team withId(String id) {
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

    public Team withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The market
     */
    public String getMarket() {
        return market;
    }

    /**
     * @param market The market
     */
    public void setMarket(String market) {
        this.market = market;
    }

    public Team withMarket(String market) {
        this.market = market;
        return this;
    }

    /**
     * @return The teamColors
     */
    public TeamColors getTeamColors() {
        return teamColors;
    }

    /**
     * @param teamColors The team_colors
     */
    public void setTeamColors(TeamColors teamColors) {
        this.teamColors = teamColors;
    }

    public Team withTeamColors(TeamColors teamColors) {
        this.teamColors = teamColors;
        return this;
    }

    /**
     * @return The venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * @param venue The venue
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Team withVenue(Venue venue) {
        this.venue = venue;
        return this;
    }

}