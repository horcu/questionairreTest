package com.horcu.apps.peez.backend.models;

import com.google.api.server.spi.types.SimpleDate;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by hacz on 9/29/2015.
 */
@Entity
public class User {

    @Id
    Long id;
    @Index
   public String userName;
   public String alias;
   private float cash;
   private String email;
   private String phone;
   private SimpleDate joined;
   private Long rank;
   private String registrationId;
    // you can add more fields...

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
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

    public SimpleDate getJoined() {
        return joined;
    }

    public void setJoined(SimpleDate joined) {
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
}
