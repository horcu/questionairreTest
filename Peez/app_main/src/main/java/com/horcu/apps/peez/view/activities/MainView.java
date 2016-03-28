package com.horcu.apps.peez.view.activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.horcu.apps.peez.Dtos.InviteDto;
import com.horcu.apps.peez.Dtos.SmsDto;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.boardApi.model.Board;
import com.horcu.apps.peez.backend.models.moveApi.model.Move;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.backend_gameboard.gameApi.model.Game;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.Dtos.MMDto;
import com.horcu.apps.peez.custom.Gameboard.SoundPoolManager;
import com.horcu.apps.peez.custom.ISoundPoolLoaded;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.custom.PeezViewPager;
import com.horcu.apps.peez.data.DbEntityBuilder;
import com.horcu.apps.peez.data.DbHelper;
import com.horcu.apps.peez.gcm.core.PubSubHelper;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.model.GameEntry;
import com.horcu.apps.peez.model.MessageEntry;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.view.fragments.SettingsView;
import com.horcu.apps.peez.view.fragments.ChatView;
import com.horcu.apps.peez.view.fragments.FeedView;
import com.horcu.apps.peez.view.fragments.GameView;
import com.horcu.apps.peez.viewmodel.PlayerViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainView extends BaseView
        implements ChatView.OnFragmentInteractionListener,
        FeedView.OnFragmentInteractionListener, GameView.OnFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String gameTopic = "test123"; //TODO this should come from a pre populated list or a variable passed in when the frag is created
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public PeezViewPager mViewPager;
    private SharedPreferences settings;
    private PubSubHelper pubsub ;
    private BroadcastReceiver mLoggerCallback;
    private LoggingService.Logger mLogger;
    private SenderCollection mSenders;

    private String mytoken;
    private ArrayList<GameEntry> gamesInProgress = null;
    public Player opponent;
    public String gameKey;
    private int favoriteColor;

    private boolean newGameBeingCreated;
    private RealmConfiguration realmConfig;
    public  Realm realm;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        settings = getSharedPreferences(consts.PEEZ, 0);
        opponent = new Player();
        mytoken = settings.getString(consts.REG_ID,"");
        favoriteColor = getResources().getIntArray(R.array.Colors)[settings.getInt(consts.FAV_COLOR, 0)];



        realmConfig = new RealmConfiguration.Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .name(consts.REALM_CONFIG_NAME)
                .schemaVersion(consts.REALM_SCHEMA_VERSION)
                .setModules(Realm.getDefaultModule())
                //.migration(new MyRealmMigration())
                .build();

        realm = Realm.getInstance(realmConfig);

        dbHelper = new DbHelper(realm);

        SoundPoolManager.CreateInstance();
        List<Integer> sounds = new ArrayList<>();
        sounds.add(R.raw.back);
        sounds.add(R.raw.bite);
        sounds.add(R.raw.collect);
        sounds.add(R.raw.pop);
        sounds.add(R.raw.regular);
        sounds.add(R.raw.snare);
        SoundPoolManager.getInstance().setSounds(sounds);
        try {
            SoundPoolManager.getInstance().InitializeSoundPool(this, new ISoundPoolLoaded() {
                @Override
                public void onSuccess() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        SoundPoolManager.getInstance().setPlaySound(true);

        if(getActionBar() != null)
            getActionBar().hide();

       // GetInProgressGamesFromDbAsync();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        try {
            mViewPager = (PeezViewPager) findViewById(R.id.container);
            if (mViewPager != null) {
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mViewPager.setCurrentItem(0);
                mViewPager.setPagingEnabled(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        pubsub= new PubSubHelper(this);

        mLogger = new LoggingService.Logger(this);
        mSenders  = SenderCollection.getInstance(getApplicationContext());

        InitBroadcastReceiver();
        changeStatusbarAndNavigationbarColor();
    }

    private void changeStatusbarAndNavigationbarColor() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(favoriteColor);
            getWindow().setStatusBarColor(favoriteColor);
        }
    }

    private void InitBroadcastReceiver() {
        mLoggerCallback = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case LoggingService.ACTION_CLEAR_LOGS:
                        break;
                    case LoggingService.ACTION_LOG:
                        String messageObject = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);
                       String timeSent = messageObject.substring(1,18).trim();
                        String messageJson = messageObject.substring(18, messageObject.length());

                        String messageType = intent.getStringExtra(LoggingService.MESSAGE_TYPE);

                        if(messageJson.contains("multicast_id")) //return its the last message you sent out. use it to update whether the message was sent successfully or not
                        {
                            UpdateUI(messageJson);
                            return;
                        }

                        JSONObject json = null;

                        if(messageType == null)
                            try {
                                 json = new JSONObject(messageJson);
                                messageType = json.getString("type");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        if (messageType != null) {
                            switch (messageType)
                            {
                                case LoggingService.MESSAGE_TYPE_MSG :
                                {
                                    try {
                                        SmsDto dto = null;
                                        if (json != null) {
                                            dto = new SmsDto(
                                                    json.getString("gameId"),
                                                    json.getString("from"),
                                                    json.getString("to"),
                                                    json.getString("message"),
                                                    json.getString("dateTime"),
                                                    json.getString("senderUrl"));
                                        }

                                        String jsonStr = MessageSender.JsonifySmsDto(dto);
                                        Message sms = MessageSender.BuildSmsMessage(dto, jsonStr);
                                        HandleSMS(sms);
                                    } catch (JsonSyntaxException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }

                                case LoggingService.MESSAGE_TYPE_MOVE :
                                {
                                    try {
                                        MMDto dto = new MMDto(
                                                json.getString("moveFrom"),
                                                json.getString("moveTo"),
                                                json.getString("message"),
                                                json.getString("dateTime"),
                                                mytoken,
                                                json.getString("receiverToken"),
                                                json.getString("senderUrl"),
                                                json.getInt("color"),
                                                json.getString("collapseKey"));

                                        String jsonStr = MessageSender.JsonifyMoveDto(dto);
                                        Message moveMessage = MessageSender.BuildMoveMessage(dto, jsonStr);

                                        HandlePlayerMove(moveMessage);
                                    } catch (JsonSyntaxException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }

                                case LoggingService.MESSAGE_TYPE_INVITATION :
                                {
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

                                        mViewPager.setCurrentItem(1);
                                        HandleInvitation(inviteMessage);
                                    } catch (JsonSyntaxException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }

                                case LoggingService.MESSAGE_TYPE_REMINDER:
                                {
                                    mViewPager.setCurrentItem(0);
                                   // HandleReminder(newMessage);
                                    break;
                                }
                            }
                        }
                        else
                        {

                        }
                }
            }
        };
    }

    private void UpdateUI(String messageJson) {

    }

    private void GetInProgressGamesFromDbAsync() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                try {
                    RealmResults<GameEntry> games = realm.where(GameEntry.class).equalTo("inProgress", true).findAll();
                    gamesInProgress.addAll(games);
                } catch (Exception e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
              //  if (result.equals("")) {
                    //we're good
             //   } else {
            //    }

            }
        }.execute();
    }

    private void HandleSMS(Message msg){
        if(msg.getFrom().equals(mytoken))
            return;

        ChatView ChatFrag = GetChatFragment();
        MessageEntry me = DbEntityBuilder.BuildMessageEntryRecord(msg, realm);
        dbHelper.saveToDb(me);

       if (ChatFrag != null) {
            ChatFrag.refreshMessagesFromDb(mytoken,this);
        }
    }

    private void HandlePlayerMove(Message move){

        GameView gameFrag = GetGameFragment();
        gameFrag.ShowMoveOnBoard(move);
        gameFrag.setPlayerTurn(move.getTo());
    }

    private void HandleInvitation(Message message){

        goToaPage(consts.PAGE_GAME);
        GameView gameFrag = GetGameFragment();

        Message moveMessage = InVitationAccepted(message);

        //TODO -- this would get moved to the result from a dialog selection
        if(moveMessage !=null)
        {
            GenerateInvitationRecordOnServer();

            gameFrag.ShowMoveOnBoard(moveMessage);
        }
        else
        {

        }
    }

    private void GenerateInvitationRecordOnServer() {

    }

    private Message InVitationAccepted(Message message) {
        try {
            Message newMessage = new Message();
            Random r = new Random(0);
            newMessage.setType(LoggingService.MESSAGE_TYPE_MOVE);

            Map<String,String> data = message.getData();
            JSONObject json = new JSONObject(data);
            json.put("moveFrom", "0");
            json.put("moveTo", String.valueOf(r.nextInt(5)));
            return newMessage;
        } catch(JSONException e) {
          e.printStackTrace();
        }
         return null;
    }

    private void goToaPage(int page) {
        mViewPager.setCurrentItem(page);
    }

    private void HandleReminder(Message message){}

    @Nullable
    private ChatView GetChatFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment frag = null;
        for (Fragment fragment : fragments) {
            if (fragment instanceof ChatView) {
                frag = fragment;
            }
        }
        return ((ChatView)frag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPlaySoundEffects(SoundEffects effect) {

        if(effect == SoundEffects.EFFECT_SWAP)
            SoundPoolManager.getInstance().playSound(R.raw.pop);
        else if(effect == SoundEffects.EFFECT_EAT)
            SoundPoolManager.getInstance().playSound(R.raw.bite);
        else if(effect == SoundEffects.EFFECT_BACK)
            SoundPoolManager.getInstance().playSound(R.raw.back);
        else if(effect == SoundEffects.EFFECT_SNARE)
            SoundPoolManager.getInstance().playSound(R.raw.snare);
        else
            SoundPoolManager.getInstance().playSound(R.raw.regular);
    }

    @Override
    protected void onDestroy() {
        mLogger.unregisterCallback(mLoggerCallback);
        realm.close();
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

    @Override
    public void onFragmentInteraction(String newMessage) {

    }

    //From the feed fragment
    @Override
    public void onInitiateNewGame(PlayerViewModel pvm) {

        try {
            newGameBeingCreated = true;

            String me = GetPlayerId();

            //generate new board
            Board board = BuildNewBoard();

            //get a new game Id
            String gameId = UUID.randomUUID().toString();

            //build the new game object
            Game game = BuildGame(gameId, board.getBoardKey(),me,  pvm.getModel().getEmail());

            //build the invitation object
            String newInvitationId = BuildInvitation(board.getBoardKey(),gameId, me, pvm.getModel());

            //build Db record
            GameEntry newGame = DbEntityBuilder.BuildGameEntryRecord(game, realm);

            //set the global current game variable
            setGameKey(game.getGameId());

            //move to that tab
            mViewPager.setCurrentItem(1);

            //update to the current opponent
            GameView gameFrag = GetGameFragment();
            if(gameFrag !=null)
                gameFrag.UpdateGameInfo(gameId,pvm.getModel().getUserName(), pvm.getModel().getToken(),pvm.getModel().getImageUri(), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PlayMove(Boolean first, Move move){

//        Move move = MakeFirstMove(game, pvm);
//
//        sendGameInviteToOpponent(newInvitationId, move);
//
//        dbHelper.saveToDb(DbEntityBuilder.BuildGameEntryRecord(game, realm));
//
//        Toast.makeText(this, "saved invite to db", Toast.LENGTH_SHORT).show();
//        RefreshPager();
//
//        //TODO this will be a good call when you get everything to work with the invite then we can tack on the first move and update the local app to show the first move
//        // GameView gFrag = GetGameFragment();
//        // gFrag.ShowMoveOnBoard();
    }


    private String GetPlayerId() {
        return settings.getString(consts.PREF_ACCOUNT_NAME, "user");
    }

    private Board BuildNewBoard() {
        Board board = new Board();
        String jsonDef = GenerateGameboardJsonDefinition();
        board.setJsonTileDefinition(jsonDef);
        return board;
    }

    private String GenerateGameboardJsonDefinition() {

        return "";
    }

    private String BuildInvitation(String boardId, String gameId, String newBoardId, Player model) {
        return "";
    }

    private Game BuildGame(String gameId, String boardId, String senderEmail, String receiverEmail) {
        Game game = new Game();
        game.setGameId(gameId);
        game.setBoardKey(boardId);
        game.setSender(senderEmail);
        game.setReceiver(receiverEmail);
        game.setPlayerTurn(receiverEmail);
        return game;
    }

    private Move MakeFirstMove(Game newGame, PlayerViewModel pvm) {


        return null;
    }

    private void RefreshPager() {
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    private void sendGameInviteToOpponent(String invitationToken, Move move) {
        try {

            MessageSender sender = new MessageSender(getApplicationContext(), mLogger,mSenders);

            int color = GetFavoriteColor();
            String moveString = new Gson().toJson(move);

            String collapseKey = UUID.randomUUID().toString();
            InviteDto dto = new InviteDto("wanna play ?", new Date().toString(), mytoken, opponent.getToken(), invitationToken, "", moveString,color ,collapseKey);
            String jsonString = MessageSender.JsonifyInviteDto(dto);
            Message message = MessageSender.BuildInvitationMessage(dto, jsonString);

            if(sender.SendInvitation(message))
            Toast.makeText(getApplicationContext(),  "invitation sent", Toast.LENGTH_SHORT).show();
            else
            Toast.makeText(getApplicationContext(),  "invitation NOT sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private int GetFavoriteColor() {
        if(settings == null)
            settings = getSharedPreferences("Peez", 0);

        int savedColorIndex = settings.getInt(consts.FAV_COLOR, 0) != 0
                ? settings.getInt(consts.FAV_COLOR, 0)
                : Color.LTGRAY;

        int[] colorArray = getResources().getIntArray(R.array.Colors);
        int choice = colorArray[savedColorIndex];
        return choice;
    }

    private Boolean CheckifGameIdUniqueAsync(String token, String mytoken) {
        return true; //TODO do this for real in an async method.. no return obv if async
    }

    @Override
    public void onSwitchCurrentGame(String gameId, PlayerViewModel pvm) {
        Player player = pvm.getModel();

      //  if(IsAtLeastOneGameInProgress()) {

        if(!gameId.equals(""))
            setGameKey(gameId);

            UpdateOpponent(player.getUserName(),player.getImageUri(),player.getToken());

            goToaPage(1);


        ChatView ChatFrag = GetChatFragment();
        if (ChatFrag != null) {
            ChatFrag.upDateChatPlayer(gameId, player.getUserName(),player.getToken(),player.getImageUri());
           // ChatFrag.refreshMessagesFromDb(gameId, this);
        }

        GameView GameFrag = GetGameFragment();
        if (GameFrag != null) {
            GameFrag.UpdateGameInfo(gameId, player.getUserName(),player.getToken(),player.getImageUri(), false);
        }
    }

    @Override
    public void onSetPlayerTurn(PlayerViewModel playerVm) {

        GameView gameFrag = GetGameFragment();
        gameFrag.setPlayerTurn(playerVm.getModel().getToken());
    }

    private void UpdateOpponent(String opponentName, String opponentImgUrl, String opponentToken) {
        opponent.setImageUri(opponentImgUrl);
        opponent.setToken(opponentToken);
        opponent.setUserName(opponentName);
    }

    private String getInProgressGameId() {
        return null;
    }

    private void setOpponent(Player player)
    {
        opponent = player;
    }
    private Player getOpponent()
    {
        return opponent;
    }

    private void setGameKey(String gameKey)
    {
        this.gameKey = gameKey;
    }
    private String getGameKey()
    {
        return this.gameKey;
    }

    private boolean IsAtLeastOneGameInProgress() {
        return gamesInProgress != null && gamesInProgress.size() > 0;
    }

    private GameView GetGameFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment frag = null;
        for (Fragment fragment : fragments) {
            if (fragment instanceof GameView) {
                frag = fragment;
            }
        }
        return ((GameView) frag);
    }

    public Realm getRealm() {
        return realm;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position)
            {
                case 0:
                    return FeedView.newInstance();
                case 1:
                    return GameView.newInstance();
                case 2:
                    return ChatView.newInstance("","","");
                case 3:
                    return SettingsView.newInstance();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            //if(IsAtLeastOneGameInProgress())
            return 3;
           // else
           // return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
    }

    public enum SoundEffects{
        EFFECT_REG, EFFECT_EAT, EFFECT_SWAP, EFFECT_BACK, EFFECT_SNARE
    }
}
