package com.horcu.apps.peez.data.realmModules;

import com.horcu.apps.peez.model.GameEntry;
import com.horcu.apps.peez.model.MessageEntry;
import com.horcu.apps.peez.model.MoveEntry;

import io.realm.annotations.RealmModule;

/**
 * Created by Horatio on 3/28/2016.
 */
@RealmModule(classes = {MoveEntry.class, GameEntry.class})
public class GameModule {
}
