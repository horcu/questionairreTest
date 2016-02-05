package com.horcu.apps.peez.custom;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.TileApi;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by hcummings on 2/2/2016.
 */
public class TilePieceGenerator {

//    piece GF - 3 total
//
//    piece BA - 5 total
//
//    piece MT - 6 total
//
//    piece GH - 4 total
//
//    piece MO - 18 total

    private static Map<String,Integer> tilePieceMap = new LinkedHashMap<>();
    private static ArrayList<Tile> tileList = new ArrayList<>();

    TileApi tileApi = Api.BuildTileApiService();
    private Context context;

    public void setTilePieceMap(Map<String, Integer> tilePieceMap) {
        this.tilePieceMap = tilePieceMap;
    }

    public Map<String, Integer> getTilePieceMap() {
        return tilePieceMap;
    }

    public TilePieceGenerator(Context ctx){
        context = ctx;
        Map<String,Integer> defaultMap = new HashMap<>( );
        defaultMap.put("GF", 3);
        defaultMap.put("GH", 4);
        defaultMap.put("BA", 5);
        defaultMap.put("MT", 6);
        defaultMap.put("MO", 18);

        SetTilePiecesMapAndTileList(defaultMap);
    }

    public TilePieceGenerator(Map<String, Integer> tilePieceMap){
        SetTilePiecesMapAndTileList(tilePieceMap);
    }

    public void SetTilePiecesMapAndTileList(Map<String, Integer> map){

        setTilePieceMap(map);
        setTileList(buildTileList());
    }

    private ArrayList<Tile> buildTileList() {

        //TODO remove - for testing only
        List<Tile> tiles = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(context.getResources().getIdentifier("tiles",
                "raw", context.getPackageName()));
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();

                String jsonString = writer.toString();
                Gson gson = new Gson();
                tiles = gson.fromJson(jsonString, new TypeToken<List<Tile>>() {
                }.getType());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int t = 0; t < tiles.size(); t++) {
            try {
                Tile tile = tiles.get(t);
                tile.setId(String.valueOf(t));
                TileApi.Inserttile inserted = tileApi.inserttile(tile);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //TODO - end remove

        ArrayList<Tile> tlist = null;
        try {
            TileApi.List tiles2 = tileApi.list();
            if(tiles2.size() > 0) {
                tlist = new ArrayList<>();
                tlist = new Gson().fromJson(tiles2.toString(), new TypeToken<List<Tile>>() {
                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //return null;
        }
        return (ArrayList<Tile>) tiles;
    }

    public void setTileList(ArrayList<Tile> tileList) {
        this.tileList = tileList;
    }

    public  ArrayList<TileView> GenerateTileIdentities(ArrayList<TileView> gridTiles){
        try {
            Random r = new Random();
            List<Tile> masterList = new ArrayList<>();
            ArrayList<Tile> tempTileList = (ArrayList<Tile>) tileList.clone();

            for(int i =0; i < tilePieceMap.size(); i++)
            {
                String key = (new ArrayList<>(tilePieceMap.keySet())).get(i);
                int occurence = tilePieceMap.get(key);

                for (int x=0; x < occurence; x++)
                {
                    int idx = r.nextInt(tempTileList.size());
                    Tile chosenTile = tempTileList.get(idx);
                    chosenTile.setPiece(key);
                    masterList.add(i,chosenTile);
                    tempTileList.remove(idx);
                }
            }

            Collections.shuffle(masterList);

            SetTileAttributes(masterList);

            for(int i =0; i < masterList.size(); i++)
            {
                TileView tileHouse = gridTiles.get(i);
                Tile masterListTile = masterList.get(i);
                tileHouse.setTile(masterListTile);
                tileHouse.setSpot(Integer.parseInt(masterListTile.getSpot()));
                tileHouse.setBackgroundColor(Color.WHITE);
                tileHouse.setTextColor(Color.LTGRAY);
                int icon = getIconForPieceType(masterListTile.getPiece());
                tileHouse.getTile().setNeighbours(masterListTile.getNeighbours());

                Picasso.with(context).load(icon).into(tileHouse);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return gridTiles;
    }

    private int getIconForPieceType(String piece) {
        switch (piece)
        {
            case "MO":
            {
                return R.drawable.ic_mo;
            }
            case "MT":
            {
                return R.drawable.ic_mt;
            }
            case "GF":
            {
                return R.drawable.ic_gf;
            }
            case "BA":
            {
                return R.drawable.ic_ba;
            }
            case "GH":
            {
                return R.drawable.ic_gh;
            }
        }
        return 0;
    }



    private static void SetTileAttributes(List<Tile> masterList) {

        for(int i=0; i < masterList.size(); i++)
        {
            Tile tile =  masterList.get(i);
            Tile tempTile = tileList.get(i);
            tile.setNeighbours(tempTile.getNeighbours());
            tile.setSpot(String.valueOf(i));
            tile.setName(tempTile.getName());
        }

    }


    //a. BuildNumbersList() - this is the list of numbers to choose from randomly to determine the type of piece to occupy the given tile

    //b.	BuildMap() - this will build the map that holds the list name as the key and its corresponding amount of spaces to occupy as the value

    //c. GetRandomNumber() - Choose number randomly when given the specific numbers in range

    //g. RemoveOneFromListValue() - When passed the list it will reduce the amount of remaining spots to occupy by one

    //e. CheckEnsureListsHaveItems() - Checks to ensure that each list in the map has at least one space left to occupy

    //f. RemoveListNumberFromOptions() - if the value in the map for amy list is 0 then remove that item from the map
}