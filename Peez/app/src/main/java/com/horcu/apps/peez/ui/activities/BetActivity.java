package com.horcu.apps.peez.ui.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.daimajia.androidanimations.library.Techniques;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.util.DateTime;
import com.greenfrvr.hashtagview.HashtagView;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.backend.models.betApi.model.NFLPlayer;
import com.horcu.apps.peez.backend.models.betApi.model.Team;
import com.horcu.apps.peez.backend.models.misc.betStructureApi.BetStructureApi;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.custom.Money;
import com.horcu.apps.peez.custom.ViewController;
import com.horcu.apps.peez.custom.notifier;
import com.horcu.apps.peez.logic.GcmServerSideSender;
import com.horcu.apps.peez.logic.Message;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.spinner.NiceSpinner;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class BetActivity extends AppCompatActivity
        implements NumberPickerDialogFragment.NumberPickerDialogHandler
                  , AdapterView.OnItemSelectedListener
                  , AdapterView.OnItemClickListener
                  , View.OnClickListener, HashtagView.TagsClickListener {

    List<Bet> Bets = null;

    private ViewController viewController = null;

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
    private LinearLayout getFriends;
    private LinearLayout addnewLayout;
    private LinearLayout friendsListButtons;
  //  private RelativeLayout existingList;
    private LinearLayout bet_hashtags;
    private LinearLayout friendslayout;

    private Button numbers;
    private Button doneSelectingFriends;
    private Button addNewBet ;
    private Button betCardDoneButton ;
    private Button discard ;
    private Button sendBet ;
    private Button player_team ;

    private LinearLayout players_list_done;
    private LinearLayout doneDiscard;
   // private LinearLayout daddyDuteItem;

    private ListView friendsList;
    private RubberLoaderView loader;

    HashtagView betHashTag;

    int betNumber;
    NiceSpinner bet_stats, stat_element, equality_result, when_result;

    LinearLayout toptagmaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bet);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        }

        viewController = new ViewController();

      //  ContentTestBetBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test_bet);
      //  Bet bet = new Bet();
      //  binding.setBet(bet);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        settings = getSharedPreferences("Peez", 0);

        daddy = (LinearLayout) findViewById(R.id.daddy_dute);
        //existingList = (RelativeLayout)findViewById(R.id.existingList);


        BuildBetService();
        BuildMessageService();
        BuildUserApiService();

        betStructureApi = Api.BuildBetStructureApiService();
        mLogger = new LoggingService.Logger(this);

        List<String> list = new LinkedList<>(Arrays.asList("win", "int", "yds", "ret yds", "rec yds","catches","catches against"));

        List<String> listAction= new LinkedList<>(Arrays.asList("will record", "will not have", "will force", "will cause"));

        List<String> listEquality= new LinkedList<>(Arrays.asList("less than", "exactly", "more than"));

        List<String> listWhen= new LinkedList<>(Arrays.asList("game", "month", "year","1st quarter","2nd quarter","3rd quarter","4th quarter", "1st half, 2nd half"));

        toptagmaker = (LinearLayout)findViewById(R.id.top_tagmaker_section);
        //betCardsButtons = (LinearLayout)findViewById(R.id.done_discard);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listAction);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listEquality);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listWhen);

        player_team = (Button)findViewById(R.id.player_team);

        bet_stats = (NiceSpinner)findViewById(R.id.bet_stats); bet_stats.setAdapter(adapter2);

        stat_element = (NiceSpinner)findViewById(R.id.stat_element); stat_element.setAdapter(adapter);

        equality_result = (NiceSpinner)findViewById(R.id.equality); equality_result.setAdapter(adapter3);

        when_result = (NiceSpinner)findViewById(R.id.when); when_result.setAdapter(adapter4);

        players_list_done = (LinearLayout) findViewById(R.id.players_list_done); players_list_done.setOnClickListener(this);

        doneDiscard = (LinearLayout) findViewById(R.id.done_discard);

        addnewLayout = (LinearLayout)findViewById(R.id.add_new_layout);
        addNewBet = (Button) addnewLayout.findViewById(R.id.add_new_bet); addNewBet.setOnClickListener(this);

        bet_hashtags = (LinearLayout)findViewById(R.id.hashtag_bet_container);

        betHashTag = (HashtagView) bet_hashtags.findViewById(R.id.hashtagview);

        numbers = (Button)findViewById(R.id.numbers); numbers.setOnClickListener(this);

        betCardDoneButton = (Button)findViewById(R.id.createTag); betCardDoneButton.setOnClickListener(this);

        discard = (Button)findViewById(R.id.discard); discard.setOnClickListener(this);

         loader = (RubberLoaderView)findViewById(R.id.loader2);

        bet_amount = (LinearLayout)findViewById(R.id.bet_amount_layout); bet_amount.setOnClickListener(this);
        bet_amount_txt = (TextView) bet_amount.findViewById(R.id.bet_amount);

        getFriends = (LinearLayout)findViewById(R.id.bet_who_layout); getFriends.setOnClickListener(this);

        friendsList = (ListView)findViewById(android.R.id.list); friendsList.setOnItemClickListener(this);

        friendslayout = (LinearLayout)findViewById(R.id.friends_reg_layout);

         friends = (TextView)findViewById(R.id.friends_reg_list);

          doneSelectingFriends = (Button)findViewById(R.id.done); doneSelectingFriends.setOnClickListener(this);
          doneDiscard = (LinearLayout)findViewById(R.id.done_discard); doneDiscard.setOnClickListener(this);

        friendsListButtons = (LinearLayout)findViewById(R.id.action_layout);

        sendBet = (Button)findViewById(R.id.send_bet); sendBet.setOnClickListener(this);

        CircleImageView test = (CircleImageView)findViewById(R.id.entitiy_img);
       // CircleImageView test2 = (CircleImageView)findViewById(R.id.entitiy_img2);
      //  daddyDuteItem = (LinearLayout)findViewById(R.id.daddy_dute_item);
       // daddyDuteItem2 = (LinearLayout)findViewById(R.id.daddy_dute_item2);

        CircleImageView playerTeamImage = (CircleImageView)findViewById(R.id.chosen_player_team);
        String uri = "http://static.nfl.com/static/content/public/static/img/fantasy/transparent/200x200/" + "CHA561428" + ".png";
        String uri2 = "https://storage.googleapis.com/ballrz/images/bengals_away.png";
        Picasso.with(this).load(uri).into(playerTeamImage);
        Picasso.with(this).load(uri).into(test);
        //Picasso.with(this).load(uri2).into(test2);

        DecoView arcView1 = (DecoView)findViewById(R.id.dynamicArcView1);
        DecoView arcView1a = (DecoView)findViewById(R.id.dynamicArcView1a);
       // DecoView arcView2 = (DecoView)findViewById(R.id.dynamicArcView2);
      //  DecoView arcView2a = (DecoView)findViewById(R.id.dynamicArcView2a);

