package com.horcu.apps.peez.custom;//package com.horcu.apps.peez.custom;
//
//import android.app.PendingIntent;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.view.baseview;


import java.util.Date;

/**
 * Created by hcummings on 10/27/2015.
 */
public class notifier {

    public static void showNotification(Intent intent, Context context, Class<baseview> goWhere) {
        try {
            Bundle bundle = intent.getExtras();
            // String message = bundle.getString("message");
            String key = bundle.getString("collapse_key");

            //get message from intent
            String message = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);

            //create the intent to send user to the main screen where the intent will be shown
            Intent go = new Intent(context, goWhere.getClass());

            //create the pending intent that wraps the "go to" intent
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1421, go, 0);

            //create and launch the notification
            NewBetNotification.notify(context, pendingIntent, message, 1, new Date().getTime() + 86400000);

            //sounds the ringtone alarm
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
