package com.horcu.apps.peez.binder;

import com.horcu.apps.peez.viewmodel.MessageViewModel;
import com.horcu.apps.peez.viewmodel.PlayerViewModel;
import com.horcu.apps.peez.viewmodel.SuperMessageViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

public class SuperPlayerBinder extends ConditionalDataBinder<PlayerViewModel>
{
    public SuperPlayerBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(PlayerViewModel model)
    {
        return model instanceof PlayerViewModel;
    }
}
