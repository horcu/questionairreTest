package com.horcu.apps.peez.view;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.horcu.apps.peez.R;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.gcm.BaseMessage;

import com.horcu.apps.peez.gcm.PubSubHelper;
import com.horcu.apps.peez.model.MessageEntry;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.viewmodel.MessageViewModel;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainView extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(realmConfig);
        realm = Realm.getInstance(realmConfig);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        settings = getSharedPreferences("Peez", 0);
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
                        String newMessage = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);

                        if(newMessage.contains("canonical_ids")) //This means the message was sent from this device TODO - check if this is the correct way
                            return;

                        String dateTime = newMessage.substring(0,17);
                      String message = newMessage.substring(17,newMessage.length());

                        List<Fragment> fragments = getSupportFragmentManager().getFragments();
                        Fragment frag = null;
                        for (Fragment fragment : fragments) {
                            if (fragment instanceof ChatView) {
                                frag = fragment;
                            }
                        }
                        ChatView ChatFrag = ((ChatView)frag);

                        ChatFrag.binding.getMsgViewModel().messageViewModels.add(new MessageViewModel(new MessageEntry(new Date().toString(), message)));
                        ChatFrag.binding.activityUsersRecycler.getAdapter().notifyDataSetChanged();
                        int msgCount = ChatFrag.binding.activityUsersRecycler.getAdapter().getItemCount();
                        ChatFrag.binding.activityUsersRecycler.smoothScrollToPosition(msgCount -1);

                        //save to db
                        BaseMessage msg = MessageSender.BuildBaseMessageFromJsonMEssage("", "", message, dateTime);
                        realm.beginTransaction();
                        realm.copyToRealm(msg);
                        realm.commitTransaction();
                        break;
                }
            }
        };
        mViewPager.setCurrentItem(1);
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
                    return ChatView.newInstance();
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
