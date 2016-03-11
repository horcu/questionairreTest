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

    }


}
