package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;

import com.horcu.apps.peez.model.Player;


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

}
