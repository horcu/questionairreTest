package com.horcu.apps.peez.steps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.adapters.MatchupsAdapter;
import com.horcu.apps.peez.backend.models.nflWeekApi.NflWeekApi;
import com.horcu.apps.peez.backend.models.nflWeekApi.model.NflWeek;
import com.horcu.apps.peez.model.nfl.schedule.Game;


import java.io.IOException;
import java.util.List;


/**
 * Created by hacz on 10/8/2015.
 */
public class TeamStep extends Step {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private NflWeek myDataset;
    private NflWeekApi nflWeekApi;
    private NflWeek thisWeek;

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
        try {
            loadTheme();

            LinearLayout layout = (LinearLayout) View.inflate(getContext(), R.layout.view_team, null);
            mRecyclerView = (RecyclerView) layout.findViewById(R.id.matchup_list);
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(layout.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            setWeekToDisplay();

            // specify an adapter (see also next example)
            mAdapter = new MatchupsAdapter(thisWeek, getContext());
            mRecyclerView.setAdapter(mAdapter);
            //Type listType = new TypeToken<List<Team>>() {}.getType();


            return layout;

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void setWeekToDisplay() {

        if (nflWeekApi == null) {
            BuildStatsApi();
        }
        GetCurrentWeekScheduleAsync();
    }

    private void GetCurrentWeekScheduleAsync() {
        new AsyncTask<Void, Void, NflWeek>() {

            protected NflWeek doInBackground(Void... params) {
               NflWeek info = null;
                try {

                    info = nflWeekApi.current().execute(); //TODO get date through matching file
                    convertToWeek(info);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                if (info != null) {
                    //transform NFLInfo into NFLWeek they are the same

                }
                return null;
            }

            @Override
            protected void onPostExecute(NflWeek week) {
                if(week !=null)
                thisWeek = week;
                else
                thisWeek = null;
            }
        }.execute();
    }

    private void convertToWeek(com.horcu.apps.peez.backend.models.nflWeekApi.model.NflWeek info) {
        List<Game> games = null;
        if(info !=null && info.getGames() !=null)
        {
            games = new Gson().fromJson(info.getGames(),new TypeToken<List<Game>>(){}.getType());
        }

        if(games !=null)
        thisWeek = new NflWeek()
                .setGames(info.getGames())
                .setWeekNumber(info.getWeekNumber());
    }

    private void BuildStatsApi() {
        try {
            NflWeekApi.Builder builder = new NflWeekApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            nflWeekApi = builder.build();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
