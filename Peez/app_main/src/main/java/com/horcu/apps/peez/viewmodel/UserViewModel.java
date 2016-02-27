package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;

import com.horcu.apps.peez.model.Player;


public class UserViewModel extends BaseObservable
{
    private final Player model;

    public UserViewModel(Player model)
    {
        this.model = model;
    }

    public Player getModel()
    {
        return model;
    }

    public String getFirstName()
    {
        return model.getFirstName();
    }

    public String getLastName()
    {
        return model.getLastName();
    }
}
