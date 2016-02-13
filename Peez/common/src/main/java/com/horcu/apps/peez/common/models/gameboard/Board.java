package com.horcu.apps.peez.common.models.gameboard;



import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;

/**
 * Created by hacz on 2/11/2016.
 */
@Entity
public class Board {

    @Id
    @Index
    @SerializedName("id")
    @Expose
    private String boardKey;

    private String gameKey;
    private ArrayList<Tile> tiles;

    public Board(String gameKey, ArrayList<Tile> tiles) {
        this.gameKey = gameKey;
        this.tiles = tiles;
    }

    public String getGameKey() {
        return gameKey;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
