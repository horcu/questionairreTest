package com.horcu.apps.peez.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.MoveMessage;
import com.horcu.apps.peez.gcm.SmsMessage;
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

    public Boolean SendSMS(final String recipient, final String msgId, String message, final String ttlStr, Boolean dry) {

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
                SendMessageAsync(recipient, messageBuilder, apiKey);

            } else {
                Toast.makeText(context,
                        "send message failed.",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

    public Boolean SendMove(final String recipient, final String msgId, String message, final String ttlStr, Boolean dry, int color) {

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
        messageBuilder.color(color);
        messageBuilder.addData("message", message);

        final String apiKey = consts.API_KEY;
        final String registrationId = settings.getString(consts.REG_ID, "");

        if (!registrationId.equals("") && !recipient.equals("") && !apiKey.equals("")) {
            SendMessageAsync(recipient, messageBuilder, apiKey);

        } else {
            Toast.makeText(context,
                    "send message failed.",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void SendMessageAsync(final String recipient, final Message.Builder messageBuilder, final String apiKey) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                GcmServerSideSender sender = new GcmServerSideSender(apiKey, logger);
                try {
                    sender.sendHttpJsonDownstreamMessage(recipient, messageBuilder.build());

                } catch (final IOException e) {
                    return e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    Toast.makeText(context,
                            "failed: " + result,
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context,
                            "sent",
                            Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public static SmsMessage BuildMessage(String to, String from, String message, String dateTime,String senderImgUrl) {
        return new SmsMessage(from, to,message,dateTime,senderImgUrl);
    }

    public static MoveMessage BuildMoveMessage(String moveFrom, String moveTo, String message, String dateTime, String senderToken, String receiverToken, String senderUrl, int color) {
        return new MoveMessage(moveFrom,moveTo,message,dateTime, senderToken,receiverToken, senderUrl, color);
    }
}
