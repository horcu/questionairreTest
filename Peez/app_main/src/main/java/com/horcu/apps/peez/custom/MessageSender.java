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
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.GcmServerSideSender;
import com.horcu.apps.peez.gcm.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;

import java.io.IOException;
import java.util.Random;

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
                          //  sender.sendHttpJsonDownstreamMessage("ejUsnCrzAI4:APA91bH0PeckRDDC7Z8WJTC2eUjP65Xiin2bh8yKMSyA6Cu2Wt62OzBu40WZK4acVDR7pFUVZt_xddHJOESA_UL8DQpisavJ9HRkEH8L5eySFiaTIFPe5H9qc619wIwiDSmR6g-TuRS4", messageBuilder.build()); //me
                            sender.sendHttpJsonDownstreamMessage("eduHXkuRKCk:APA91bG4T6jVn8SMUTb_dCjZIiKxC30sBhGxvKf1OsGuCDXg0c0r2D-Z2ub7EQU1RPP_SlT0yFWUSShVxfs9KFnksakMbhix4qrdw-yeWeY9Az0pGptpjmOT4n3ZEWew61g6q91wH12U", messageBuilder.build()); //remy

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
}
