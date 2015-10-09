package com.horcu.apps.peez.steps;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.model.nfl.Conference;
import com.horcu.apps.peez.model.nfl.Division;
import com.horcu.apps.peez.model.nfl.GeneralInfo;
import com.horcu.apps.peez.model.nfl.Team;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hacz on 10/8/2015.
 */
public class TeamStep extends Step {
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
        ListView teamsList = (ListView) layout.getChildAt(0);
        List<String> teams = new ArrayList<>();

        //get json from the raw folder


        InputStream rawTeams =  getContext().getResources().openRawResource(R.raw.nfl_teams);
        InputStream rawMatchups =  getContext().getResources().openRawResource(R.raw.season_schedule_2015);
        Reader rd = new BufferedReader(new InputStreamReader(rawTeams));
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Team>>() {}.getType();
        GeneralInfo generalNflInfo = gson.fromJson(rd, GeneralInfo.class);

        List<Conference> conferencences = generalNflInfo.getConferences();

        Conference AFC = conferencences.get(0);
        List<Division> AFCDivisions = AFC.getDivisions();

        for (Division div: AFCDivisions)
        {
            for (Team t: div.getTeams()) {
                teams.add(t.getMarket() + " " + t.getName());
            }
        }

        Conference NFC = conferencences.get(1);
        List<Division> NFCDivisions = NFC.getDivisions();

        for (Division div: NFCDivisions)
        {
            for (Team t: div.getTeams()) {
                teams.add(t.getMarket() + " " + t.getName());
            }
        }

        teamsList.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, teams));
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
