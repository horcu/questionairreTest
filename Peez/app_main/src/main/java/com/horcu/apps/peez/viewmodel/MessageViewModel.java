package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.custom.CircleTransform;
import com.horcu.apps.peez.model.MessageEntry;
import com.squareup.picasso.Picasso;


public class MessageViewModel extends BaseObservable
{
    private final MessageEntry model;

    public MessageViewModel(MessageEntry model)
    {
        this.model = model;
    }

    public MessageEntry getModel()
    {
        return model;
    }

    public String getFirstName()
    {
        return model.getDatetime();
    }

    public String getLastName()
    {
        return model.getMessage();
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
