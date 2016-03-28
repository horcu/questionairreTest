package com.horcu.apps.peez.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;

/**
 * Created by Horatio on 3/27/2016.
 */
public class Utils {

    private static SharedPreferences settings;

    @NonNull
    public static int GetFavoriteColor(Context context) {
        if(settings == null)
            settings = context.getSharedPreferences(consts.PEEZ, 0);

        int savedColorIndex = settings.getInt(consts.FAV_COLOR, 0) != 0
                ? settings.getInt(consts.FAV_COLOR, 0)
                : Color.parseColor("#a5a5c9");

        return context.getResources().getIntArray(R.array.Colors)[savedColorIndex];
    }
}
