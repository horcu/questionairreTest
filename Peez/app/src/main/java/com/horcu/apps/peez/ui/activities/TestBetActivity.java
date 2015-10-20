package com.horcu.apps.peez.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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
import com.horcu.apps.peez.logic.GcmServerSideSender;
import com.horcu.apps.peez.logic.Message;
import com.horcu.apps.peez.service.LoggingService;

import java.io.IOException;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class TestBetActivity extends AppCompatActivity {

    private BetApi betApi;
    private LoggingService.Logger mLogger;
    private Message messageApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bet);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        BuildBetService();
        BuildMessageService();

        final Message.Builder messageBuilder = new Message.Builder();
        messageBuilder.delayWhileIdle(true);
        mLogger = new LoggingService.Logger(this);

        final EditText player_team = (EditText)findViewById(R.id.player_team);
        final EditText bet_stats = (EditText)findViewById(R.id.bet_stats);
        final EditText bet_amount = (EditText)findViewById(R.id.bet_amount);
        final EditText users_groups_betting = (EditText)findViewById(R.id.users_groups_betting);


        findViewById(R.id.send_bet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playerTeam = player_team.getText().toString();
                final String betStats = bet_stats.getText().toString();
                final String betAmount = bet_amount.getText().toString();
                final String userGroups = users_groups_betting.getText().toString();



                new AsyncTask<Void, Void, List>() {
                    @Override
                    protected List doInBackground(Void... params) {

                        String[] betText = betStats.split(",");
                        final String[] regIds = userGroups.split(",");
                        List<String> registrationIds = Arrays.asList(regIds);
                        registrationIds.add(consts.REG_ID); //send to yourself TODO remove this

                        Bet bet = new Bet()
                                .setBet(Arrays.asList(betText))
                                .setBetMadeAt(new DateTime(new Date(), TimeZone.getDefault()))
                                .setAcceptersRegIds(registrationIds)
                                .setBetterRegId(consts.REG_ID)
                                        //.setTeam()
                                .setPlayer(new NFLPlayer())
                                .setWager(Double.valueOf(betAmount));

                        //first make the bet
                        try {
                            betApi.insert(bet).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return registrationIds;
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
        if (messageApi == null) {
            final Message.Builder messageApi = new Message.Builder() ;
        }
    }
}
