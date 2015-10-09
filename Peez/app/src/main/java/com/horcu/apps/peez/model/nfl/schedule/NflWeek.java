package com.horcu.apps.peez.model.nfl.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcummings on 10/9/2015.
 */
public class NflWeek {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("number")
    @Expose
    private long number;
    @SerializedName("games")
    @Expose
    private List<Game> games = new ArrayList<Game>();

    /**
     * No args constructor for use in serialization
     *
     */
    public NflWeek() {
    }

    /**
     *
     * @param id
     * @param games
     * @param number
     */
    public NflWeek(String id, long number, List<Game> games) {
        this.id = id;
        this.number = number;
        this.games = games;
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

    public NflWeek withId(String id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The number
     */
    public long getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    public NflWeek withNumber(long number) {
        this.number = number;
        return this;
    }

    /**
     *
     * @return
     * The games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     *
     * @param games
     * The games
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    public NflWeek withGames(List<Game> games) {
        this.games = games;
        return this;
    }

}
