package com.horcu.apps.peez.binder;

import com.horcu.apps.peez.viewmodel.MessageViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

public class MessageBinder extends ConditionalDataBinder<MessageViewModel>
{
    public MessageBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(MessageViewModel model)
    {
        return true;
    }
}
