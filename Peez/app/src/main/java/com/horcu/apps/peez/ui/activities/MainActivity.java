package com.horcu.apps.peez.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.ui.fragments.testItemFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements testItemFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private User user;
    private BroadcastReceiver mLoggerCallback;
    private LoggingService.Logger mLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
            getSupportActionBar().setTitle("");
        }

        mLogger = new LoggingService.Logger(this);
        Bundle bundle = getIntent().getExtras();

        mLoggerCallback = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case LoggingService.ACTION_CLEAR_LOGS:
                      //  mLogsUI.setText("");
                        break;
                    case LoggingService.ACTION_LOG:
                        StringBuilder stringBuilder = new StringBuilder();
                        String newLog = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);
                      //  String oldLogs = Html.toHtml(new SpannableString(mLogsUI.getText()));
                     //   appendFormattedLogLine(newLog, stringBuilder);
                      //  stringBuilder.append(oldLogs);
                     //   mLogsUI.setText(Html.fromHtml(stringBuilder.toString()));
                        List<Fragment> fragments = getSupportFragmentManager().getFragments();
                        for (Fragment fragment : fragments) {
                            if (fragment instanceof GCMActivity.RefreshableFragment && fragment.isVisible()) {
                                ((GCMActivity.RefreshableFragment) fragment).refresh();
                            }
                        }

                        Snackbar.make(findViewById(R.id.bet_list), Html.fromHtml(newLog), Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

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

    private void appendFormattedLogLine(String log, StringBuilder stringBuilder) {
        String[] logLines = log.split("\n");
        if (logLines.length > 0) {
            logLines[0] = "<b>" + logLines[0] + "</b>";
            for (String line : logLines) {
                if (line.startsWith("exception: ")) {
                    continue;
                }
                int keySeparator = line.indexOf(": ");
                if (keySeparator > 0) {
                    stringBuilder
                            .append("<b>").append(line.substring(0, keySeparator + 1)).append
                            ("</b>")
                            .append(line.substring(keySeparator + 1)).append("<br>");
                } else {
                    stringBuilder.append(line).append("<br>");
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if( id == R.id.action_bet){
            Intent intent = new Intent(this, TestBetActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_invite){
            Intent intent = new Intent(this, InviteActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_msg)
        {
            Intent intent = new Intent(this, GCMActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            return testItemFragment.newInstance(user);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
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
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
