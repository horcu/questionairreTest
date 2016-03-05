package com.horcu.apps.peez.backend.models;

/**
 * Created by hacz on 2/11/2016.
 */

public class GameInvite {

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
