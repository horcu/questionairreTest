package com.horcu.apps.peez.custom;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.TileApi;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    private ArrayList<Tile> gameTileList = new ArrayList<>();
    private ArrayList<LetterImageView> gameReadyViews = new ArrayList<>();

    TileApi tileApi = Api.BuildTileApiService();
    static final List<Integer> piecesRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    static final String[] tileNames = new String[] {"GF","GH","BA","MT","MO"};
    private Context context;

    public void setTilePieceMap(Map<String, Integer> tilePieceMap) {
        this.tilePieceMap = tilePieceMap;
    }

    public Map<String, Integer> getTilePieceMap() {
        return tilePieceMap;
    }

    public TilePieceGenerator(Context ctx){
        context = ctx;

        //use this default map unless the other constructor is used that allows you to pass in
        //a specific map

        Map<String,Integer> defaultMap = new HashMap<>( );
        defaultMap.put("GF", 3);
        defaultMap.put("GH", 4);
        defaultMap.put("BA", 5);
        defaultMap.put("MT", 6);
        defaultMap.put("MO", 18);

        setTilePieceMap(defaultMap);

        setTileList(buildTileList());
    }

    public TilePieceGenerator(Map<String, Integer> tilePieceMap){

        setTilePieceMap(tilePieceMap);
        setTileList(buildTileList());

    }

    private ArrayList<Tile> buildTileList() {

        //TODO remove - for testing only

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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();

//        InputStream raw =  context.getResources().openRawResource(R.raw.tiles);
//        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
       Tile[] tiles = gson.fromJson(jsonString, new TypeToken<Tile[]>() {}.getType());

        for (Tile t: tiles)
        {
            try {
                tileApi.inserttile(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TODO - end remove

        ArrayList<Tile> tlist = null;
        try {
            TileApi.List tiles2 = tileApi.list();
            tlist  = new ArrayList<>();
            tlist = new Gson().fromJson(tiles2.toString(), new TypeToken<List<Tile>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
       // return tlist;
    }

    public void setTileList(ArrayList<Tile> tileList) {
        this.tileList = tileList;
    }

    //    \\Psuedo//
//
//    prerequisite:
//            1. Make a map with the List name as the key and a value representing the amount of tiles possible for this list as the value [GFList, 3 ; BAList, 5 etc...]
//            2. get the list of tiles from the datastore and cache it

//            for each spot [0-35]
//            for spot 0
//    a  select randomly from a sea of number [1,2,3,4,5]  => each number represents a type of piece {1 reps piece aliased as GF}
//    b     number is 1
//    c	      set tile one to use a GF piece
//    d		      reduce GFList value by one [GFList.value --]
//    e			      check for lists with at least one item left. {If the list is empty then no more tiles can use that piece}
//    f				        if list is empty remove number representing list [1]
//    repeat step a without this number in the list [2,3,4,5]

    //Write code here

    //generate the appropriate pieces for each of the 36 tiles

   public static ArrayList<LetterImageView> GenersteTileIdentities(ArrayList<LetterImageView> gridTiles){
       Random r = new Random();

       ArrayList<Tile> tempTileList = (ArrayList<Tile>) tileList.clone();

       //all lists for MO, GF, BA, MT,GH
       //MO
       List<Tile> MOTiles = new ArrayList<>();

       int mo_count = tilePieceMap.get("MO");
       for(int i = 0; i< tilePieceMap.get("MO"); i++)
       {
           int idx = r.nextInt(tempTileList.size());
           Tile chosenTile = tempTileList.get(idx);
           if(chosenTile.getSpot() == idx)
           {
               chosenTile.setPiece("MO");
               MOTiles.add(i,chosenTile);
               tempTileList.remove(idx);
           }
       }

       List<Tile> GFTiles = new ArrayList<>();

       for(int i = 0; i< tilePieceMap.get("GF"); i++)
       {
           int idx = r.nextInt(tempTileList.size());
           Tile chosenTile = tempTileList.get(idx);
           if(chosenTile.getSpot() == idx)
           {
               chosenTile.setPiece("GF");
               GFTiles.add(i,chosenTile);
               tempTileList.remove(idx);
           }
       }
       List<Tile> BATiles = new ArrayList<>();

       for(int i = 0; i< tilePieceMap.get("BA"); i++)
       {
           int idx = r.nextInt(tempTileList.size());
           Tile chosenTile = tempTileList.get(idx);
           if(chosenTile.getSpot() == idx)
           {
               chosenTile.setPiece("BA");
               BATiles.add(i,chosenTile);
               tempTileList.remove(idx);
           }
       }

       List<Tile> MTTiles = new ArrayList<>();

       for(int i = 0; i< tilePieceMap.get("MT"); i++)
       {
           int idx = r.nextInt(tempTileList.size());
           Tile chosenTile = tempTileList.get(idx);
           if(chosenTile.getSpot() == idx)
           {
               chosenTile.setPiece("MT");
               MTTiles.add(i,chosenTile);
               tempTileList.remove(idx);
           }
       }

       List<Tile> GHTiles = new ArrayList<>();

       for(int i = 0; i< tilePieceMap.get("GH"); i++)
       {
           int idx = r.nextInt(tempTileList.size());
           Tile chosenTile = tempTileList.get(idx);
           if(chosenTile.getSpot() == idx)
           {
               chosenTile.setPiece("GH");
               GHTiles.add(i,chosenTile);
               tempTileList.remove(idx);
           }
       }

       List<Tile> masterList = new ArrayList<>();
       masterList.addAll(MTTiles);
       masterList.addAll(MOTiles);
       masterList.addAll(GHTiles);
       masterList.addAll(BATiles);
       masterList.addAll(GFTiles);

       Collections.shuffle(masterList);

       for(int i =0; i < gridTiles.size(); i++)
       {
          gridTiles.get(i).setTile(masterList.get(i));
       }

       return gridTiles;
   }



    //a. BuildNumbersList() - this is the list of numbers to choose from randomly to determine the type of piece to occupy the given tile

    //b.	BuildMap() - this will build the map that holds the list name as the key and its corresponding amount of spaces to occupy as the value

    //c. GetRandomNumber() - Choose number randomly when given the specific numbers in range

    //g. RemoveOneFromListValue() - When passed the list it will reduce the amount of remaining spots to occupy by one

    //e. CheckEnsureListsHaveItems() - Checks to ensure that each list in the map has at least one space left to occupy

    //f. RemoveListNumberFromOptions() - if the value in the map for amy list is 0 then remove that item from the map
}
