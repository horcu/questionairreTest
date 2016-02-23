package com.horcu.apps.peez.common.models.gameboard;


public class Tile {

    private String id;
    private String name;
    private String spot;
    private String owner;
    private String piece;
    private String used;
    private String finishLine;
    private String neighbours;

    /**
     * No args constructor for use in serialization
     *
     */
    public Tile() {
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
    public Tile(String name, String owner, String piece, String used, String finishLine, String neighbours) {
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
     * @param name
     * The name
     */
    public void setId(String id) {
        this.id = id;
    }

    public Tile withId(String id) {
        this.id = id;
        return this;
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

    public Tile withName(String name) {
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

    public Tile withSpot(String spot) {
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

    public Tile withOwner(String owner) {
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

    public Tile withPiece(String piece) {
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

    public Tile withUsed(String used) {
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

    public Tile withFinishLine(String finishLine) {
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

    public Tile withNeighbours(String neighbours) {
        this.neighbours = neighbours;
        return this;
    }
}