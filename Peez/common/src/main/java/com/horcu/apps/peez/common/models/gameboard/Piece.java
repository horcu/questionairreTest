package com.horcu.apps.peez.common.models.gameboard;


import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.annotation.Generated;

@Entity
@Generated("org.jsonschema2pojo")
public class Piece {

    @Id
    @Index
    @SerializedName("id")
    @Expose
    private String id;

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
    public Piece() {
    }

    /**
     *
     * @param icon
     * @param alias
     * @param color
     * @param description
     */
    public Piece(String description, String color, String icon, String alias) {
        this.description = description;
        this.color = color;
        this.icon = icon;
        this.alias = alias;
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

    public Piece withDescription(String description) {
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

    public Piece withColor(String color) {
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

    public Piece withIcon(String icon) {
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

    public Piece withAlias(String alias) {
        this.alias = alias;
        return this;
    }
}