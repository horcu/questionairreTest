package com.horcu.apps.peez.custom;
import android.content.Context;
import android.util.AttributeSet;

import com.horcu.apps.peez.R;

import com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile;
import com.horcu.apps.peez.backend_gameboard.gameApi.model.Game;
import com.horcu.apps.peez.chat.LeBubbleTitleTextView;
import com.horcu.apps.peez.custom.Gameboard.TileView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import at.markushi.ui.CircleButton;

/**
 * Created by hcummings on 2/2/2016.
 */
public class GameBuilder {

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

    private Context context;

    public void setTilePieceMap(Map<String, Integer> tilePieceMap) {
        this.tilePieceMap = tilePieceMap;
    }

    public Map<String, Integer> getTilePieceMap() {
        return tilePieceMap;
    }

    public GameBuilder(Context ctx){
        context = ctx;
        Map<String,Integer> defaultMap = new HashMap<>( );
        defaultMap.put("GF", 3);
        defaultMap.put("GH", 4);
        defaultMap.put("BA", 5);
        defaultMap.put("MT", 6);
        defaultMap.put("MO", 18);
    }


    public  ArrayList<TileView> BuildTilesForGame(AutoFitGridLayout grid){
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

            //new game tiles will then be populated
            for(int i =0; i < orderedTileList.size(); i++)
            {
                TileView tileHouse = gridTiles.get(i);
                Tile masterListTile = orderedTileList.get(i);
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

    public static Game CreateOrGetGameboard(String gameKey, Boolean newGame) {

        if(newGame)
            return BuildNewGame(gameKey);

        //TODO - get the game from the db or cache by its key
        Game game = GetGameFromDB(gameKey);
        if(game != null)
            return game;

        //TODO get from server
        game = GetGameFromServer(gameKey);
        if(game != null)
        return game;

       return null;
    }

    private static Game BuildNewGame(String gameKey) {
        return new Game();
    }

    private static Game GetGameFromServer(String gameKey) {
        return null;
    }

    private static Game GetGameFromDB(String gameKey) {
       // return new Game(); //TODO get from db
        return null;
    }

    public static UserImageView BuildBadge(Context ctx, AttributeSet attrs,  int position, String badgeImgSrc) {
        return new UserImageView(ctx,attrs);
    }

    public static CircleButton BuildPlayerPiece(Context context, AttributeSet attrs,int i, String playerPieceImgSrc) {
        return new CircleButton(context, attrs);
    }

    public static LeBubbleTitleTextView BuildBubble(Context context, AttributeSet attrs, int i, String tileInfo) {
        LeBubbleTitleTextView bubble = new LeBubbleTitleTextView(context, attrs);
        bubble.setTitle(tileInfo);
        bubble.setParentLocation(i);
        return bubble;
    }
}