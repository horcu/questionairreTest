package com.horcu.apps.peez.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.notifier;
import com.horcu.apps.peez.gcm.SmsMessage;
import com.horcu.apps.peez.view.MainView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Service used for receiving GCM messages. When a message is received this service will log it.
 */
public class GcmService extends GcmListenerService {

    private LoggingService.Logger logger;
    private PowerManager.WakeLock wl;
    private SharedPreferences settings;


    public GcmService() {
        logger = new LoggingService.Logger(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "peez"); //TODO find the way to get this done pre v18 or upgrade to min being v21
        wl.acquire();

        String message = data.getString("message");

        Gson gson = new Gson();
        Object type = null;
        JSONObject msgObject = gson.fromJson(message, JSONObject.class);
        try {
             type = msgObject.get("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(type == null)
            return;

        if(type.equals(LoggingService.MESSAGE_TYPE_MSG))
        {
            SmsMessage sms = gson.fromJson(message,SmsMessage.class);

            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(this)
                        .load(sms.getSenderUrl())
                        .get();

                Log.d("GCM message:", "image received");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bitmap == null)
            {
                bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
            }
            sendNotification(bitmap, sms);
            logMessage(sms.getMessage(), bitmap);
            Log.d("Received GCM Message: ", data.toString());
        }

        //TODO implement these messages as well
//        else if(basemessage instanceof InvitationMessage)
//        {
//
//        }
//        else if(basemessage instanceof MoveMessage)
//        {
//
//        }
//        else if(basemessage instanceof ReminderMessage)
//        {
//
//        } //TTODTODO implement these methods as well
    }

    private boolean ISentThisMessage(String senderId) {

       String sId = settings.getString(consts.REG_ID,"");
        return senderId.equals(sId);
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

    private void sendNotification(Bitmap bitmap, SmsMessage message) {
        Intent intent = new Intent(this, MainView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(LoggingService.MESSAGE_TYPE, message.getType());
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notifier.showNotification(message.getMessage(), getApplicationContext(),MainView.class, soundUri, bitmap);
    }
}
