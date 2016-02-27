package com.horcu.apps.peez.common.models;


/**
 * Created by hacz on 9/29/2015.
 */

public class User { //extends Player - extend the google gsm player object??

    public String email;
   public String userName;
   public String alias;
   public double cash;
   public String imageUri;

   public String phone;
   public String joined;
   public Long rank;
   public String registrationId;
    // you can add more fields...

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}
