package com.horcu.apps.peez.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.horcu.apps.peez.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by hacz on 2/5/2016.
 */
public class TileHelper {

    public static int getColorForInnerPieceType(Context ctx, String piece) {
        String[] colorsArr = ctx.getResources().getStringArray(R.array.piece_colors);

        switch (piece)
        {
            case "MO":
            {
                return Color.parseColor(colorsArr[0]);
            }
            case "MT":
            {
                return Color.parseColor(colorsArr[1]);
            }
            case "GF":
            {
                return Color.parseColor(colorsArr[2]);
            }
            case "BA":
            {
                return Color.parseColor(colorsArr[3]);
            }
            case "GH":
            {
                return Color.parseColor(colorsArr[4]);
            }
        }
        return 0;
    }

    public static int randomColor(Context ctx) {
        Random random = new Random();
        String[] colorsArr = ctx.getResources().getStringArray(R.array.colors);
        return Color.parseColor(colorsArr[random.nextInt(colorsArr.length)]);
    }

    @NonNull
    public  ArrayList<TileView> GetNeighbours(ArrayList<TileView> staticViews, TileView lView) {
        String[] ns = lView.getNeighbours();
        ArrayList<String> nArrayList = new ArrayList<>(Arrays.asList(ns));
        ArrayList<TileView> highlighted = new ArrayList<>();

        for (int i = 0; i < staticViews.size(); i++) {
            TileView currentView = staticViews.get(i);

            for(int n =0; n < nArrayList.size(); n ++) {

                if (nArrayList.get(n).equals(currentView.getName()))
                    highlighted.add(currentView);
            }
        }
        return highlighted;
    }

    /*This method will attempt to use the amount of squares in the grid to determine where
    * the view is in the grid and therefore determine its neighbours*/
    public static String[] getNeighbours(int g, int size) {

        double rowCount = Math.sqrt(size);

        return null;
    }
}
