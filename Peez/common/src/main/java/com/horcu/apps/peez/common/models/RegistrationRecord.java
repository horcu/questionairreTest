package com.horcu.apps.peez.common.models;

/**
 * The Objectify object model for device registrations we are persisting
 */

public class RegistrationRecord {


    Long id;
    private String token;
    private String userEmail;
    private String regDate;

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