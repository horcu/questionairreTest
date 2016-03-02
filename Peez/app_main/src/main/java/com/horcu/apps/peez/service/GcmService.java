package com.horcu.apps.peez.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.view.main_view;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Service used for receiving GCM messages. When a message is received this service will log it.
 */
public class GcmService extends GcmListenerService {

    private LoggingService.Logger logger;
    private PowerManager.WakeLock wl;

    public GcmService() {
        logger = new LoggingService.Logger(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "peez");
        wl.acquire();

        String message = data.getString("message");
       // String collapsedKey = data.getString("collapsed_key");
       // String senderId = data.getString("sender");
        String senderImgUrl = "https://storage.googleapis.com/ballrz/images/users/IMAG0311%5B1%5D.jpg";// data.getString("sender");


        //TODO get the image from the message coming in instead of the below hardcoded test
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(this)
                    .load(senderImgUrl)
                    .get();

            Log.d("GCM message:", "image received");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap == null)
        {
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
            sendNotification(bitmap, message);
        }
        sendNotification(bitmap, message);
        logMessage(message, bitmap);
        Log.d("Received GCM Message: ", data.toString());
    }

    @Override
    public void onDeletedMessages() {
        logMessage("Deleted messages on server", null);
    }

    @Override
    public void onMessageSent(String msgId) {
        logMessage("Upstream message sent. Id=" + msgId, null);
    }

    @Override
    public void onSendError(String msgId, String error) {
        logMessage("Upstream message send error. Id=" + msgId + ", error" + error, null);
    }


    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void logMessage(String msg, Bitmap bitmap) {

        logger.log(Log.INFO, msg, "error");
    }

    private void sendNotification(Bitmap bitmap, String message) {
        Intent intent = new Intent(this, main_view.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
