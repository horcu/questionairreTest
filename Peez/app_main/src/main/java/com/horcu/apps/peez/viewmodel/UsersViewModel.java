package com.horcu.apps.peez.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.horcu.apps.peez.model.Player;


public class UsersViewModel extends BaseObservable
{
    @Bindable
    public ObservableArrayList<UserViewModel> users;

    public UsersViewModel()
    {
        this.users = new ObservableArrayList<>();
    }

    public void addUser(String name, String surname)
    {
        this.users.add(new UserViewModel(new Player(name, surname)));
    }

    public void clearEditText() {

    }
}
