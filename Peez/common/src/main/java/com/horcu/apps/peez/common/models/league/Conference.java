package com.horcu.apps.peez.common.models.league;


import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Conference {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("divisions")
    @Expose
    private List<Division> divisions = new ArrayList<Division>();

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

    public Conference withId(String id) {
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

    public Conference withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The divisions
     */
    public List<Division> getDivisions() {
        return divisions;
    }

    /**
     * @param divisions The divisions
     */
    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    public Conference withDivisions(List<Division> divisions) {
        this.divisions = divisions;
        return this;
    }

}