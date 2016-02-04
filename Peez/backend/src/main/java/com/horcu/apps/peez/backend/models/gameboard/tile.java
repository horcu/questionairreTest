package com.horcu.apps.peez.backend.models.gameboard;

import com.google.appengine.api.datastore.Text;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Id;

import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
public class tile {

    @Id
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("spot")
    @Expose
    private String spot;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("piece")
    @Expose
    private String piece;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("finishLine")
    @Expose
    private String finishLine;
    @SerializedName("neighbours")
    @Expose
    private String neighbours;

    /**
     * No args constructor for use in serialization
     *
     */
    public tile() {
    }

    /**
     *
     * @param finishLine
     * @param name
     * @param owner
     * @param used
     * @param neighbours
     * @param piece
     */
    public tile(String name, String owner, String piece, String used, String finishLine, String neighbours) {
        this.name = name;
        this.owner = owner;
        this.piece = piece;
        this.used = used;
        this.finishLine = finishLine;
        this.neighbours = neighbours;
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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public tile withName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return
     * The name
     */
    public String getSpot() {
        return spot;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setSpot(String spot) {
        this.spot = spot;
    }

    public tile withSpot(String spot) {
        this.spot = spot;
        return this;
    }

    /**
     *
     * @return
     * The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public tile withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    /**
     *
     * @return
     * The piece
     */
    public String getPiece() {
        return piece;
    }

    /**
     *
     * @param piece
     * The piece
     */
    public void setPiece(String piece) {
        this.piece = piece;
    }

    public tile withPiece(String piece) {
        this.piece = piece;
        return this;
    }

    /**
     *
     * @return
     * The used
     */
    public String getUsed() {
        return used;
    }

    /**
     *
     * @param used
     * The used
     */
    public void setUsed(String used) {
        this.used = used;
    }

    public tile withUsed(String used) {
        this.used = used;
        return this;
    }

    /**
     *
     * @return
     * The finishLine
     */
    public String getFinishLine() {
        return finishLine;
    }

    /**
     *
     * @param finishLine
     * The finishLine
     */
    public void setFinishLine(String finishLine) {
        this.finishLine = finishLine;
    }

    public tile withFinishLine(String finishLine) {
        this.finishLine = finishLine;
        return this;
    }

    /**
     *
     * @return
     * The neighbours
     */
    public String getNeighbours() {
        return neighbours;
    }

    /**
     *
     * @param neighbours
     * The neighbours
     */
    public void setNeighbours(String neighbours) {
        this.neighbours = neighbours;
    }

    public tile withNeighbours(String neighbours) {
        this.neighbours = neighbours;
        return this;
    }
}