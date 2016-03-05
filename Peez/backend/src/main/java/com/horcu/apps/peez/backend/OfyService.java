package com.horcu.apps.peez.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


import com.horcu.apps.peez.backend.models.RegistrationRecord;
import com.horcu.apps.peez.backend.models.User;
import com.horcu.apps.peez.backend.models.Board;
import com.horcu.apps.peez.backend.models.Game;
import com.horcu.apps.peez.backend.models.GameInvite;
import com.horcu.apps.peez.backend.models.Piece;
import com.horcu.apps.peez.backend.models.Tile;


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
        ObjectifyService.register(Board.class);
        ObjectifyService.register(Tile.class);
        ObjectifyService.register(Game.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
