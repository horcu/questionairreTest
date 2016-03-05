package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

/**
 * Created by Horatio on 2/11/2016.
 */

@Entity
public class Board {
    private String boardKey;

    @Id
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
