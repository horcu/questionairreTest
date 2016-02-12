package com.horcu.apps.peez.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


import com.horcu.apps.peez.common.models.RegistrationRecord;
import com.horcu.apps.peez.common.models.User;
import com.horcu.apps.peez.common.models.gameboard.GameInvite;
import com.horcu.apps.peez.common.models.gameboard.Piece;
import com.horcu.apps.peez.common.models.gameboard.Tile;


/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(User.class);
        ObjectifyService.register(Tile.class);
        ObjectifyService.register(Piece.class);
        ObjectifyService.register(GameInvite.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
