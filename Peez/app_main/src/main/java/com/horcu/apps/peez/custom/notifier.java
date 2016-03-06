package com.horcu.apps.peez.custom;//package com.horcu.apps.peez.custom;
//
//import android.app.PendingIntent;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.view.MainView;
import com.horcu.apps.peez.view.baseview;


import java.util.Date;

/**
 * Created by hcummings on 10/27/2015.
 */
public class notifier {

    public static void showNotification(String message, Context context, Class<MainView> goWhere, Uri soundUri, Bitmap bitmap) {
        try {
            //create the intent to send user to the main screen where the intent will be shown
            Intent go = new Intent(context, goWhere.getClass());

            //create the pending intent that wraps the "go to" intent
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1421, go, 0);

            //create and launch the notification
            NewBetNotification.notify(context, pendingIntent, message, 1, new Date().getTime() + 86400000, bitmap);

            //sounds the ringtone alarm
            Ringtone r = RingtoneManager.getRingtone(context, soundUri);
            r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
