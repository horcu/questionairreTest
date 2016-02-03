package com.horcu.apps.peez.backend.models.gameboard;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class piece {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("alias")
    @Expose
    private String alias;

    /**
     * No args constructor for use in serialization
     *
     */
    public piece() {
    }

    /**
     *
     * @param icon
     * @param alias
     * @param color
     * @param description
     */
    public piece(String description, String color, String icon, String alias) {
        this.description = description;
        this.color = color;
        this.icon = icon;
        this.alias = alias;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public piece withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    public piece withColor(String color) {
        this.color = color;
        return this;
    }

    /**
     *
     * @return
     * The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public piece withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     *
     * @return
     * The alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     *
     * @param alias
     * The alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public piece withAlias(String alias) {
        this.alias = alias;
        return this;
    }
}