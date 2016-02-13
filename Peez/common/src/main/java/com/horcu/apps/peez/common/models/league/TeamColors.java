package com.horcu.apps.peez.common.models.league;


import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

/**
 * Created by hacz on 10/7/2015.
 */
public class TeamColors {
    @SerializedName("primary_color")
    @Expose
    private String primaryColor;
    @SerializedName("secondary_color")
    @Expose
    private String secondaryColor;
    @SerializedName("tertiary_color")
    @Expose
    private String tertiaryColor;

    /**
     * @return The primaryColor
     */
    public String getPrimaryColor() {
        return primaryColor;
    }

    /**
     * @param primaryColor The primary_color
     */
    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public TeamColors withPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    /**
     * @return The secondaryColor
     */
    public String getSecondaryColor() {
        return secondaryColor;
    }

    /**
     * @param secondaryColor The secondary_color
     */
    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public TeamColors withSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    /**
     * @return The tertiaryColor
     */
    public String getTertiaryColor() {
        return tertiaryColor;
    }

    /**
     * @param tertiaryColor The tertiary_color
     */
    public void setTertiaryColor(String tertiaryColor) {
        this.tertiaryColor = tertiaryColor;
    }

    public TeamColors withTertiaryColor(String tertiaryColor) {
        this.tertiaryColor = tertiaryColor;
        return this;
    }

}
