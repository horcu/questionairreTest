package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by hacz on 10/10/2015.
 */
@Entity
public class UserSettings {

    public UserSettings(){}

    @Id
    @Index
    private String email;

    @Index
    private String value;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}