package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

public class PlayersViewModel extends BaseObservable
{
    @Bindable
    public ObservableArrayList<PlayerViewModel> playersVMs;

    public PlayersViewModel()
    {
        this.playersVMs = new ObservableArrayList<>();
    }

}
