package com.horcu.apps.peez.backend.models;

import java.util.List;

/**
 * Created by hacz on 10/10/2015.
 */

public class UserSettings {

    public UserSettings(){}


    private String name;


    private String imgUrl;

    private List<String> friendsEmails;
    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public List<String> getFriendsEmails() {
        return friendsEmails;
    }

    public void setFriendsEmails(List<String> friendsEmails) {
        this.friendsEmails = friendsEmails;
    }
}
