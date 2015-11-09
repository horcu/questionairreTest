package com.horcu.apps.peez.ui.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.google.api.client.util.DateTime;
import com.google.gson.Gson;
import com.greenfrvr.hashtagview.HashtagView;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.backend.models.betApi.model.NFLPlayer;
import com.horcu.apps.peez.backend.models.betApi.model.Team;
import com.horcu.apps.peez.backend.models.misc.betStructureApi.BetStructureApi;
import com.horcu.apps.peez.backend.models.misc.betStructureApi.model.BetStructure;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.custom.Money;
import com.horcu.apps.peez.custom.Taglist;
import com.horcu.apps.peez.custom.notifier;
import com.horcu.apps.peez.logic.GcmServerSideSender;
import com.horcu.apps.peez.logic.Message;
import com.horcu.apps.peez.service.LoggingService;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class BetActivity extends AppCompatActivity implements NumberPickerDialogFragment.NumberPickerDialogHandler {

    List<Bet> Bets = null;

    private BetApi betApi;
    private LoggingService.Logger mLogger;
    private Message messageApi;
    private SharedPreferences settings;
    private BroadcastReceiver mLoggerCallback;
    private NotificationManager manager;
    private Notification myNotication;
    private UserApi userApi;
    private BetStructureApi betStructureApi;
    private CollectionResponseUser allFriends;
    private UserSettingsApi userSettingsApi;
    private String me;
    private  TextView friends;
    private  TextView bet_amount_txt;
    private LinearLayout bet_amount;
    private String structure;
    private TextView selected_stat;

    private LinearLayout daddy;
    private RelativeLayout existingList;

    private Button numbers;

    HashtagView htagView_verb;

    int betNumber;
    BetterSpinner bet_stats, stat_element, equality_result, when_result;

    LinearLayout toptagmaker, bottomTagmaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bet);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.white)));
        }

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        settings = getSharedPreferences("Peez", 0);

        daddy = (LinearLayout) findViewById(R.id.daddy_dute);
        existingList = (RelativeLayout)findViewById(R.id.existingList);


        BuildBetService();
        BuildMessageService();
        BuildUserApiService();

        betStructureApi = Api.BuildBetStructureApiService();
        mLogger = new LoggingService.Logger(this);

        List<String> list = new ArrayList<>();
        list.add("win");
        list.add("int");
        list.add("yds");
        list.add("ret yds");
        list.add("rec yds");
        list.add("catches");
        list.add("catches against");

        List<String> listAction= new ArrayList<>();
        listAction.add("will record");
        listAction.add("will not have");
        listAction.add("will force");
        listAction.add("will cause");

        List<String> listEquality= new ArrayList<>();
        listEquality.add("less than");
        listEquality.add("exactly");
        listEquality.add("more than");


        List<String> listWhen= new ArrayList<>();
        listWhen.add("game");
        listWhen.add("month");
        listWhen.add("year");
        listWhen.add("1st quarter");
        listWhen.add("2nd quarter");
        listWhen.add("3rd quarter");
        listWhen.add("4th quarter");

        toptagmaker = (LinearLayout)findViewById(R.id.top_tagmaker_section);
        bottomTagmaker = (LinearLayout)findViewById(R.id.bottom_tagmaker_section);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listAction);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listEquality);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listWhen);

        final Button player_team = (Button)findViewById(R.id.player_team);

        bet_stats = (BetterSpinner)findViewById(R.id.bet_stats);
        bet_stats.setAdapter(adapter2);

        stat_element = (BetterSpinner)findViewById(R.id.stat_element);
        stat_element.setAdapter(adapter);

        equality_result = (BetterSpinner)findViewById(R.id.equality);
        equality_result.setAdapter(adapter3);

        when_result = (BetterSpinner)findViewById(R.id.when);
        when_result.setAdapter(adapter4);

        final LinearLayout bet_hashtags = (LinearLayout)findViewById(R.id.hashtag_bet_container);
        final LinearLayout stats_layout = (LinearLayout)findViewById(R.id.stat_layout);

        htagView_verb = (HashtagView)bet_hashtags.findViewById(R.id.hashtagview);
       // selected_stat = (TextView)findViewById(R.id.selected_stat);

        numbers = (Button)findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerBuilder numberPicker = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setReference(2)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                numberPicker.show();
            }
        });

        final Button expandLess = (Button)findViewById(R.id.expand_less);
        final RubberLoaderView loader = (RubberLoaderView)stats_layout.findViewById(R.id.loader2);

        expandLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bet_hashtags.setVisibility(View.VISIBLE);
                expandLess.setVisibility(View.GONE);
                bet_stats.setVisibility(View.VISIBLE);
                loader.setVisibility(View.VISIBLE);
                loader.startLoading();
                String tagString = getTagString(v);

                if(!tagString.equals(""))
                makeTag(tagString);

                loader.setVisibility(View.GONE);

            }
        });

