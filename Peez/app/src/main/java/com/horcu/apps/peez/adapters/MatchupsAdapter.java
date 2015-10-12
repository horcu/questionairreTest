package com.horcu.apps.peez.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.model.nfl.league.Team;
import com.horcu.apps.peez.model.nfl.schedule.Game;
import com.horcu.apps.peez.model.nfl.schedule.NflWeek;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hcummings on 10/9/2015.
 */
public class MatchupsAdapter extends RecyclerView.Adapter<MatchupsAdapter.ViewHolder> {
    private com.horcu.apps.peez.backend.models.nflWeekApi.model.NflWeek mDataset;
    private Context mContext;
    private int itemRow;
    private String mGames;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mParentView;
        public ImageView awayTeamImageView;
        public ImageView homeTeamImageView;

        public ViewHolder(LinearLayout v) {
            super(v);
            mParentView = v;
        }
    }

    public MatchupsAdapter(com.horcu.apps.peez.backend.models.nflWeekApi.model.NflWeek myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
        if(mDataset !=null)
        mGames = mDataset.getGames();
        //  itemRow = ItemRow;

    }

    @Override
    public MatchupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        LinearLayout v = (LinearLayout)mInflater.inflate(R.layout.view_team, parent, false);
        final LinearLayout row = (LinearLayout) mInflater.inflate(R.layout.team_list_item, v, true);

        ViewHolder vh = new ViewHolder(v);
        vh.awayTeamImageView = (ImageView) row.findViewById(R.id.away_image);
        vh.homeTeamImageView = (ImageView)row.findViewById(R.id.home_image);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mDataset !=null) {
            mGames = mDataset.getGames();

            Game game = new Gson().fromJson(mGames,new TypeToken<List<Game>>(){}.getType());
            String awayTeam = game.getAway();
            String homeTeam = game.getHome();


            Picasso.with(mContext)
                    .load(Uri.parse("https://console.developers.google.com/m/cloudstorage/b/ballrz/o/images/bengals_away.png"))
                    .resize(40, 40)
                    .centerInside()
                    .into(holder.awayTeamImageView);

            Picasso.with(mContext)
                    .load(Uri.parse("https://console.developers.google.com/m/cloudstorage/b/ballrz/o/images/ravens_home.jpg"))
                    .resize(40, 40)
                    .centerInside()
                    .into(holder.homeTeamImageView);
            //   holder.mTextView.setText(mDataset[position]);
        }

    }

    @Override
    public int getItemCount() {
        if(mGames!= null)
      return 16; // return mGames.size();
        return 0;
    }
}
