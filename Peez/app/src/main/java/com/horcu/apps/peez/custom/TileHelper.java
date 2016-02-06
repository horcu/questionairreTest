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
    public static ArrayList<TileView> GetNeighbours(ArrayList<TileView> staticViews, TileView lView) {
        String ns = lView.getTile().getNeighbours();
        ArrayList<String> nArrayList = new ArrayList<>(Arrays.asList(ns.split(",")));
        ArrayList<TileView> highlighted = new ArrayList<>();

        for (int i = 0; i < staticViews.size(); i++) {
            TileView currentView = staticViews.get(i);

            for(int n =0; n < nArrayList.size(); n ++) {

                String neighboursName = nArrayList.get(n);
                String name = currentView.getTile().getName();
                if (neighboursName.equals(name))
                    highlighted.add(currentView);

            }
        }
        return highlighted;
    }

}
