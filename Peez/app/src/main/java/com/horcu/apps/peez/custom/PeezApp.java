package com.horcu.apps.peez.custom;

import android.app.Application;

import com.horcu.apps.peez.ui.activities.MainActivity;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;


/**
 * Created by Horatio on 2/17/2016.
 */
public class PeezApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CustomActivityOnCrash.setLaunchErrorActivityWhenInBackground(true);
        CustomActivityOnCrash.setShowErrorDetails(true);
        CustomActivityOnCrash.setEnableAppRestart(true);
        CustomActivityOnCrash.setRestartActivityClass(MainActivity.class);
        //Install CustomActivityOnCrash
        CustomActivityOnCrash.install(this);

        //Now initialize your error handlers as normal
        //i.e., ACRA.init(this);
        //or Crashlytics.start(this);
    }
}
