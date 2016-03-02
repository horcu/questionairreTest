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
                            sender.sendHttpJsonDownstreamMessage("dwzAnKlTG3k:APA91bGYaATwRGqeYf6kYYMR5e6y_6hEiDsZY27L1ODOY4kFFGgedDDLiywqgEhEnBANyTwsHQy_twPRVgyZ2fzOCOlE3RLdBZ9Wga1XMAfk2UX6534HawIHeAhcK1envl4RXBQZ_cqv", messageBuilder.build()); //me
                          //  sender.sendHttpJsonDownstreamMessage("fLU7DY-3zWQ:APA91bEbRau3a259kR8GZ6PcVjewe0-a-mBq4GNNqlq68ynaJqiC3uyXhjUzUOIODPx0F_EJLTyhhYEes0D8p2aZp_RfECty0K2XTPWlOvm4bOdp5YFjrm5vpPJ0CSMHF-yc5VumKikV", messageBuilder.build()); //remy

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
