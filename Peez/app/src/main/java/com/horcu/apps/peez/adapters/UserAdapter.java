package com.horcu.apps.peez.adapters;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.custom.LetterImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hacz on 10/14/2015.
 */

/**
 * Created by phanirajabhandari on 7/6/2015.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.BindingHolder> {
    private List<User> mUsers;
    private Context ctx;

    public static class BindingHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView selected;
        public LetterImageView userImage;

        public BindingHolder(View v) {
            super(v);
        }

    }

    public UserAdapter(List<User> recyclerUsers, Context context) {
        this.mUsers = recyclerUsers;
        this.ctx = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        BindingHolder holder = new BindingHolder(v);
        holder.userName = (TextView)v.findViewById(R.id.friend);
        holder.selected = (ImageView)v.findViewById(R.id.selected);
        holder.userImage = (LetterImageView)v.findViewById(R.id.user_img);

        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.userName.setText(user.getUserName());

            holder.userImage.setOval(true);
            holder.userImage.setLetter(user.getUserName().charAt(0));

            Picasso.with(ctx).load(user.getImageUri()).into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}