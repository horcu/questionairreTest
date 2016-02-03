package com.horcu.apps.peez.backend.endpoints.gameboard;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.horcu.apps.peez.backend.models.gameboard.piece;
import com.horcu.apps.peez.backend.utilities.consts;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "pieceApi",
        version = "v1",
        resource = "piece",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "gameboard.models.backend.peez.apps.horcu.com",
                ownerName = "gameboard.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class pieceEndpoint {

    private static final Logger logger = Logger.getLogger(pieceEndpoint.class.getName());

    /**
     * This method gets the <code>piece</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>piece</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getpiece")
    public piece getpiece(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getpiece method");
        return null;
    }

    /**
     * This inserts a new <code>piece</code> object.
     *
     * @param piece The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertpiece")
    public piece insertpiece(piece piece) {
        // TODO: Implement this function
        logger.info("Calling insertpiece method");
        return piece;
    }
}