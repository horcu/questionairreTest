package com.horcu.apps.peez.steps;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.adapters.MatchupsAdapter;
import com.horcu.apps.peez.model.nfl.league.Conference;
import com.horcu.apps.peez.model.nfl.league.Division;
import com.horcu.apps.peez.model.nfl.league.GeneralInfo;
import com.horcu.apps.peez.model.nfl.league.Team;
import com.horcu.apps.peez.model.nfl.schedule.Game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacz on 10/8/2015.
 */
public class TeamStep extends Step {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Game myDataset;

    public TeamStep(Context context, String dataKey, int titleResId, int errorResId, int detailsResId) {
        super(context, dataKey, titleResId, errorResId, detailsResId);
    }

    public TeamStep(Context context, String dataKey, String title, String error, String details) {
        super(context, dataKey, title, error, details);
    }

    public TeamStep(Context context, String matchups, int teams, int matchup_title, int team_choice__error, int team_choice_details, StepChecker stepChecker) {
        super(context, matchups, teams, matchup_title, team_choice__error);
    }

    @Override
    public LinearLayout getView() {
        if (super.getView() instanceof LinearLayout) {
            return (LinearLayout) super.getView();
        }
        throw new ClassCastException("Input view must be Linearlayout");
    }

    @Override
    public View onCreateView() {
        loadTheme();

        LinearLayout layout = (LinearLayout) View.inflate(getContext(), R.layout.view_team, null);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.matchup_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(layout.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MatchupsAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        InputStream rawTeams =  getContext().getResources().openRawResource(R.raw.nfl_teams);
        InputStream rawMatchups =  getContext().getResources().openRawResource(R.raw.season_schedule_2015);

        Reader rd = new BufferedReader(new InputStreamReader(rawTeams));
        Reader rdmatchups = new BufferedReader(new InputStreamReader(rawMatchups));

        Gson gson = new Gson();
        //Type listType = new TypeToken<List<Team>>() {}.getType();

        GeneralInfo generalNflInfo = gson.fromJson(rd, GeneralInfo.class);

        List<Conference> conferences = generalNflInfo.getConferences();

        Conference AFC = conferences.get(0);
        List<Division> AFCDivisions = AFC.getDivisions();

        for (Division div: AFCDivisions)
        {
            for (Team t: div.getTeams()) {
            //    teams.add(t.getMarket() + " " + t.getName());
            }
        }

        Conference NFC = conferences.get(1);
        List<Division> NFCDivisions = NFC.getDivisions();

        for (Division div: NFCDivisions)
        {
            for (Team t: div.getTeams()) {
            //    teams.add(t.getMarket() + " " + t.getName());
            }
        }

        return layout;
    }

    @Override
    public void updateView(boolean lastStep) {

    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    protected void onSave() {

    }

    @Override
    protected void onRestore() {

    }

    private void loadTheme() {
        /* Custom values */
        int[] attrs = {android.R.attr.textColorPrimaryInverse};
        TypedArray array = getContext().obtainStyledAttributes(attrs);
        array.recycle();
    }

    public interface StepChecker {
        boolean check(String teamName, String teamId, String opponentName, String opponentId);
    }
}
