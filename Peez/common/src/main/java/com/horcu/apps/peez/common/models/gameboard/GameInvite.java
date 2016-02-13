package com.horcu.apps.peez.common.models.gameboard;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by hacz on 2/11/2016.
 */

@Entity
public class GameInvite {

    @Id
    @Index
    @SerializedName("id")
    @Expose
    private String inviteKey;
    private String inviteDateTime ;
    private String invitationText;

    public GameInvite(String inviteKey, String inviteDateTime, String invitationText){
        this.inviteKey = inviteKey;
        this.inviteDateTime = inviteDateTime;
        this.invitationText = invitationText;
    }

    public String getInviteKey() {
        return inviteKey;
    }

    public String getInviteDateTime() {
        return inviteDateTime;
    }

    public String getInvitationText() {
        return invitationText;
    }

}
