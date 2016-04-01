package com.horcu.apps.peez.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.JsonSyntaxException;
import com.horcu.apps.peez.Dtos.InviteDto;
import com.horcu.apps.peez.Dtos.MMDto;
import com.horcu.apps.peez.Dtos.SmsDto;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.gcm.core.PubSubHelper;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.view.fragments.ChatView;
import com.horcu.apps.peez.view.fragments.GameView;
import com.horcu.apps.peez.view.fragments.LandingViewFragment;
import com.horcu.apps.peez.viewmodel.PlayerViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LandingView extends AppCompatActivity implements LandingViewFragment.OnFragmentInteractionListener {

    private PubSubHelper pubsub;
    private LoggingService.Logger mLogger;
    private SenderCollection mSenders;
    private SharedPreferences settings;

    private BroadcastReceiver mLoggerCallback;
    private String mytoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);

        pubsub= new PubSubHelper(this);

        mLogger = new LoggingService.Logger(this);
        mSenders  = SenderCollection.getInstance(getApplicationContext());
        settings = getSharedPreferences(consts.PEEZ, 0);
        mytoken = settings.getString(consts.REG_ID, "");

        InitBroadcastReceiver();
    }

    @Override
    public void onInitiateNewGame(PlayerViewModel playerViewModel) {
        StartMainView(InComingMessageType.NEW_GAME, playerViewModel);
    }

    @Override
    public void onSwitchCurrentGame(String gameKey, PlayerViewModel playerViewModel) {
        StartMainView(InComingMessageType.GAME_SWITCH, playerViewModel, gameKey);
    }

    @Override
    public void onNonGameChat(PlayerViewModel playerVm) {
        StartMainView(InComingMessageType.CHAT, playerVm);
    }

    private void StartMainView(InComingMessageType type, PlayerViewModel playerVm) {
        Player player = playerVm.getModel();

        switch (type) {
            case CHAT:
                startActivityForChat(player);
                break;
            case GAME_SWITCH:
                break;
            case NEW_GAME:
                break;

        }


        Intent intent = new Intent();
    }

    private void startActivityForChat(Player player) {
        Intent intent = new Intent(getApplicationContext(), MainView.class);
        intent.putExtra(consts.MAINVIEW_PAGE, ChatView.class.getName());
        putDefaultExtras(player, intent);
        startActivity(intent);
    }

    private void putDefaultExtras(Player player, Intent intent) {
        intent.putExtra(consts.PLAYER_NAME, player.getUserName());
        intent.putExtra(consts.PLAYER_IMG_URL, player.getImageUri());
        intent.putExtra(consts.PLAYER_EMAIL, player.getEmail());
        intent.putExtra(consts.PLAYER_TOKEN, player.getToken());
    }

    private void StartMainView(InComingMessageType type, PlayerViewModel playerVm, String gameKey) {
        Player player = playerVm.getModel();

        switch (type) {
            case GAME_SWITCH:
                startActivityForGameSwitch(gameKey,player);
                break;
        }
    }

    private void startActivityForGameSwitch(String gameKey, Player player) {
        Intent intent = new Intent(getApplicationContext(), MainView.class);
        intent.putExtra(consts.MAINVIEW_PAGE, GameView.class.getName());
        putDefaultExtras(player, intent);
        startActivity(intent);
    }


    public enum InComingMessageType {
        CHAT,INCOMING_MOVE,GAME_SWITCH,NEW_GAME,
    }

    private void HandleInvitation(Message inviteMessage) {

    }

    private void InitBroadcastReceiver() {
        mLoggerCallback = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String messageObject = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);

                String messageJson = messageObject.substring(18, messageObject.length());
                if(messageJson.contains("multicast_id")) //return its the last message you sent out. use it to update whether the message was sent successfully or not
                {
                    // UpdateUI(messageJson);
                    return;
                }

                JSONObject json = null;
                try {
                    json = new JSONObject(messageJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                switch (intent.getAction()) {
                    case LoggingService.MESSAGE_TYPE_FEED:
                        break;
                    case LoggingService.MESSAGE_TYPE_INVITATION:
                        try {
                            InviteDto dto = new InviteDto(
                                    json.getString("message"),
                                    json.getString("dateTime"),
                                    mytoken,
                                    json.getString("receiverToken"),
                                    json.getString("invitationToken"),
                                    json.getString("senderUrl"),
                                    json.getString("move"),
                                    json.getInt("color"),
                                    json.getString("collapseKey"));

                            String jsonStr = MessageSender.JsonifyInviteDto(dto);
                            Message inviteMessage = MessageSender.BuildInvitationMessage(dto, jsonStr);

                            HandleInvitation(inviteMessage);
                        } catch (JsonSyntaxException | JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        mLogger.unregisterCallback(mLoggerCallback);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO rebuild instancestate here
        mLogger.registerCallback(mLoggerCallback);
    }

    @Override
    protected void onPause() {
        //TODO save the instance state
        mLogger.unregisterCallback(mLoggerCallback);
        super.onPause();
    }

}
