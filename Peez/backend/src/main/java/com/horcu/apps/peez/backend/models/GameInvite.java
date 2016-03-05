package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by hacz on 2/11/2016.
 */

@Entity
public class GameInvite {

    @Id
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
