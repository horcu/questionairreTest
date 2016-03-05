package com.horcu.apps.peez.custom;
import android.content.Context;

import com.horcu.apps.peez.R;

import com.horcu.apps.peez.backend.models.gameboard.tileApi.TileApi;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    //static map for positioning views correctly in the grid
    private static Map<Integer, String> PosAndNeighboursList = new HashMap<>(36);

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
    }


    public  ArrayList<TileView> GenerateTileIdentities(ArrayList<TileView> gridTiles, ArrayList<Tile> masterList){
        try {
            //setup the grids first
            for(int g=0; g < gridTiles.size(); g++)
            {
                TileView gtile = gridTiles.get(g);
                gtile.setSpot(g);
                gtile.setName(tileList.get(g).getName());
                gtile.setMode("default");
                gtile.setTile(tileList.get(g));
                String[] neighbours = gtile.getTile().getNeighbours().split(",");
                gtile.setNeighbours(neighbours);
            }

            //TODO - move below code into class that will handle when the intent comes in
            //new game tiles populate will then be populated
            for(int i =0; i < masterList.size(); i++)
            {
                TileView tileHouse = gridTiles.get(i);
                Tile masterListTile = masterList.get(i);
                tileHouse.getTile().setName(masterListTile.getName());
                tileHouse.getTile().setId(String.valueOf(i));
                tileHouse.getTile().setPiece(masterListTile.getPiece());
                int icon = getIconForPieceType(masterListTile.getPiece());
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

}