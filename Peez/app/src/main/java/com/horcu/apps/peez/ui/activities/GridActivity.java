package com.horcu.apps.peez.ui.activities;

import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import com.horcu.apps.peez.custom.OpponentView;
import com.horcu.apps.peez.custom.PlayerView;
import com.horcu.apps.peez.custom.TileHelper;
import com.horcu.apps.peez.custom.TileView;
import com.horcu.apps.peez.custom.TilePieceGenerator;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

public class GridActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static int selectedViewBgColor = Color.WHITE;

    TileView selectedView = null;
    private AbstractList<TileView> playerViews = new ArrayList<>();;
    private AbstractList<TileView> opponentViews = new ArrayList<>();
    private ArrayList<TileView> gameboardViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);


        AutoFitGridLayout playerSection = (AutoFitGridLayout)findViewById(R.id.gameboard_grid_user);
        for (int i=0; i < playerSection.getChildCount(); i++) {

            PlayerView view = (PlayerView) playerSection.getChildAt(i);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            playerViews.add(i, view);

        }

        AutoFitGridLayout opponentSection = (AutoFitGridLayout)findViewById(R.id.gameboard_grid_opponent);
        for (int i=0; i < opponentSection.getChildCount(); i++) {

            OpponentView view = (OpponentView) opponentSection.getChildAt(i);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            opponentViews.add(i, view);
        }

        AutoFitGridLayout gameBoard = (AutoFitGridLayout)findViewById(R.id.gameboard_grid);


         for (int i=0; i < gameBoard.getChildCount(); i++)
         {
                 TileView view = (TileView) gameBoard.getChildAt(i);
                 view.setOnClickListener(this);
                 view.setOnLongClickListener(this);
                 gameboardViews.add(i, view);
         }


        ArrayList<TileView> ImageViewTiles = new TilePieceGenerator(getApplicationContext()).GenerateTileIdentities(gameboardViews);
    }


    @Override
    public void onClick(View v) {

        if(TileIsOccupied())
        {
            //this spot cannot be a destination
            String owner = ((TileView) v).getTile().getOwner() != null ? ((TileView) v).getTile().getOwner() : "anonymous";
        }
        else
        {
            if (SelectedPlayerCanMoveHere(v)) {
                TileView lView = (TileView) v;
                YoYo.with(Techniques.Pulse).duration(1000).playOn(lView);

                //set the selected view
                UpdateSelectedView(lView);

                //TODO refactor this !!
                Snackbar snack = Snackbar.make(v, lView
                        .getTile()
                        .getPiece() + " who's neighbours are " + lView.getTile().getNeighbours() + " moved to  " + lView.getTile().getSpot(), Snackbar.LENGTH_LONG)
                        .setActionTextColor(TileHelper.getColorForInnerPieceType(this,lView.getTile().getPiece()));

                View snackView = snack.getView();
                snackView.setBackgroundColor(Color.LTGRAY);
                snack.show();

                //set the user's id in the string below
                //View.getTile().setOwner();

            } else {
                //The user cannot move here most likely because it isnt a neighbour or they dont qualify for this "un neighbourly" move hahaha

                Snackbar snack = Snackbar.make(v,
                         "You cannot move to " + ((TileView) v).getTile().getSpot() + " from there", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.CYAN);

                View snackView = snack.getView();
                snackView.setBackgroundColor(Color.LTGRAY);
                snack.show();
            }
        }
    }

    private boolean TileIsOccupied() {

        return false;
    }

    private boolean SelectedPlayerCanMoveHere(View v) {
        //check and see if selectedView and this view are neighbours
        //assume its a LetterImageView or else... kabooooosh!!!!!!!!!! haha

        if(selectedView == null)
           return false;

        ArrayList<TileView> listOne = TileHelper.GetNeighbours(gameboardViews, selectedView);
        ArrayList<TileView> listTwo = TileHelper.GetNeighbours(gameboardViews,(TileView) v);
        boolean areNeighbours = false;

        for(int i = 0; i < listOne.size(); i++ )
        {
            for(int n = 0; n < listTwo.size(); n++)
            {
                if(i == n) {
                    areNeighbours = true;
                    return areNeighbours;
                }
            }
        }

        return areNeighbours;
    }

    @Override
    public boolean onLongClick(View v) {

        TileView lView = (TileView) v;

        //set the selected view
        UpdateSelectedView(lView);

        Snackbar snack =   Snackbar.make(v, lView
                .getTile()
                .getName() +  " here are your neighbours " + ((TileView)v).getTile().getSpot(), Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.CYAN);

        View snackbarView = snack.getView();
        snackbarView.setBackgroundColor(Color.LTGRAY);
                snack.show();

        //highlight the neighbours
        ArrayList<TileView> neighbours = TileHelper.GetNeighbours(gameboardViews, lView);
        highlightNeighbours(neighbours);

        return true;
    }

    private void UpdateSelectedView(TileView lView) {
        if(selectedView != null && selectedViewBgColor != 0)
        selectedView.setBackgroundColor(selectedViewBgColor);

        lView.setBackgroundColor(Color.WHITE);
        selectedView = lView;

        selectedViewBgColor = Color.WHITE;
    }

    private void highlightNeighbours(ArrayList<TileView> highlighted) {

        for (int i = 0; i < highlighted.size(); i++) {

            TileView currentView = highlighted.get(i);
            currentView.setBackgroundColor(Color.YELLOW);
            //add some sort of icon here temporarily .. then..
            YoYo.with(Techniques.Pulse).duration(1000).playOn(currentView);
            currentView.setBackgroundColor(Color.WHITE);
        }
    }



}