//        bet_stats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                bet_hashtags.setVisibility(View.VISIBLE);
//                expandLess.setVisibility(View.VISIBLE);
//                bet_stats.setText("He will have");
//                numbers.setVisibility(View.VISIBLE);
//
//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... params) {
////                        InputStream raw =  getResources().openRawResource(R.raw.bet_structure);
////                        Reader rd = new BufferedReader(new InputStreamReader(raw));
////                        Gson gson = new Gson();
////                        structure = gson.fromJson(rd, JSONObject.class).toString();
//                        try {
//                            if(structure == null || structure.equals("")) {
//                                List<BetStructure> structures = betStructureApi.list().execute().getItems();
//                                for (BetStructure struct : structures) {
//                                    structure = struct.getStructure();
//                                    return null;
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void result) {
//                        if (structure != null) {
//                            try {
//                                htagView_verb.setData(Taglist.getVerbs(consts.ENTITY_QUARTERBACK, structure));
//                                htagView_verb.setBackgroundColor(R.color.colorPrimary);
//                                bet_stats.setVisibility(View.GONE);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }.execute();
       //     }
   //     });

        bet_amount = (LinearLayout)findViewById(R.id.bet_amount_layout);
        bet_amount_txt = (TextView) bet_amount.findViewById(R.id.bet_amount);

        bet_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerBuilder numberPicker = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setReference(1)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                numberPicker.show();
            }
        });

        final LinearLayout getFriends = (LinearLayout)findViewById(R.id.bet_who_layout);

        final ListView friendsList = (ListView)findViewById(android.R.id.list);
         friends = (TextView)findViewById(R.id.friends_reg_list);
        final Button done = (Button)findViewById(R.id.done);
        final LinearLayout actionLayout = (LinearLayout)findViewById(R.id.action_layout);
//
//
//        HashtagView htagView_actionverb = (HashtagView)bet_hashtags.findViewById(R.id.hashtagview_action_verbs);
//        htagView_actionverb.setVisibility(View.GONE);
//        HashtagView htagView_when = (HashtagView)bet_hashtags.findViewById(R.id.hashtagview_when);
//
//
//
//        htagView_actionverb.setData(Taglist.getActionVerbs().subList(0, 4));
//        htagView_actionverb.setBackgroundColor(R.color.wallet_holo_blue_light);
//        htagView_actionverb.setVisibility(View.GONE);
//
//        htagView_when.setData(Taglist.getWhen().subList(0, 4));
//        htagView_when.setBackgroundColor(android.R.color.holo_green_light);
//        htagView_when.setVisibility(View.GONE);

        CircleImageView playerTeamImage = (CircleImageView)findViewById(R.id.chosen_player_team);
        String uri = "http://static.nfl.com/static/content/public/static/img/fantasy/transparent/200x200/" + "FLA009602" + ".png";
        Picasso.with(this).load(uri).into(playerTeamImage);

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
                final String betAmount = bet_amount_txt.getText().toString();
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

    private void makeTag(String tagString) {
        List<String> data = new ArrayList<>();
        if(tagString.equals(""))
            return;

        data.add(tagString);
        htagView_verb.setData(data);
        toptagmaker.setVisibility(View.GONE);
        bottomTagmaker.setVisibility(View.GONE);
    }

    private String getTagString(View v) {
        String stat = bet_stats.getText().toString();
        String equality = equality_result.getText().toString();
        String element = stat_element.getText().toString();
        String when = when_result.getText().toString();

        if(stat.equals("") || equality.equals("") || element.equals("") || when.equals(""))
            {
                Snackbar.make(v,"the bet is incomplete. Make all required choices "
                        + (!stat.equals("") ? stat : "")
                        + (!equality.equals("") ? equality : "")
                        + (!element.equals("") ? element : "")
                        + (!when.equals("") ? when : "")
                        , Snackbar.LENGTH_LONG).show();
                return "";
            }


        return String.format("%s %s %d %s %s", stat, equality, betNumber, element, when);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bet_new) {
            showNewStage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNewStage() {
        daddy.setVisibility(View.VISIBLE);
        int daddyBottom = daddy.getBottom();
        existingList.setTop((daddyBottom + 10));
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

    @Override
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative, double fullNumber) {

        if(reference == 1) {
            Money.decreaseTotalAmountBy(number);
            bet_amount_txt.setText(number + " credits");
        }
        else if(reference == 2)
        {
            numbers.setText(String.valueOf(number));
            betNumber = number;
        }
    }
}
