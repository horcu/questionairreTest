package com.horcu.apps.peez.binder;

import com.horcu.apps.peez.viewmodel.SuperUserViewModel;
import com.horcu.apps.peez.viewmodel.UserViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.binder.ConditionalDataBinder;

public class SuperUserBinder extends ConditionalDataBinder<UserViewModel>
{
    public SuperUserBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(UserViewModel model)
    {
        return model instanceof SuperUserViewModel;
    }
}