//       // Create background track
//        arcView1.addSeries(new SeriesItem.Builder(Color.argb(255, 102, 102, 102))
//                .setRange(0, 100, 100)
//                .setInitialVisibility(false)
//                .setLineWidth(8f)
//                .setChartStyle(SeriesItem.ChartStyle.STYLE_LINE_HORIZONTAL)
//                .build());
//
//      // Create background track
//        arcView2.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
//                .setRange(0, 100, 100)
//                .setInitialVisibility(false)
//                .setChartStyle(SeriesItem.ChartStyle.STYLE_LINE_HORIZONTAL)
//                .setLineWidth(8f)
//                .build());

        //Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(8f)
                .setCapRounded(false)
                .setSeriesLabel(new SeriesLabel.Builder("yds").build())
                .setShowPointWhenEmpty(true)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_LINE_VERTICAL)
                .build();

        int series1Index1 = arcView1.addSeries(seriesItem1);

        //Create data series track
        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(8f)
                .setCapRounded(true)
                .setSeriesLabel(new SeriesLabel.Builder("ints").build())
                .setShowPointWhenEmpty(true)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_LINE_VERTICAL)
                .build();

        int series1Index2 = arcView1a.addSeries(seriesItem2);

        arcView1.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(2000)
                .setColor(android.R.color.holo_purple)
                .build());

//        arcView2.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
//                .setDelay(1000)
//                .setDuration(2000)
//                .setColor(android.R.color.holo_red_dark)
//                .build());

        arcView1.addEvent(new DecoEvent.Builder(90).setIndex(series1Index1).setDelay(4000).build());
        arcView1.addEvent(new DecoEvent.Builder(54).setIndex(series1Index1).setDelay(8000).build());

        arcView1a.addEvent(new DecoEvent.Builder(54).setIndex(series1Index2).setDelay(8000).build());
        arcView1a.addEvent(new DecoEvent.Builder(54).setIndex(series1Index2).setDelay(8000).build());

