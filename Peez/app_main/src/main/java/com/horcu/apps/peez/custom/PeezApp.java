package com.horcu.apps.peez.custom;

import android.app.Application;
import android.content.SharedPreferences;


import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;

import java.util.ArrayList;
import java.util.Random;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;


/**
 * Created by Horatio on 2/17/2016.
 */
public class PeezApp extends Application {
    private SharedPreferences settings;

    @Override
    public void onCreate() {
        super.onCreate();

        CustomActivityOnCrash.setLaunchErrorActivityWhenInBackground(true);
        CustomActivityOnCrash.setShowErrorDetails(true);
        CustomActivityOnCrash.setEnableAppRestart(true);
        //Install CustomActivityOnCrash
        CustomActivityOnCrash.install(this);

        settings = getSharedPreferences("Peez",0);

        ArrayList<Integer> colors = GetColors();
        int color = colors.get(new Random(0).nextInt(colors.size() - 1));

        settings.edit().putInt(consts.FAV_COLOR, color).apply();
        //Now initialize your error handlers as normal
        //i.e., ACRA.init(this);
        //or Crashlytics.start(this);
    }

    private  ArrayList<Integer> GetColors() {
        ArrayList<Integer> colorArrayList = new ArrayList<>();
        colorArrayList.add(getResources().getColor(R.color.red));
        colorArrayList.add(getResources().getColor(R.color.black));
        colorArrayList.add(getResources().getColor(android.R.color.holo_blue_dark));
        colorArrayList.add(getResources().getColor(R.color.colorAccent));
        colorArrayList.add(getResources().getColor(android.R.color.holo_orange_dark));
        colorArrayList.add(getResources().getColor(android.R.color.holo_green_dark));
        return colorArrayList;
    }
}
