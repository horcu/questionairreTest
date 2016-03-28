package com.horcu.apps.peez.data;

import com.horcu.apps.peez.backend.models.moveApi.model.Move;
import com.horcu.apps.peez.backend_gameboard.gameApi.model.Game;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.model.GameEntry;
import com.horcu.apps.peez.model.MessageEntry;
import com.horcu.apps.peez.model.MoveEntry;

import java.util.Date;

import io.realm.Realm;

public class DbEntityBuilder {

    public static MessageEntry BuildMessageEntryRecord(Message message, Realm realm) {
        realm.beginTransaction();
        MessageEntry me = realm.createObject(MessageEntry.class);
        me.setDatetime(message.getData().get("dateTime"));
        me.setFrom(message.getFrom());
        me.setTo( message.getTo());
        me.setId(message.getMessageId());
        me.setMessage(message.getData().get("message"));
        me.setStatus(MessageEntry.Status.NOT_SENT);
        realm.commitTransaction();
        return me;
    }

    public static GameEntry BuildGameEntryRecord(Game newGame, Realm realm) {
        realm.beginTransaction();
        GameEntry ge = realm.createObject(GameEntry.class);
        ge.setDatetime(new Date().toString());
        ge.setGameId(newGame.getGameId());
        ge.setInProgress(true);
        realm.commitTransaction();
        return ge;
    }

    public static MoveEntry BuildMoveEntryRecord(Move newGame, Realm realm) {
        realm.beginTransaction();
        MoveEntry me = realm.createObject(MoveEntry.class);
        me.setTimeStamp(new Date().toString());
        me.setReceiver(newGame.getReceivedBy());
        me.setSender(newGame.getPlayedBy());
        me.setMade(false);
        me.setMoveFromIndex( newGame.getMoveFrom());
        me.setMoveToIndex(newGame.getMoveTo());
        me.setMoveType(newGame.getMoveType());
        realm.commitTransaction();
        return me;
    }
}
