package com.horcu.apps.peez.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.adapters.BetAdapter;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.backend.models.betApi.model.CollectionResponseBet;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.listener.RecyclerItemClickListener;

import java.io.IOException;
import java.util.List;



public class FeedActivity extends BaseActivity {

    private BetAdapter mBetAdapter;
    private RecyclerView betList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);
       // FragmentFeedBinding feed = DataBindingUtil.setContentView(this, R.layout.fragment_feed);
       // ButterKnife.inject(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        betList = (RecyclerView)findViewById(R.id.bets_list);


        getAllBets(); //pass email address

       
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedActivity.this, BetActivity.class);
                startActivity(intent);
            }
        });
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        betList.setLayoutManager(new LinearLayoutManager(this));
        betList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, float x, float y) {
                Intent intent = new Intent(FeedActivity.this, BetActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void getAllBets() {


        new AsyncTask<Void, Void, Void>() {
            public List<Bet> mBets;

            @Override
            protected Void doInBackground(Void... params) {
                BetApi betService = Api.BuildBetApiService();
                try {
                    CollectionResponseBet collection = betService.list().execute();
                    if(collection !=null)
                    mBets = collection.getItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if (mBetAdapter == null) {
                    mBetAdapter = new BetAdapter(mBets);
                    betList.setAdapter(mBetAdapter);
                } else {
                    mBetAdapter = new BetAdapter(mBets);
                    betList.setAdapter(mBetAdapter);
                    mBetAdapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }


    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void displayProgress(boolean display) {

    }

}
