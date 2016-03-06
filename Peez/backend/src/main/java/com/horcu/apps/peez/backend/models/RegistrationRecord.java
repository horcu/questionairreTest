package com.horcu.apps.peez.backend.models;

import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The Objectify object model for device registrations we are persisting
 */

@Entity
public class RegistrationRecord {

    @Id
    Long id;
    public String token;

    public String userEmail;
    public String regDate;

    public RegistrationRecord() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}