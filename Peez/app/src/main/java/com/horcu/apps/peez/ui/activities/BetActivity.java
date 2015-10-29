package com.horcu.apps.peez.ui.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
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
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.custom.notifier;
import com.horcu.apps.peez.logic.GcmServerSideSender;
import com.horcu.apps.peez.logic.Message;
import com.horcu.apps.peez.service.LoggingService;

import java.io.IOException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;


public class BetActivity extends AppCompatActivity {

    private BetApi betApi;
    private LoggingService.Logger mLogger;
    private Message messageApi;
    private SharedPreferences settings;
    private BroadcastReceiver mLoggerCallback;
    private NotificationManager manager;
    private Notification myNotication;
    private UserApi userApi;
    private CollectionResponseUser allFriends;
    private UserSettingsApi userSettingsApi;
    private String me;
    private  TextView friends;

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
        bet_amount.setVisibility(View.GONE);
        final LinearLayout getFriends = (LinearLayout)findViewById(R.id.bet_who_layout);
        getFriends.setVisibility(View.GONE);
        final ListView friendsList = (ListView)findViewById(android.R.id.list);
         friends = (TextView)findViewById(R.id.friends_reg_list);
        final Button done = (Button)findViewById(R.id.done);
        final LinearLayout actionLayout = (LinearLayout)findViewById(R.id.action_layout);

        StringBuilder friendsString = null;

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendsList.setVisibility(View.GONE);
                v.setVisibility(View.GONE);
                actionLayout.setVisibility(View.VISIBLE);
            }
        });

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    done.setVisibility(View.VISIBLE);
                    ArrayAdapter adapter = (ArrayAdapter) friendsList.getAdapter();
                    String item = adapter.getItem(position).toString();
                    String existing ;
                    if(friends.getText() !=null)
                    existing = friends.getText().toString();
                    else
                    existing = "";

                    String selected = existing.equals("")
                            ?item
                            : existing.contains(item) ? existing : existing + ("," +  item);

                    if(selected !=null && selected !="")
                     friends.setText(selected);
                     else
                        friends.setText("no one chosen");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              done.setVisibility(View.VISIBLE);
                friendsList.setVisibility(View.VISIBLE);
                friendsList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                actionLayout.setVisibility(View.GONE);


                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            allFriends = userApi
                                         .list()
                                         .execute();

                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {

                        try {
                            ArrayAdapter<String> adapter;
                            List<String> friends = new ArrayList<>();
                            me = settings.getString(consts.PREF_ACCOUNT_NAME,"");
                            for (User user: allFriends.getItems())
                            {
                                if(!user.getEmail().equals(me)) {
                                    String userEmail = user.getEmail();
                                        friends.add(userEmail);
                                }
                            }
                            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.user_item, R.id.friend, friends);
                            friendsList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            // friendsList.notify();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                      notifier.showNotification(intent, BetActivity.this, MainActivity.class);
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
                        final String[] emails = userGroups.split(",");
                        registrationIds = new ArrayList<>();

                            for (int i = 0; i< emails.length; i++)
                            {
                                String regid = userApi.get(emails[i]).execute().getRegistrationId();
                               registrationIds.add(regid);
                            }

                        Bet bet = new Bet()
                                .setBetId(UUID.randomUUID().toString())
                                .setBet(Arrays.asList(betText))
                                .setBetMadeAt(new DateTime(new Date(), TimeZone.getDefault()))
                                //.setSentTo(registrationIds) //TODO add this property
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
