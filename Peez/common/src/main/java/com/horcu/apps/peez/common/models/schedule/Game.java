package com.horcu.apps.peez.common.models.schedule;
import com.horcu.apps.peez.common.models.league.Venue;


/**
 * Created by hcummings on 10/9/2015.
 */

public class Game {

    private String id;


    private String scheduled;

    private String homeRotation;

    private String awayRotation;

    private String home;

    private String away;

    private String status;

    private Venue venue;

    private Broadcast broadcast;

    private Weather weather;

    /**
     * No args constructor for use in serialization
     *
     */
    public Game() {
    }

    /**
     *
     * @param homeRotation
     * @param id
     * @param away
     * @param home
     * @param scheduled
     * @param status
     * @param broadcast
     * @param weather
     * @param awayRotation
     * @param venue
     */
    public Game(String id, String scheduled, String homeRotation, String awayRotation, String home, String away, String status, Venue venue, Broadcast broadcast, Weather weather) {
        this.id = id;
        this.scheduled = scheduled;
        this.homeRotation = homeRotation;
        this.awayRotation = awayRotation;
        this.home = home;
        this.away = away;
        this.status = status;
        this.venue = venue;
        this.broadcast = broadcast;
        this.weather = weather;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Game withId(String id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The scheduled
     */
    public String getScheduled() {
        return scheduled;
    }

    /**
     *
     * @param scheduled
     * The scheduled
     */
    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public Game withScheduled(String scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    /**
     *
     * @return
     * The homeRotation
     */
    public String getHomeRotation() {
        return homeRotation;
    }

    /**
     *
     * @param homeRotation
     * The home_rotation
     */
    public void setHomeRotation(String homeRotation) {
        this.homeRotation = homeRotation;
    }

    public Game withHomeRotation(String homeRotation) {
        this.homeRotation = homeRotation;
        return this;
    }

    /**
     *
     * @return
     * The awayRotation
     */
    public String getAwayRotation() {
        return awayRotation;
    }

    /**
     *
     * @param awayRotation
     * The away_rotation
     */
    public void setAwayRotation(String awayRotation) {
        this.awayRotation = awayRotation;
    }

    public Game withAwayRotation(String awayRotation) {
        this.awayRotation = awayRotation;
        return this;
    }

    /**
     *
     * @return
     * The home
     */
    public String getHome() {
        return home;
    }

    /**
     *
     * @param home
     * The home
     */
    public void setHome(String home) {
        this.home = home;
    }

    public Game withHome(String home) {
        this.home = home;
        return this;
    }

    /**
     *
     * @return
     * The away
     */
    public String getAway() {
        return away;
    }

    /**
     *
     * @param away
     * The away
     */
    public void setAway(String away) {
        this.away = away;
    }

    public Game withAway(String away) {
        this.away = away;
        return this;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Game withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     *
     * @return
     * The venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     *
     * @param venue
     * The venue
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Game withVenue(Venue venue) {
        this.venue = venue;
        return this;
    }

    /**
     *
     * @return
     * The broadcast
     */
    public Broadcast getBroadcast() {
        return broadcast;
    }

    /**
     *
     * @param broadcast
     * The broadcast
     */
    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public Game withBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
        return this;
    }

    /**
     *
     * @return
     * The weather
     */
    public Weather getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     * The weather
     */
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Game withWeather(Weather weather) {
        this.weather = weather;
        return this;
    }

}
