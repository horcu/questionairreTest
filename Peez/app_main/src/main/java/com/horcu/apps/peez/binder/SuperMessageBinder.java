package com.horcu.apps.peez.binder;

import com.horcu.apps.peez.viewmodel.SuperMessageViewModel;
import com.horcu.apps.peez.viewmodel.MessageViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

public class SuperMessageBinder extends ConditionalDataBinder<MessageViewModel>
{
    public SuperMessageBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(MessageViewModel model)
    {
        return model instanceof SuperMessageViewModel;
    }
}
