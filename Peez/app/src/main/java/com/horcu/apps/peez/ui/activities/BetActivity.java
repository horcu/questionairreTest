package com.horcu.apps.peez.ui.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.daimajia.androidanimations.library.Techniques;
import com.google.api.client.util.DateTime;
import com.greenfrvr.rubberloader.RubberLoaderView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.backend.models.betApi.model.NFLPlayer;
import com.horcu.apps.peez.backend.models.betApi.model.Team;
import com.horcu.apps.peez.backend.models.misc.betStructApi.BetStructApi;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.custom.TileView;
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

public class BetActivity extends AppCompatActivity
        implements NumberPickerDialogFragment.NumberPickerDialogHandler
        , AdapterView.OnItemSelectedListener
        , AdapterView.OnItemClickListener
        , View.OnClickListener {

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
    private BetStructApi betStructureApi;
    private CollectionResponseUser allFriends;
    private List<User> allLocalFriends;
    private UserSettingsApi userSettingsApi;
    private String me;
    private TextView friends;
   // private TextView bet_amount_txt;
    private LinearLayout bet_amount;
    private String structure;
    private TextView selected_stat;

    private LinearLayout daddy;
    private LinearLayout getFriends;
    private LinearLayout addnewLayout;
    private LinearLayout friendsListButtons;
    private RelativeLayout peezbar_root;
    private LinearLayout bet_hashtags;
    private LinearLayout friendslayout;

    private Button numbers;
    private Button doneSelectingFriends;
    private Button addNewBet;
    private Button betCardDoneButton;
    private Button discard;
    private Button sendBet;
    private Button player_team;

    private LinearLayout players_list_done;
    private LinearLayout doneDiscard;
    private LinearLayout daddyDuteItem;

    private RecyclerView friendsList;

    private TileView friend_1, friend_2, friend_3;
    private TextView extra_friends_count;

    private RubberLoaderView loader;
//
  //  HashtagView betHashTag;

    int betNumber;
    NiceSpinner bet_stats, stat_element, equality_result, when_result;

    LinearLayout toptagmaker;
    private LinearLayout peezbar;
    private RecyclerView current_bets;
    private TextView friendsListText;
    private HorizontalScrollView selected_users_scrollView;
    private LinearLayout userGrid;
    private ImageView playerTeamImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bet);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(2);
        }


        viewController = new ViewController();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        settings = getSharedPreferences("Peez", 0);

        daddy = (LinearLayout) findViewById(R.id.daddy_dute);
        betApi = Api.BuildBetApiService();
        BuildMessageService();
        userApi = Api.BuildUserApiService();

        betStructureApi = Api.BuildBetStructureApiService();
        mLogger = new LoggingService.Logger(this);

        List<String> list = new LinkedList<>(Arrays.asList("win", "int", "yds", "ret yds", "rec yds", "catches", "catches against"));

        List<String> listAction = new LinkedList<>(Arrays.asList("will record", "will not have", "will force", "will cause"));

        List<String> listEquality = new LinkedList<>(Arrays.asList("less than", "exactly", "more than"));

        List<String> listWhen = new LinkedList<>(Arrays.asList("This week vs The Baltimore Ravens", "For the entire month of June", "For the 2015 Nfl regular season", "At the end of the 1st quarter", "2nd quarter", "3rd quarter", "4th quarter", "1st half, 2nd half"));

        toptagmaker = (LinearLayout) findViewById(R.id.top_tagmaker_section);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listAction);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listEquality);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listWhen);

        userGrid = (LinearLayout)findViewById(R.id.selected_users_grid);

        friendsListText = (TextView)findViewById(R.id.users_groups_betting);

        player_team = (Button) findViewById(R.id.player_team);

        bet_stats = (NiceSpinner) findViewById(R.id.bet_stats);
        bet_stats.setAdapter(adapter2);

        stat_element = (NiceSpinner) findViewById(R.id.stat_element);
        stat_element.setAdapter(adapter);

        equality_result = (NiceSpinner) findViewById(R.id.equality);
        equality_result.setAdapter(adapter3);

        when_result = (NiceSpinner) findViewById(R.id.when);
        when_result.setAdapter(adapter4);

        doneDiscard = (LinearLayout) findViewById(R.id.done_discard);

        numbers = (Button) findViewById(R.id.numbers);
        numbers.setOnClickListener(this);

        betCardDoneButton = (Button) findViewById(R.id.createTag);
        betCardDoneButton.setOnClickListener(this);

        discard = (Button) findViewById(R.id.discard);
        discard.setOnClickListener(this);

        loader = (RubberLoaderView) findViewById(R.id.loader2);

        getFriends = (LinearLayout) findViewById(R.id.bet_who_layout);
        getFriends.setOnClickListener(this);

        friendsList = (RecyclerView) findViewById(R.id.users_list);

        friends = (TextView) findViewById(R.id.friends_reg_list);

        doneDiscard = (LinearLayout) findViewById(R.id.done_discard);
        doneDiscard.setOnClickListener(this);

        friendsListButtons = (LinearLayout) findViewById(R.id.action_layout);

        sendBet = (Button) findViewById(R.id.send_bet);
        sendBet.setOnClickListener(this);

        peezbar_root = (RelativeLayout) findViewById(R.id.peezbar_root);
        peezbar = (LinearLayout) findViewById(R.id.peezbar);

        playerTeamImage = (ImageView) findViewById(R.id.chosen_player_team);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            playerTeamImage.setElevation(2f);
        }

        friend_1  = (TileView)findViewById(R.id.friend_1);
        friend_2  = (TileView)findViewById(R.id.friend_2);
        friend_3  = (TileView)findViewById(R.id.friend_3);

      //  String uri2 = "https://storage.googleapis.com/ballrz/images/bengals_away.png";
        String uri = String.format("%sBRO581187.png", consts.IMG_DEF_URI);
        Picasso.with(this).load(uri).into(playerTeamImage);

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
        if (tagString.equals(""))
            return;

        data.add(tagString);

        new AsyncTask<Void, Void, Drawable>() {
            @Override
            protected Drawable doInBackground(Void... params) {
                Drawable d = Drawable.createFromPath("http://static.nfl.com/static/content/public/static/img/fantasy/transparent/200x200/" + "CHA561428" + ".png");
                return d;
            }

            @Override
            protected void onPostExecute(Drawable result) {
                // betHashTag.setLeftDrawable(R.id.);
            }
        }.execute();

        toptagmaker.setVisibility(View.GONE);
        doneDiscard.setVisibility(View.GONE);
    }

    private String getTagString(View v) {
        String stat = bet_stats.getText().toString();
        String equality = equality_result.getText().toString();
        String element = stat_element.getText().toString();
        String when = when_result.getText().toString();

        if (stat.equals("") || equality.equals("") || element.equals("") || when.equals("")) {
            Snackbar.make(v, "the bet is incomplete. Make all required choices "
                    + (!stat.equals("") ? stat : "")
                    + (!equality.equals("") ? equality : "")
                    + (!element.equals("") ? element : "")
                    + (!when.equals("") ? when : "")
                    , Snackbar.LENGTH_LONG).show();
            return "";
        }


        return String.format("%s %s %s %d %s %s", player_team, stat, equality, betNumber, element, when);
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
                    for (int i = 0; i < regIds.size(); i++) {
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

                } else {
                    Toast.makeText(getApplicationContext(),
                            "bet sent!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void BuildMessageService() {
        String userName = settings.getString(consts.PREF_ACCOUNT_NAME, "anonymous user");

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


    @Override
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative, double fullNumber) {

        if (reference == 1) {
            Money.decreaseTotalAmountBy(number);
           // bet_amount_txt.setText(number + " credits");
        } else if (reference == 2) {
            numbers.setText(String.valueOf(number));
            betNumber = number;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (id == R.id.spinner_filter) {
            Snackbar.make(view, "Filter position: " + position, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.players_list_done: {
                openbetCard();
                break;
            }
            case R.id.numbers: {
                NumberPickerBuilder numberPicker = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setReference(2)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                numberPicker.show();
                break;
            }
            case R.id.discard: {

                viewController
                        .hideThis(daddy, Techniques.FadeOutDown)
                        .hideThis(doneDiscard, Techniques.FadeOutUp)
                        .showThis(friendsListButtons, Techniques.FadeInUp);
                break;
            }
            case R.id.createTag: {

                viewController
                        .showThis(bet_hashtags, Techniques.SlideInLeft)
                        .showThis(betCardDoneButton, Techniques.SlideOutRight)
                        .showThis(bet_stats, Techniques.SlideInLeft)
                        .showThis(loader, Techniques.FadeIn);
          //      loader.setVisibility(View.VISIBLE);
           //     loader.startLoading();
                String tagString = getTagString(v);

                if (!tagString.equals(""))
                    makeTag(tagString);

                viewController
                        .hideThis(loader, Techniques.FadeOut)
                        .hideThis(betCardDoneButton, Techniques.SlideOutDown)
                        .hideThis(daddy, Techniques.SlideOutUp);
                break;
            }
            case R.id.bet_amount_layout: {
                NumberPickerBuilder numberPicker = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setReference(1)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                numberPicker.show();
                break;
            }
            case R.id.done: {
                viewController
                        .showThis(addnewLayout, Techniques.SlideInDown)
                        .showThis(friendsListButtons, Techniques.SlideInUp)
                                // .showThis(daddyDuteItem, Techniques.BounceInRight)
                        .hideThis(friendsList, Techniques.SlideOutRight);
                break;

            }
            case R.id.bet_who_layout: {
//                viewController
//                        .showThis(doneSelectingFriends, Techniques.SlideInUp)
//                        .showThis(friendslayout, Techniques.SlideInDown)
//                        .showThis(friendsList, Techniques.SlideInDown)
//                        .showThis(friendsListButtons, Techniques.SlideInDown)
//                        .hideThis(daddy, Techniques.SlideInDown)
//                        .hideThis(doneDiscard, Techniques.SlideOutDown)
//                        .hideThis(bet_amount, Techniques.SlideOutDown)
//                        .hideThis(peezbar, Techniques.SlideOutUp);
                Intent usersIntent = new Intent(getApplicationContext(),UsersActivity.class);
                startActivityForResult(usersIntent,consts.GET_CONTACTS_RESULTS);

                break;
            }
            case R.id.send_bet: {
                final String playerTeam = player_team.getText().toString();
                final String betStats = bet_stats.getText().toString();
              //  final String betAmount = bet_amount_txt.getText().toString();
                final String userGroups = friends.getText().toString();

                new AsyncTask<Void, Void, List>() {
                    @Override
                    protected List doInBackground(Void... params) {
                        List<String> registrationIds = null;
                        try {
                            String[] betText = betStats.split(",");
                            final String[] emails = userGroups.split(",");
                            registrationIds = new ArrayList<>();

                            for (int i = 0; i < emails.length; i++) {
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
                                    .setPlayer(new NFLPlayer().setName(playerTeam));
                                    //.setWager(Double.valueOf(betAmount));

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
                        if (!ids.isEmpty()) {
                            sendBetNotification(ids);
                        }
                    }
                }.execute();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == consts.GET_CONTACTS_RESULTS){
            if(resultCode == RESULT_OK)
            {
                ArrayList<String> selectedFriends = (ArrayList<String>) data.getExtras().get(consts.SELECTED_FRIENDS);
                if(selectedFriends == null)
                    return;

                userGrid.setVisibility(View.VISIBLE);
                new ViewController().showThis(userGrid, Techniques.SlideInDown);

//                LinearLayout lin = new LinearLayout(this);
//                lin.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
//                lin.setOrientation(LinearLayout.HORIZONTAL);
//
//                LetterImageView l1 = new LetterImageView(this, null);
//                l1.setLayoutParams(new LinearLayoutCompat.LayoutParams(30, 30, 1));
//                l1.setOval(true);
//                l1.setLetter(selectedFriends.get(0).charAt(0));
//
//                TextView tv1 = new TextView(this);
//                l1.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 10));
//                tv1.setText(selectedFriends.get(0));
//
//                ImageView iv1 = new ImageView(this);
//                iv1.setLayoutParams(new LinearLayoutCompat.LayoutParams(20,20, 1));
//                iv1.setBackground(getDrawable(R.drawable.ic_money));
//                iv1.setAlpha(.4f);

//                lin.addView(iv1);
//                lin.addView(tv1);
//                lin.addView(l1);

//                LetterImageView l1 = new LetterImageView(this, null);
//                l1.setLayoutParams(new LinearLayoutCompat.LayoutParams(30, 30));
//                l1.setOval(true);
//                l1.setLetter(selectedFriends.get(0).charAt(0));
//
//                LetterImageView l2 = new LetterImageView(this, null);
//                l2.setLayoutParams(new LinearLayoutCompat.LayoutParams(30, 30));
//                l2.setOval(true);
//                l2.setLetter(selectedFriends.get(1).charAt(0));
//
//                LetterImageView l3 = new LetterImageView(this, null);
//                l3.setLayoutParams(new LinearLayoutCompat.LayoutParams(30,30));
//                l3.setOval(true);
//                l3.setLetter(selectedFriends.get(2).charAt(0));
//
//                userGrid.addView(l1);
//                userGrid.addView(l2);
//                userGrid.addView(l3);

                TextView f1 = (TextView) userGrid.findViewById(R.id.friend_1_text);
                TextView f2 = (TextView) userGrid.findViewById(R.id.friend_2_text);
                TextView f3 = (TextView) userGrid.findViewById(R.id.friend_3_text);

                friend_1.setOval(true);
                friend_1.setTextColor(R.color.white);
                friend_1.setLetter(selectedFriends.get(0).charAt(0));
                friend_2.setOval(true);
                friend_2.setTextColor(R.color.white);
                friend_2.setLetter(selectedFriends.get(1).charAt(0));
                friend_3.setOval(true);
                friend_3.setTextColor(R.color.white);
                friend_3.setLetter(selectedFriends.get(2).charAt(0));

                viewController
                        .showThis(friend_1, Techniques.SlideInLeft)
                        .showThis(friend_2, Techniques.SlideInLeft)
                        .showThis(friend_3, Techniques.SlideInLeft)
                        .showThis(f1, Techniques.FadeIn)
                        .showThis(f2, Techniques.FadeIn)
                        .showThis(f3, Techniques.FadeIn);

                f1.setText(selectedFriends.get(0));
                f2.setText(selectedFriends.get(1));
                f3.setText(selectedFriends.get(2));
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
        switch (view.getId()) {
            case R.id.users_list: {
  //              try {

//                    doneSelectingFriends.setVisibility(View.VISIBLE);
//                    UserAdapter adapter = (UserAdapter) friendsList.getAdapter();
//                    String item = adapter.get .getItem(position).toString();
//                    String existing;
//                    if (friends.getText() != null)
//                        existing = friends.getText().toString();
//                    else
//                        existing = "";
//
//                    String selected = existing.equals("")
//                            ? item
//                            : existing.contains(item) ? existing : existing + ("," + item);

//                    if (selected != null && selected != "")
//                        friends.setText(selected);
//                    else
//                        friends.setText("no one chosen");
//                } catch (Exception e) {
//                    e.printStackTrace();
 //             }
                break;
            }
        }
    }

}
