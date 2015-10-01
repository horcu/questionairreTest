package com.horcu.apps.peez.backend.models;

import com.google.api.server.spi.types.SimpleDate;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by hacz on 9/29/2015.
 */
@Entity
public class User {

    @Id public String id;

    @Index
   public String userName;
   public String alias;
   public double cash;
   public String email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
