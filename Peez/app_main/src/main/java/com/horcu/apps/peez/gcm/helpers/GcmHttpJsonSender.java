package com.horcu.apps.peez.gcm.helpers;///*

import android.support.v4.util.SimpleArrayMap;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.core.GcmServerSideSender;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.service.LoggingService;
import java.io.IOException;


public class GcmHttpJsonSender implements GcmTokenGetterInterface {
    String recipient;
    String response;

    @Override
    public int getName() {
        return R.string.quicktest_downstream_http_json;
    }


    @Override
    public void execute(final LoggingService.Logger logger, final Context context,
                        SimpleArrayMap<Integer, String> params, final String recip) {
        final String apiKey = consts.API_KEY;
        recipient = recip;

        logger.log(Log.INFO, context.getText(R.string.quicktest_downstream_http_json).toString(),"", "error");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final Message.Builder messageBuilder = new Message.Builder();
                GcmServerSideSender sender = new GcmServerSideSender(apiKey, logger);
                try {
                     response = sender.sendHttpJsonDownstreamMessage(messageBuilder.build());

                } catch (IOException ex) {
                    logger.log(Log.INFO, "Downstream HTTP JSON failed:\nerror: " + ex.getMessage(),"", "error");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //TODO - do something with the response here
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
}
