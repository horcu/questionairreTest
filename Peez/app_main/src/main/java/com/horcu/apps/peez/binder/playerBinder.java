package com.horcu.apps.peez.binder;

import com.horcu.apps.peez.viewmodel.PlayerViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

public class PlayerBinder extends ConditionalDataBinder<PlayerViewModel>
{
    public PlayerBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(PlayerViewModel model)
    {
        return true;
    }
}
