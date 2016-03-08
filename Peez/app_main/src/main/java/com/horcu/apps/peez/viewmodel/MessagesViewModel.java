package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.horcu.apps.peez.model.MessageEntry;


public class MessagesViewModel extends BaseObservable
{
    @Bindable
    public ObservableArrayList<MessageViewModel> messageViewModels;

    public MessagesViewModel()
    {
        this.messageViewModels = new ObservableArrayList<>();
    }

//    public void addUser(String name, String surname, boolean superUser)
//    {
//        if(superUser)
//        this.messageViewModels.add(new SuperMessageViewModel(new MessageEntry(name, surname)));
//        else
//        this.messageViewModels.add(new MessageViewModel(new MessageEntry(name, surname)));
//    }
//
//    public void clearEditText() {
//
//    }
}
