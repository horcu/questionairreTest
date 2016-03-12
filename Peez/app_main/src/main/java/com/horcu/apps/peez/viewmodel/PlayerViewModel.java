package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.custom.CircleTransform;
import com.squareup.picasso.Picasso;


public class PlayerViewModel extends BaseObservable
{
    private Player model;

    public PlayerViewModel(Player model)
    {
        this.model = model;
    }

    public PlayerViewModel() {
    }

    public Player getModel()
    {
        return model;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(com.horcu.apps.peez.custom.UserImageView view, String imageUrl) {

        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_player)
                .transform(new CircleTransform())
                .into(view);
    }
}
