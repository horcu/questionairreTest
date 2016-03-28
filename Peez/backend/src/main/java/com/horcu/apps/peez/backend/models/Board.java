package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


import misc.KeyGenerator;

/**
 * Created by Horatio on 2/11/2016.
 */

@Entity
public class Board {

    @Id
    private String boardKey;
    private String jsonTileDefinition;

    public Board(){
        this.boardKey = KeyGenerator.GenerateGameboardKey();
    }

    public Board(String jsonTileDefinition) {
        this.jsonTileDefinition = jsonTileDefinition;
        this.boardKey = KeyGenerator.GenerateGameboardKey();
    }

    public String getJsonTileDefinition() {
        return jsonTileDefinition;
    }

    public void setJsonTileDefinition(String jsonTileDefinition) {
        this.jsonTileDefinition = jsonTileDefinition;
    }

    public String getBoardKey() {
        return boardKey;
    }
}
