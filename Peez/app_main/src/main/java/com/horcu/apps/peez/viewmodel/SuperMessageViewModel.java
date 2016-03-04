package com.horcu.apps.peez.viewmodel;


import com.horcu.apps.peez.model.MessageEntry;

public class SuperMessageViewModel extends MessageViewModel
{
    public SuperMessageViewModel(MessageEntry model)
    {
        super(model);
    }

    public String getGroup()
    {
        return "Droid";
    }
}
