package com.horcu.apps.peez;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

import me.tatarka.bindingcollectionadapter.sample.R;
import me.tatarka.bindingcollectionadapter.sample.databinding.ActivityMainBinding;
import me.tatarka.bindingcollectionadapter.sample.databinding.FragmentFeedBinding;

public class PeezActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peez);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_peez, menu);
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

    public static class GameboardFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public GameboardFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GameboardFragment newInstance() {
            GameboardFragment fragment = new GameboardFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_game, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.game_label);
            textView.setTypeface(Typer.set(getContext()).getFont(Font.ROBOTO_THIN));
            textView.setText("This is the gameboard page");
            return rootView;
        }
    }
    /**/
    public static class FeedFragment extends Fragment {

        FragmentFeedBinding binding = null;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FeedFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FeedFragment newInstance() {
            FeedFragment fragment = new FeedFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 0);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

           binding = FragmentFeedBinding.inflate(inflater,container, false);// DataBindingUtil.setContentView(getActivity(), R.layout.fragment_feed);
           View rootView = binding.getRoot();
            TextView textView = (TextView) rootView.findViewById(R.id.feed_label);
            textView.setTypeface(Typer.set(getContext()).getFont(Font.ROBOTO_THIN));
           textView.setText("This is the Feed page");
            return rootView;
        //    return binding.getRoot();
        }
    }
    /**/
    public static class ChatFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ChatFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ChatFragment newInstance() {
            ChatFragment fragment = new ChatFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.chat_label);
            textView.setTypeface(Typer.set(getContext()).getFont(Font.ROBOTO_THIN));
            textView.setText("This is the Chat page");
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FeedFragment();
                case 1:
                    return new GameboardFragment();
                case 2:
                    return new ChatFragment();
                default:
                    return new GameboardFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "feed";
                case 1:
                    return "game";
                case 2:
                    return "chat";
            }
            return null;
        }
    }
}