//        arcView2.addEvent(new DecoEvent.Builder(45).setIndex(series1Index2).setDelay(4000).build());
//        arcView2.addEvent(new DecoEvent.Builder(70).setIndex(series1Index2).setDelay(8000).build());
//
//        arcView2a.addEvent(new DecoEvent.Builder(70).setIndex(series1Index1).setDelay(8000).build());
//        arcView2a.addEvent(new DecoEvent.Builder(70).setIndex(series1Index1).setDelay(8000).build());


        StringBuilder friendsString = null;

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
    }

    private void makeTag(String tagString) {
        List<String> data = new ArrayList<>();
        if(tagString.equals(""))
            return;

        data.add(tagString);

        betHashTag.addOnTagClickListener(this);
        betHashTag.setRowMode(HashtagView.MODE_STRETCH);


        new AsyncTask<Void, Void, Drawable>() {
            @Override
            protected Drawable doInBackground(Void... params) {
                Drawable  d = Drawable.createFromPath("http://static.nfl.com/static/content/public/static/img/fantasy/transparent/200x200/" + "CHA561428" + ".png");
            return d;
            }

            @Override
            protected void onPostExecute(Drawable result) {
               // betHashTag.setLeftDrawable(R.id.);
            }
        }.execute();

        betHashTag.setData(data);
        toptagmaker.setVisibility(View.GONE);
        doneDiscard.setVisibility(View.GONE);
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


        return String.format("%s %s %s %d %s %s",player_team , stat, equality, betNumber, element, when);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bet, menu);
        MenuItem item = menu.findItem(R.id.spinner_filter);
        NiceSpinner spinner = (NiceSpinner) MenuItemCompat.getActionView(item);

        List<String> list = new LinkedList<>(Arrays.asList("Requested", "Accepted", "Public"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
        spinner.setOnItemSelectedListener(this); // set the listener, to perform actions based on item selection
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private void showNewStage() {
        viewController
                .showThis(daddy, Techniques.FadeIn)
                .hideThis(friendslayout, Techniques.FadeOut);
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
        String userName = settings.getString(consts.PREF_ACCOUNT_NAME,"anonymous user");

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(id == R.id.spinner_filter)
        {
            Snackbar.make(view, "Filter position: " + position, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_new_bet:
            {
             //   viewController.hideThis(daddyDuteItem, Techniques.SlideOutUp);
                openbetCard();

                break;
            }

            case R.id.players_list_done:
            {
                openbetCard();
                break;
            }
            case R.id.numbers:
            {
                NumberPickerBuilder numberPicker = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setReference(2)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                numberPicker.show();
                break;
            }
            case R.id.discard:
            {

                viewController
                        .hideThis(daddy, Techniques.FadeOutDown)
                        .hideThis(doneDiscard, Techniques.FadeOutUp)
                        .showThis(friendsListButtons, Techniques.FadeInUp);
                break ;
            }
            case R.id.createTag:
            {

                viewController
                        .showThis(bet_hashtags, Techniques.SlideInLeft)
                        .showThis(betCardDoneButton, Techniques.SlideOutRight)
                        .showThis(bet_stats, Techniques.SlideInLeft)
                            .showThis(loader, Techniques.FadeIn);
                loader.setVisibility(View.VISIBLE);
                loader.startLoading();
                String tagString = getTagString(v);

                if (!tagString.equals(""))
                    makeTag(tagString);

                viewController
                        .hideThis(loader, Techniques.FadeOut)
                        .hideThis(betCardDoneButton, Techniques.SlideOutDown)
                        .hideThis(daddy, Techniques.SlideOutUp);
                        //.showThis(existingList, Techniques.FadeIn);

               // existingList.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.bet_amount:
            {
                NumberPickerBuilder numberPicker = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setReference(1)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                numberPicker.show();
                break;
            }
            case R.id.done:
            {
                viewController
                        .showThis(addnewLayout, Techniques.SlideInDown)
                        .showThis(friendsListButtons, Techniques.SlideInUp)
                       // .showThis(daddyDuteItem, Techniques.BounceInRight)
                        .hideThis(friendsList, Techniques.SlideOutRight);
                break;

            }
            case R.id.bet_who_layout:
            {
                viewController
                        .showThis(doneSelectingFriends, Techniques.SlideInUp)
                        .showThis(friendslayout, Techniques.SlideInDown)
                        .hideThis(daddy, Techniques.SlideInDown)
                        .hideThis(doneDiscard, Techniques.SlideOutDown)
                        .hideThis(friendsListButtons, Techniques.SlideOutDown);

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
                break;
            }
            case R.id.send_bet:
            {
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
                break;
            }
        }
    }

    private void openbetCard() {
            showNewStage();

            viewController
                    .hideThis(addnewLayout, Techniques.FadeOutLeft)
                    .hideThis(friendslayout, Techniques.FadeOut)
                    //.hideThis(existingList,Techniques.FadeOut)
                    .showThis(doneDiscard, Techniques.FadeInRight);


        return;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     switch (view.getId())
     {
         case android.R.id.list:
         {
             try {

                 doneSelectingFriends.setVisibility(View.VISIBLE);
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
             break;
         }
     }
    }

    //this is for the hashtags
    @Override
    public void onItemClicked(Object item) {
        Snackbar.make(addnewLayout,"Tag clicked and should go to edit if you parked it or if you are creating a new only", Snackbar.LENGTH_LONG).show();
    }
}
