package com.horcu.apps.peez.viewmodel;


import com.horcu.apps.peez.model.Player;

public class SuperUserViewModel extends UserViewModel
{
    public SuperUserViewModel(Player model)
    {
        super(model);
    }

    public String getGroup()
    {
        return "Droid";
    }
}
