package com.horcu.apps.peez.viewmodel;


import com.horcu.apps.peez.model.Player;

public class WinnerViewModel extends PlayerViewModel
{
    private final Player model;

    public WinnerViewModel(Player model)
    {
        this.model = model;
    }

    public Player getModel()
    {
        return model;
    }

}
