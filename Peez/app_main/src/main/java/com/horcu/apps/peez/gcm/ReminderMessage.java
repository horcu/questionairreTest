package com.horcu.apps.peez.gcm;

import com.horcu.apps.peez.service.LoggingService;

import io.realm.RealmObject;

/**
 * Created by Horatio on 3/6/2016.
 */
public class ReminderMessage extends RealmObject {

    private String type;
    public ReminderMessage(){
type = LoggingService.MESSAGE_TYPE_REMINDER;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
