package com.horcu.apps.peez.binder;

import com.horcu.apps.peez.viewmodel.MessageViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

public class UserBinder extends ConditionalDataBinder<MessageViewModel>
{
    public UserBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(MessageViewModel model)
    {
        return true;
    }
}
