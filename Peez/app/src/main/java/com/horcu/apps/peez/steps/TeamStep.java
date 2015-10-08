package com.horcu.apps.peez.steps;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.horcu.apps.peez.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

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
        if (super.getView() instanceof FrameLayout) {
            return (LinearLayout) super.getView();
        }
        throw new ClassCastException("Input view must be Linearlayout");
    }

    @Override
    public View onCreateView() {
        loadTheme();

        FrameLayout layout = (FrameLayout) View.inflate(getContext(), R.layout.view_team, null);
        ListView teamsList = (ListView) layout.getChildAt(0);
        List<String> teams = new ArrayList<>();
        teams.add("Horatio Cummings");
        teams.add("Fran Tarkenton");
        teams.add("Joe Flacco");
        teamsList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.view_team, teams));
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
