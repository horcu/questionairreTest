package com.horcu.apps.peez.custom;

import android.support.v4.content.res.TypedArrayUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.TileApi;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static br.com.zbra.androidlinq.Linq.stream;

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

    Map<String,Integer> tilePieceMap = new LinkedHashMap<>();
    private ArrayList<Tile> tileList = new ArrayList<>();
    private ArrayList<Tile> gameTileList = new ArrayList<>();

    TileApi tileApi = Api.BuildTileApiService();
    static final List<Integer> piecesRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    static final String[] tileNames = new String[] {"GF","GH","BA","MT","MO"};

    public void setTilePieceMap(Map<String, Integer> tilePieceMap) {
        this.tilePieceMap = tilePieceMap;
    }

    public Map<String, Integer> getTilePieceMap() {
        return tilePieceMap;
    }

    public TilePieceGenerator(){

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
        ArrayList<Tile> tlist = null;
        try {
            TileApi.List tiles = tileApi.list();
            tlist  = new ArrayList<>();
            tlist = new Gson().fromJson(tiles.toString(), new TypeToken<List<Tile>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tlist;
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

   private void GenersteTileIdentities(){
       Random r = new Random();

        for(int i =0; i < tileList.size() ; i++)
        {
            //get the random number representing the index of the number to select from the available range values
            int idx = r.nextInt(piecesRange.size());
            int selected = piecesRange.get(idx);

            //use that number to get the tile type (piece) that should be used here
            Iterator iterator = tilePieceMap.entrySet().iterator();
            int n = 0;
             String alias = "";
            int allowed = 0;
            while(iterator.hasNext()){
                if(selected == n)
                {
                     alias = (String) iterator.next();
                     allowed = tilePieceMap.get(alias);
                    break;
                }
                n++;
            }

            final String finalAlias = alias;

            //now use the alias and allowed to determine what tile to add

            //check if the allowed amount has been exceeded
            int left = tilePieceMap.get(alias).intValue();

            if(left == 0)//cannot use anymore of this piece type
                return;

            //you can assign more
            //find the tile in the tileList from the server using the linq like syntax eg. where tile.name == "blah blah"
            Tile newTile = stream(tileList).where(t-> t.getName() == finalAlias);

            //decorate the tile accordingly
            //newTile.set

            // Add the newly assigned tile to the gameTileList to send off to the server to represent this game instance tile layout
             gameTileList.add(i,newTile);

            if(left == 1) //this is the last one remove the type from the tilePieceMap and the pieces range
            {
             tileList.remove(alias);
             piecesRange.remove(selected);
            }
        }
    }



    //a. BuildNumbersList() - this is the list of numbers to choose from randomly to determine the type of piece to occupy the given tile

    //b.	BuildMap() - this will build the map that holds the list name as the key and its corresponding amount of spaces to occupy as the value

    //c. GetRandomNumber() - Choose number randomly when given the specific numbers in range

    //g. RemoveOneFromListValue() - When passed the list it will reduce the amount of remaining spots to occupy by one

    //e. CheckEnsureListsHaveItems() - Checks to ensure that each list in the map has at least one space left to occupy

    //f. RemoveListNumberFromOptions() - if the value in the map for amy list is 0 then remove that item from the map
}
