package com.horcu.apps.peez.custom;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.BaseMessage;
import com.horcu.apps.peez.gcm.GcmServerSideSender;
import com.horcu.apps.peez.gcm.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;

import java.io.IOException;

/**
 * Created by Horatio on 2/29/2016.
 */
public class MessageSender {

    private static final String LOG_TAG = "MessageSender";
    final GoogleCloudMessaging gcm;
    SharedPreferences settings;
    Context context;
    private LoggingService.Logger logger;
    private SenderCollection senders;

    public MessageSender(Context ctx, LoggingService.Logger logger, SenderCollection senders){
         context = ctx;
        this.logger = logger;
        this.senders = senders;
        gcm = GoogleCloudMessaging.getInstance(ctx);
         settings= ctx.getSharedPreferences("Peez", 0);
    }

        public Boolean sendMessage(final String recipient, final String msgId, String message, final String ttlStr, Boolean dry) {

            final Message.Builder messageBuilder = new Message.Builder();
            if (!msgId.equals("")) {
                messageBuilder.collapseKey(msgId.trim());
            }
            try {
                int ttl = Integer.parseInt(ttlStr);
                messageBuilder.timeToLive(ttl);
            } catch (NumberFormatException e) {
                // ttl not set properly, ignoring
                Log.d(LOG_TAG, "Failed to parse TTL, ignoring: " + ttlStr);
            }
            messageBuilder.delayWhileIdle(false);
            messageBuilder.dryRun(dry);

//            Date date = null;
//            try {
//                String now = new Date().toString();
//                date = new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(now);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

           // message = date.toString() + " " + message;
            messageBuilder.addData("message", message);

            // messageBuilder.addData("move", moveString); TODO move this to another method that handles sending move data
            // messageBuilder.addData("invite", invitation); TODO move this to another method that handles sending invite data
            // messageBuilder.addData("board", boardLayoutString); TODO move this to another method that handles sending boardLayout data

            final String apiKey = consts.API_KEY;
            final String registrationId = settings.getString(consts.REG_ID, "");

            if (!registrationId.equals("") && !recipient.equals("") && !apiKey.equals("")) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        GcmServerSideSender sender = new GcmServerSideSender(apiKey, logger);
                        try {
                            sender.sendHttpJsonDownstreamMessage(recipient, messageBuilder.build()); //me
                           // sender.sendHttpJsonDownstreamMessage("f1mpXKewWmM:APA91bFHQn0czrXUJTfzwtBIhnTmthVVKm9vvlUKJDLJC-jCt3gQlCFmZ1lG1eHxXskmkNTGM-3FSefFMja4otrea6dGY74BtcBXrHVSZfGejABlv0LDuB2ciIf_aXOpscq656DT6-YM", messageBuilder.build()); //remy

                        } catch (final IOException e) {
                            return e.getMessage();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (result != null) {
                            Toast.makeText(context,
                                    "send message failed: " + result,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();

            } else {
                Toast.makeText(context,
                        "send message failed. Log out and log back in",
                        Toast.LENGTH_LONG).show();
                return false;
            }
return true;
        }

    public static BaseMessage BuildBaseMessageFromJsonMEssage(String to, String from,String message, String dateTime) {
            return new BaseMessage(to, from,message,dateTime);
    }
}
