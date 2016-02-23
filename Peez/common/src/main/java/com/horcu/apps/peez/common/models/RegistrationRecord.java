package com.horcu.apps.peez.common.models;

/**
 * The Objectify object model for device registrations we are persisting
 */

public class RegistrationRecord {


    Long id;

    private String regId;
    // you can add more fields...

    public RegistrationRecord() {
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}