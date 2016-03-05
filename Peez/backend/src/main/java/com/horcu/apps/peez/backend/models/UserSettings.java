package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import java.util.List;

/**
 * Created by hacz on 10/10/2015.
 */

@Entity
public class UserSettings {

    public UserSettings(){}

    @Id
    private Long id;
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
