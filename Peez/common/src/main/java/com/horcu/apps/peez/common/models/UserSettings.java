package com.horcu.apps.peez.common.models;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

/**
 * Created by hacz on 10/10/2015.
 */
@Entity
public class UserSettings {

    public UserSettings(){}

    @Id
    @Index
    private String name;

    @Index
    private String imgUrl;

    private List<Text> friendsEmails;

    @Index
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


    public List<Text> getFriendsEmails() {
        return friendsEmails;
    }

    public void setFriendsEmails(List<Text> friendsEmails) {
        this.friendsEmails = friendsEmails;
    }
}
