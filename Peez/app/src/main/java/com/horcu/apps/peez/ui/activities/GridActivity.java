package com.horcu.apps.peez.ui.activities;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import com.horcu.apps.peez.custom.LetterImageView;
import com.horcu.apps.peez.custom.TilePieceGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        AutoFitGridLayout gameBoard = (AutoFitGridLayout)findViewById(R.id.gameboard_grid);

        ViewTreeObserver observer = (ViewTreeObserver)gameBoard.getViewTreeObserver();

        ArrayList<LetterImageView> views = new ArrayList<>();

         for (int i=0; i < 36; i++)
         {
             LetterImageView view = (LetterImageView) gameBoard.getChildAt(i);
             view.setOnClickListener(this);
             view.setOnLongClickListener(this);
             views.add(i, view);
         }


        ArrayList<LetterImageView> returnedViews = new TilePieceGenerator(this).GenersteTileIdentities(views);

    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v,((
                LetterImageView)v)
                .getTile()
                .getPiece() +  " at spot " + ((LetterImageView)v).getTile().getSpot(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View v) {
        Snackbar.make(v,((
                LetterImageView)v)
                .getTile()
                .getName() +  " at spot " + ((LetterImageView)v).getTile().getSpot(), Snackbar.LENGTH_LONG).show();

        return true;
    }
}
