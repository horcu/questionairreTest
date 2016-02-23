//package com.horcu.apps.peez.ui.activities;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.BoolRes;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.ViewSwitcher;
//
//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidanimations.library.YoYo;
//import com.horcu.apps.peez.R;
//import com.horcu.apps.peez.common.models.gameboard.Tile;
//import com.horcu.apps.peez.custom.AutoFitGridLayout;
//import com.horcu.apps.peez.custom.OpponentView;
//import com.horcu.apps.peez.custom.PlayerView;
//import com.horcu.apps.peez.custom.TileHelper;
//import com.horcu.apps.peez.custom.TilePieceGenerator;
//import com.horcu.apps.peez.custom.TileView;
//
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.util.AbstractList;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//
//public class GridActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
//
//    private static int selectedViewBgColor = Color.WHITE;
//
//    TileView selectedView = null;
//    private AbstractList<TileView> playerViews = new ArrayList<>();;
//    private AbstractList<TileView> opponentViews = new ArrayList<>();
//    private ArrayList<TileView> gameboardViews = new ArrayList<>();
//
//    //sections
//    AutoFitGridLayout playerSection = null;
//    AutoFitGridLayout opponentSection = null;
//    AutoFitGridLayout gameBoard = null;
//    //end section
//
//    //switcher for the main views
//    ViewSwitcher switcher = null;
//    //end switcher
//
//    //private TextSurface textSurface;
//    RelativeLayout preGboard;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_grid);
//
//        switcher = (ViewSwitcher) findViewById(R.id.gameboard_switcher);
//        //This is the animated pre game stuff
//       // textSurface = findViewById(R.id.text_surface);
//
//        //generate the gameboard in the background
//        playerSection = (AutoFitGridLayout)findViewById(R.id.gameboard_grid_user);
//        opponentSection = (AutoFitGridLayout)findViewById(R.id.gameboard_grid_opponent);
//        gameBoard = (AutoFitGridLayout)findViewById(R.id.gameboard_grid);
//
//        //TODO - when this class is shown that means that the server sent a request to
//        //TODO - start the game
//        //TODO - check the intent for the game data aand pass it in as the list of tiles in the BuildGameBoardAsync method below
//        GBObject gbo = new GBObject(opponentSection,gameBoard, playerSection, this);
//        SetEvents(gbo);
//        BuildGameBoardAsync(gbo, new ArrayList<Tile>());
//
//        preGboard = (RelativeLayout)findViewById(R.id.pre_gameboard);
//        preGboard.postDelayed(new Runnable() {
//            @Override public void run() {
//                show();
//            }
//        }, 1000);
//
//    }
//
//    private void SetEvents(GBObject gbo) {
//        for (int i = 0; i < gbo.playerList.size(); i++) {
//
//            PlayerView view = gbo.playerList.get(i);
//            view.setOnClickListener(this);
//            view.setOnLongClickListener(this);
//            playerViews.add(i, view);
//        }
//
//        for (int i = 0; i < gbo.opponentList.size(); i++) {
//
//            OpponentView view = gbo.opponentList.get(i);
//            view.setOnClickListener(this);
//            view.setOnLongClickListener(this);
//            opponentViews.add(i, view);
//        }
//
//        for (int i = 0; i < gbo.gamePiecesList.size(); i++) {
//            TileView view = gbo.gamePiecesList.get(i);
//            view.setOnClickListener(this);
//            view.setOnLongClickListener(this);
//            gameboardViews.add(i, view);
//        }
//    }
//
//    private void show() {
////        textSurface.reset();
////
////        Text textDaai = su.levenetc.android.textsurface.TextBuilder
////                .create("Daai")
////                .setSize(64)
////                .setAlpha(0)
////                .setColor(Color.WHITE)
////                .setPosition(Paint.Align.CENTER).build();
////
////        textSurface.play(
////                new Sequential(
////                        Slide.showFrom(Side.TOP, textDaai, 500),
////                        Delay.duration(500),
////                        Alpha.hide(textDaai, 1500)
////                )
////        );
//        ((TextView)preGboard.findViewById(R.id.Intro_text)).setText("creating gameboard...");
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if(TileIsOccupied())
//        {
//            //this spot cannot be a destination
//            String owner = ((TileView) v).getTile().getOwner() != null ? ((TileView) v).getTile().getOwner() : "anonymous";
//        }
//        else
//        {
//            if (SelectedPlayerCanMoveHere(v)) {
//                TileView lView = (TileView) v;
//                YoYo.with(Techniques.Pulse).duration(1000).playOn(lView);
//
//                //set the selected view
//                UpdateSelectedView(lView);
//
//                //TODO refactor this !!
//                Snackbar snack = Snackbar.make(v, lView
//                        .getTile()
//                        .getPiece() + " who's neighbours are " + lView.getTile().getNeighbours() + " moved to  " + lView.getTile().getSpot(), Snackbar.LENGTH_LONG)
//                        .setActionTextColor(TileHelper.getColorForInnerPieceType(this,lView.getTile().getPiece()));
//
//                View snackView = snack.getView();
//                snackView.setBackgroundColor(Color.LTGRAY);
//                snack.show();
//
//                //set the user's id in the string below
//                //View.getTile().setOwner();
//
//            } else {
//                //The user cannot move here most likely because it isnt a neighbour or they dont qualify for this "un neighbourly" move hahaha
//
//                Snackbar snack = Snackbar.make(v,
//                         "You cannot move to " + ((TileView) v).getTile().getSpot() + " from there", Snackbar.LENGTH_LONG)
//                        .setActionTextColor(Color.CYAN);
//
//                View snackView = snack.getView();
//                snackView.setBackgroundColor(Color.LTGRAY);
//                snack.show();
//            }
//        }
//    }
//
//    private boolean TileIsOccupied() {
//
//        return false;
//    }
//
//    private boolean SelectedPlayerCanMoveHere(View v) {
//
//        return true;
//    }
//
//    @Override
//    public boolean onLongClick(View v) {
//
//        TileView lView = (TileView) v;
//
//        //set the selected view
//        UpdateSelectedView(lView);
//
//        Snackbar snack =   Snackbar.make(v, lView
//                .getTile()
//                .getPiece() +  " here are your neighbours " + lView.getTile().getNeighbours(), Snackbar.LENGTH_LONG)
//                .setActionTextColor(Color.CYAN);
//
//        View snackbarView = snack.getView();
//        snackbarView.setBackgroundColor(Color.LTGRAY);
//                snack.show();
//
//        //highlight the neighbours
//        List<String> neighbours = Arrays.asList(lView.getTile().getNeighbours());
//        ArrayList<TileView> vs = new ArrayList<>();
//
//        for(int i=0; i < gameboardViews.size(); i++)
//        {
//            String n = gameboardViews.get(i).getTile().getName();
//            if(neighbours.contains(n))
//                vs.add(i, gameboardViews.get(i));
//        }
//        highlightNeighbours(vs);
//
//        return true;
//    }
//
//    private void UpdateSelectedView(TileView lView) {
//        if(selectedView != null && selectedViewBgColor != 0)
//        selectedView.setBackgroundColor(selectedViewBgColor);
//
//        lView.setBackgroundColor(Color.WHITE);
//        selectedView = lView;
//
//        selectedViewBgColor = Color.WHITE;
//    }
//
//    private void highlightNeighbours(ArrayList<TileView> highlighted) {
//
//        for (int i = 0; i < highlighted.size(); i++) {
//
//            final TileView currentView = highlighted.get(i);
//            currentView.setBackgroundColor(Color.BLUE);
//            //add some sort of icon here temporarily .. then..
//            YoYo.with(Techniques.Pulse).duration(1500).playOn(currentView);
//
////            currentView.postDelayed(new Runnable() {
////                @Override public void run() {
////                    currentView.setBackgroundColor(Color.WHITE);
////                }
////            }, 1200);
//
//        }
//    }
//
//    private class GBObject {
//        private final AutoFitGridLayout opponentSection;
//        private final AutoFitGridLayout gameBoard;
//        private final AutoFitGridLayout playerSection;
//        private final Context context;
//        private ArrayList<OpponentView> opponentList;
//        private ArrayList<TileView> gamePiecesList;
//        private ArrayList<PlayerView> playerList;
//
//
//        public GBObject(AutoFitGridLayout opponentSection, AutoFitGridLayout gameBoard, AutoFitGridLayout playerSection, Context context) {
//
//            this.opponentSection = opponentSection;
//            this.gameBoard = gameBoard;
//            this.playerSection = playerSection;
//            this.context = context;
//
//            opponentList = new ArrayList<>();
//            playerList = new ArrayList<>();
//            gamePiecesList = new ArrayList<>();
//
//            for(int i=0; i < opponentSection.getChildCount(); i++){opponentList.add(i,(OpponentView) opponentSection.getChildAt(i));}
//            for(int i=0; i < gameBoard.getChildCount(); i++){gamePiecesList.add(i,(TileView) gameBoard.getChildAt(i));}
//            for(int i=0; i < playerSection.getChildCount(); i++){playerList.add(i,(PlayerView) playerSection.getChildAt(i));}
//
//        }
//    }
//
//    private void BuildGameBoardAsync(final GBObject obj, final ArrayList<Tile> tiles) {
//
//        new AsyncTask<Void, Void, Boolean>() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                try {
//                    //oh snap..
//                    if (gameboardViews == null){
//                    return null;
//                }
//                    //build up the list
//                  new TilePieceGenerator(obj.context)
//                          .GenerateTileIdentities(gameboardViews, tiles);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean result) {
//
//                //something bad must have happened
//                if(!result)
//                {
//                    ((TextView)preGboard.findViewById(R.id.Intro_text)).setText("something went wrong.. press back and then retry");
//                    return;
//                }
//                //switch to the gameboard slide elegantly
//                switcher.showNext();
//            }
//        }.execute();
//    }
//}
