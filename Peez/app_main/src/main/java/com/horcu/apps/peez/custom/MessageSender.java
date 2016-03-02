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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
                            //sender.sendHttpJsonDownstreamMessage("eDBwBS4KvHE:APA91bHUhsJc2Um201NSXfpX8tLYKKWjADfW3Pg0noHiQ0QSoLtSuE6UHtGm3csiU6cBEOBVwX1gzcL70j2jehIHEux3ZZnPqrQLYq1rd65Vs2by2IFdbpOm_nltdG-mVAW2ZNS8PqAi", messageBuilder.build()); //me
                            sender.sendHttpJsonDownstreamMessage("d-IelLPYTKw:APA91bHbVPEW8fVYM0YJ5-H8Qa3BZ9Mam7ESvGbcFL-GGCojuwkQqL7zMm1PKqxb2yHehE7FgDxlaJVjLFiNEgrru2wrCbAS0Llzuk99RR15QsDzyWyCSrMB7hp6iVoUjIldjSwM_0C-", messageBuilder.build()); //remy

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
