package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;

import com.horcu.apps.peez.model.MessageEntry;


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
}
