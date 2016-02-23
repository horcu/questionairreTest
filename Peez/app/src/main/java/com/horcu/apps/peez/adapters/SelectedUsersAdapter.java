package com.horcu.apps.peez.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.custom.TileView;

import java.util.List;

/**
 * Created by hcummings on 11/16/2015.
 */
public class SelectedUsersAdapter extends BaseAdapter {
    private List<User> users;
    private Context mContext;

    // Gets the context so it can be used later
    public SelectedUsersAdapter(List<User> users, Context c) {
        this.users = users;
        mContext = c;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return users.size();
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position,
                        View convertView, ViewGroup parent) {
        ImageView userImage;
        TextView usernameTxt;
        TextView userWagerTxt;
        User usr = users.get(position);
        GridView v;

        if (convertView == null) {

           v = (GridView) LayoutInflater.from(mContext).inflate(R.layout.selected_user_grid_item, null);
        }
        else {
             v = (GridView) convertView;
        }
        TileView liv = new TileView(mContext, null);
        liv.setMinimumHeight(38);
        liv.setMinimumHeight(38);
        liv.setLetter(usr.getUserName().charAt(0));

        TextView tv = new TextView(mContext);
        tv.setText(usr.getUserName());

        v.addView(liv);
        v.addView(tv);
        v.setLayoutParams(new GridView.LayoutParams(100, 55));
        v.setPadding(2, 2, 2, 2);

        return v;
    }
}
