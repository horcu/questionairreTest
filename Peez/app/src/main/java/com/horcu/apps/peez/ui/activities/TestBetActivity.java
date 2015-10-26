package com.horcu.apps.peez.ui.activities;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.google.api.client.util.DateTime;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.backend.models.betApi.model.NFLPlayer;
import com.horcu.apps.peez.backend.models.betApi.model.Team;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.custom.NewBetNotification;
import com.horcu.apps.peez.logic.GcmServerSideSender;
import com.horcu.apps.peez.logic.Message;
import com.horcu.apps.peez.service.LoggingService;

import org.w3c.dom.Text;

import java.io.IOException;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;


public class TestBetActivity extends AppCompatActivity {

    private BetApi betApi;
    private LoggingService.Logger mLogger;
    private Message messageApi;
    private SharedPreferences settings;
    private BroadcastReceiver mLoggerCallback;
    private NotificationManager manager;
    private Notification myNotication;
    private UserApi userApi;
    private CollectionResponseUser allFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bet);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        settings = getSharedPreferences("Peez", 0);
        BuildBetService();
        BuildMessageService();
        BuildUserApiService();
        mLogger = new LoggingService.Logger(this);



        final EditText player_team = (EditText)findViewById(R.id.player_team);
        final EditText bet_stats = (EditText)findViewById(R.id.bet_stats);
        final EditText bet_amount = (EditText)findViewById(R.id.bet_amount);
        final LinearLayout getFriends = (LinearLayout)findViewById(R.id.bet_who_layout);
        final ListView friendsList = (ListView)findViewById(android.R.id.list);
        final TextView friends = (TextView)findViewById(R.id.friends_reg_list);
        final Button done = (Button)findViewById(R.id.done);
        StringBuilder friendsString = null;

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendsList.setVisibility(View.GONE);
                v.setVisibility(View.GONE);
            }
        });

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String existing = friends.getText().toString();
                friends.setText(existing += ("," + ((TextView) view).getText().toString()));
            }
        });

        getFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              done.setVisibility(View.VISIBLE);
              friendsList.setVisibility(View.VISIBLE);
                friendsList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

               // friendsList.setAdapter();

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            allFriends = userApi.list().execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                       List<String> friends = new ArrayList<>();

                        for (User user: allFriends.getItems())
                        {
                            String userEmail = user.getEmail();
                            if(!userEmail.equals(consts.PREF_ACCOUNT_NAME))
                            friends.add(userEmail);
                        }
                      friendsList.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.list, friends));

                    }
                }.execute();
            }
        });

        mLoggerCallback = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case LoggingService.ACTION_CLEAR_LOGS:
                        //  mLogsUI.setText("");
                        break;
                    case LoggingService.ACTION_LOG:
                       showNotification(intent);
//TODO remove line directly below
                        Snackbar.make(findViewById(R.id.test_main), Html.fromHtml(intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE)), Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };


        findViewById(R.id.send_bet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String playerTeam = player_team.getText().toString();
                final String betStats = bet_stats.getText().toString();
                final String betAmount = bet_amount.getText().toString();
                final String userGroups = friends.getText().toString();

                new AsyncTask<Void, Void, List>() {
                    @Override
                    protected List doInBackground(Void... params) {
                        List<String> registrationIds = null;
                        try {
                        String[] betText = betStats.split(",");
                        final String[] regIds = userGroups.split(",");
                        registrationIds = new ArrayList<>();
                        registrationIds.addAll(Arrays.asList(regIds));

                            String regId = settings.getString(consts.REG_ID, "");
                            registrationIds.add(regId); //send to yourself TODO remove this

                        Bet bet = new Bet()
                                .setBetId(UUID.randomUUID().toString())
                                .setBet(Arrays.asList(betText))
                                .setBetMadeAt(new DateTime(new Date(), TimeZone.getDefault()))
                                .setAcceptersRegIds(registrationIds)
                                .setBetterRegId(settings.getString(consts.REG_ID, ""))
                                .setTeam(new Team().setName("Baltimore Ravens"))
                                .setPlayer(new NFLPlayer().setName(playerTeam))
                                .setWager(Double.valueOf(betAmount));

                        //first make the bet

                            betApi.insert(bet).execute();
                            return registrationIds;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return new ArrayList();
                    }

                    @Override
                    protected void onPostExecute(List ids) {
                        if(!ids.isEmpty())
                        {
                            sendBetNotification(ids);
                        }

                    }
                }.execute();
            }
        });
    }

    private void showNotification(Intent intent) {
        try {
            String newLog = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);
            PendingIntent pendingIntent = PendingIntent.getActivity(TestBetActivity.this, 1, intent, 0);

            Notification.Builder builder = new Notification.Builder(TestBetActivity.this);

            builder.setAutoCancel(true);
            builder.setTicker(newLog);
            builder.setContentTitle("Peez");
            builder.setContentText(newLog);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(false);
            builder.setSubText("#swagBet #aintnobitch");   //API level 16
            builder.setNumber(100);
            builder.build();

           NewBetNotification.notify(getApplicationContext(), "Peyton Manning < 300 yds", 1, new Date().getTime() + 86400000);
//            NewBetNotification note = new NewBetNotification().notify(getApplicationContext(), builder.build());
            myNotication = builder.getNotification();
            manager.notify(11, myNotication);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        mLogger.unregisterCallback(mLoggerCallback);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLogger.registerCallback(mLoggerCallback);
    }


    private void sendBetNotification(final List regIds) {


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                GcmServerSideSender sender = new GcmServerSideSender(consts.API_KEY, mLogger);
                try {
                    for (int i = 0; i < regIds.size(); i++)
                    {
                        sender.sendHttpJsonDownstreamMessage((String) regIds.get(i), messageApi);
                    }

                } catch (final IOException e) {
                    return e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),
                            "send message failed: " + result,
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "bet sent!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void BuildBetService(){
        if (betApi == null) {
            BetApi.Builder builder = new BetApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            betApi = builder.build();
        }
    }

    public void BuildMessageService(){
        String userName = settings.getString(consts.PREF_ACCOUNT_NAME,"anonymouse user");

        if (messageApi == null) {
         messageApi = new Message.Builder()

            .collapseKey(consts.MESSAGE_COLLAPSED_KEY)
            .restrictedPackageName("")
            .timeToLive(10000)
            .dryRun(false)
            .delayWhileIdle(true)
            .notificationClickAction(consts.NOTIFICATION_CLICK_ACTION)
            .notificationIcon("icon")
            .notificationTitle("Peez challenge")
            .notificationTag("bet")
            .notificationSound("default")
            .notificationBody(String.format("%s %s", userName, consts.NOTIFICATION_BODY))
            .build();
        }
    }

    private void BuildUserApiService() {
        if (userApi == null) {
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            userApi = builder.build();
        }
    }
}
