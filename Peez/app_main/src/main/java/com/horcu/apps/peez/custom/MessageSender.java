package com.horcu.apps.peez.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.horcu.apps.peez.Dtos.InviteDto;
import com.horcu.apps.peez.Dtos.MMDto;
import com.horcu.apps.peez.Dtos.SmsDto;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.core.GcmServerSideSender;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

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

    public Boolean SendSMS(Message message) {
            final String registrationId = settings.getString(consts.REG_ID, "");

            if (!registrationId.equals("") && !message.getTo().equals("")) {
                SendMessageAsync(consts.API_KEY,message);

            } else {
                Toast.makeText(context,
                        "send message failed.",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

    public Boolean SendMove(Message message) {
        try {
            SendMessageAsync(consts.API_KEY, message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean SendInvitation(final Message message) {
        try {
            SendMessageAsync(consts.API_KEY, message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void SendMessageAsync(final String apiKey, final Message message) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                GcmServerSideSender sender = new GcmServerSideSender(apiKey, logger);
                try {
                    sender.sendHttpJsonDownstreamMessage(message);

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

    public static Message BuildSmsMessage(SmsDto dto, String message) {
       return new Message.Builder()
                        .addData("message", message)
                        .type(LoggingService.EXTRA_LOG_MESSAGE)
                        .delayWhileIdle(false)
                        .timeToLive(10)
                        .collapseKey(dto.getFrom())
                        .to(dto.getTo())
                        .from(dto.getFrom())
                        .SenderImageUrl(dto.getSenderUrl())
                        .dryRun(false).build();
    }

    public static Message BuildMoveMessage(MMDto MMDto, String message) {
        return new Message.Builder()
                .addData("message", message)
                .type(LoggingService.MESSAGE_TYPE_MOVE)
                .from(MMDto.getMoveFrom())
                .to(MMDto.getMoveTo())
                .timeToLive(10)
                .SenderImageUrl(MMDto.getSenderUrl())
                .collapseKey(UUID.randomUUID().toString()).build();
    }

    public static Message BuildInvitationMessage(InviteDto dto,String message) {
        return new Message.Builder()
                .addData("message", message)
                .type(LoggingService.MESSAGE_TYPE_INVITATION)
                .timeToLive(10)
                .dryRun(false)
                .color(dto.getColor())
                .to(dto.getReceiverToken())
                .from(dto.getSenderToken())
                .SenderImageUrl(dto.getSenderUrl())
                .collapseKey(UUID.randomUUID().toString()).build();
    }

    public static String JsonifyMoveDto(MMDto dto) {
        JSONObject json = new JSONObject();
        try {
            json.put("color", dto.getColor());
            json.put("message", dto.getMessage());
            json.put("senderToken", dto.getSenderToken());
            json.put("receiverToken", dto.getReceiverToken());
            json.put("senderUrl", dto.getSenderUrl());
            json.put("collapseKey", dto.getCollapseKey());
            json.put("dateTime", dto.getDateTime());
            json.put("moveFrom", dto.getMoveFrom());
            json.put("moveTo", dto.getMoveTo());
            json.put("type", LoggingService.MESSAGE_TYPE_MOVE);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        return json.toString();
    }

    public static String JsonifySmsDto(SmsDto dto) {
        JSONObject json = new JSONObject();
        try {
            json.put("message", dto.getMessage());
            json.put("senderToken", dto.getFrom());
            json.put("receiverToken", dto.getTo());
            json.put("senderUrl", dto.getSenderUrl());
            json.put("dateTime", dto.getDateTime());
            json.put("type", LoggingService.MESSAGE_TYPE_MSG);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        return json.toString();
    }

    public static String JsonifyInviteDto(InviteDto dto) {
        JSONObject json = new JSONObject();
        try {
            json.put("color", dto.getColor());
            json.put("message", dto.getMessage());
            json.put("senderToken", dto.getSenderToken());
            json.put("receiverToken", dto.getReceiverToken());
            json.put("senderUrl", dto.getSenderUrl());
            json.put("collapseKey", dto.getCollapseKey());
            json.put("dateTime", dto.getDateTime());
            json.put("type", LoggingService.MESSAGE_TYPE_INVITATION);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        return json.toString();
    }
}
