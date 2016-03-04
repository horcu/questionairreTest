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

    public void addUser(String name, String surname, boolean superUser)
    {
        if(superUser)
        this.playersVMs.add(new PlayerViewModel(new com.horcu.apps.peez.model.Player(name, surname) {
        }));
        else
        this.playersVMs.add(new WinnerViewModel(new com.horcu.apps.peez.model.Player(name, surname)));
    }

    public void clearEditText() {

    }
}
