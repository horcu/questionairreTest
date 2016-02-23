package com.horcu.apps.peez.model.app;

import android.databinding.BaseObservable;

import com.horcu.apps.peez.common.models.User;

/**
 * Created by Horatio on 2/16/2016.
 */
public class Friend extends BaseObservable {

    private String email;
    private String username;
    private String joinedOn;
    private String friendSince;
    private String telephone;
    private String registrationId;


   public Friend(com.horcu.apps.peez.backend.models.userApi.model.User user, String friendSince, String telephone){

       this.username = user.getUserName();
       this.email = user.getEmail();
       this.joinedOn = user.getJoined();
       this.registrationId = user.getRegistrationId();
       this.friendSince = friendSince;
       this.telephone = telephone;
   }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getFriendSince() {
        return friendSince;
    }

    public void setFriendSince(String friendSince) {
        this.friendSince = friendSince;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
