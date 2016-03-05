package com.horcu.apps.peez.backend.endpoints.gameboard;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.horcu.apps.peez.backend.models.Invitation;
import com.horcu.apps.peez.common.utilities.consts;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "invitationApi",
        version = "v1",
        resource = "invitation",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "gameboard.models.backend.peez.apps.horcu.com",
                ownerName = "gameboard.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class InvitationEndpoint {

    private static final Logger logger = Logger.getLogger(InvitationEndpoint.class.getName());

    /**
     * This method gets the <code>Invitation</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Invitation</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getInvitation")
    public Invitation getInvitation(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getInvitation method");
        return null;
    }

    /**
     * This inserts a new <code>Invitation</code> object.
     *
     * @param invitation The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertInvitation")
    public Invitation insertInvitation(Invitation invitation) {
        // TODO: Implement this function
        logger.info("Calling insertInvitation method");
        return invitation;
    }
}