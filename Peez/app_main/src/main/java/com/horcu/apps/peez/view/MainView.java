package com.horcu.apps.peez.view;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.InvitationMessage;
import com.horcu.apps.peez.gcm.MoveMessage;
import com.horcu.apps.peez.gcm.PubSubHelper;
import com.horcu.apps.peez.gcm.ReminderMessage;
import com.horcu.apps.peez.gcm.SmsMessage;
import com.horcu.apps.peez.service.LoggingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class MainView extends baseview
        implements ChatView.OnFragmentInteractionListener,
        FeedView.OnFragmentInteractionListener, GameView.OnFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String gameTopic = "test123"; //TODO this should come from a pre populated list or a variable passed in when the frag is created
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private SharedPreferences settings;
    private PubSubHelper pubsub ;
    private BroadcastReceiver mLoggerCallback;
    private LoggingService.Logger mLogger;
    private Realm realm;
    private RealmConfiguration realmConfig;
    private String mytoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        settings = getSharedPreferences("Peez", 0);

        mytoken = settings.getString(consts.REG_ID,"");

        realmConfig = new RealmConfiguration.Builder(this).build();

        if(consts.DEV_MODE)
        Realm.deleteRealm(realmConfig);

        realm = Realm.getInstance(realmConfig);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        pubsub= new PubSubHelper(this);
        //pubsub.subscribeTopic(consts.SENDER_ID,settings.getString(consts.REG_ID,""),gameTopic, new Bundle());

        mLogger = new LoggingService.Logger(this);

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

                        Gson gson = new Gson();

                        String messageType = intent.getStringExtra(LoggingService.MESSAGE_TYPE)  != null ? intent.getStringExtra(LoggingService.MESSAGE_TYPE) : LoggingService.MESSAGE_TYPE_MSG;

                        if(messageJson.contains("multicast_id")) //return its the last message you sent out
                        {
                            return;}

                        switch (messageType)
                        {
                            case LoggingService.MESSAGE_TYPE_MSG :
                            {
                                try {
                                    JSONObject jsonO = new JSONObject(messageJson);
                                    SmsMessage sms = new SmsMessage(jsonO.getString("from"),jsonO.getString("to"),jsonO.getString("message"),jsonO.getString("dateTime"),jsonO.getString("senderUrl"));

                                    HandleSMS(sms);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }

                            case LoggingService.MESSAGE_TYPE_MOVE :
                            {
                                MoveMessage newMessage = gson.fromJson(messageObject, MoveMessage.class);
                                mViewPager.setCurrentItem(1);
                                HandleMove(newMessage);
                                break;
                            }

                            case LoggingService.MESSAGE_TYPE_INVITATION :
                            {
                                InvitationMessage newMessage = gson.fromJson(messageObject, InvitationMessage.class);
                                mViewPager.setCurrentItem(1);
                                HandleInvitation(newMessage);
                                break;
                            }

                            case LoggingService.MESSAGE_TYPE_REMINDER:
                            {
                                ReminderMessage newMessage = gson.fromJson(messageObject, ReminderMessage.class);
                                mViewPager.setCurrentItem(0);
                                HandleReminder(newMessage);
                                break;
                            }
                        }
                }
            }
        };

    }

    private Boolean saveToDb(RealmObject obj){
        try {
            realm.beginTransaction();
            realm.copyToRealm(obj);
            realm.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private void HandleSMS(SmsMessage msg){
        if(msg.getFrom().equals(mytoken))
            return;

        String dateTime = msg.getDateTime();
        String message = msg.getMessage();

        ChatView ChatFrag = GetChatFragment();
        //save to db
        saveToDb(msg);

        //refresh List from db and update ui
        ChatFrag.refreshMessagesFromDb(mytoken,this);
    }
    private void HandleInvitation(InvitationMessage message){}
    private void HandleMove(MoveMessage message){}
    private void HandleReminder(ReminderMessage message){}


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


    @Override
    public void onFragmentInteraction(String newMessage) {

    }


    //From the feed fragment
    @Override
    public void onFragmentInteraction(String name, String imageUrl, String token) {

        ChatView ChatFrag = GetChatFragment();
        if (ChatFrag != null) {
            ChatFrag.upDateChatPlayer(name,token,imageUrl);
            ChatFrag.refreshMessagesFromDb(ChatFrag.myToken,this);
            mViewPager.setCurrentItem(2);
        }
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
            return 3;
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
}
