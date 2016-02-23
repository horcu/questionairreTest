package com.horcu.apps.peez.common.models.gameboard;

import java.util.ArrayList;

/**
 * Created by hacz on 2/11/2016.
 */

public class Board {
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
