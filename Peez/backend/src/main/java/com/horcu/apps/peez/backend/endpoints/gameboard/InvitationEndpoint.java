package com.horcu.apps.peez.backend.endpoints.gameboard;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.backend.models.Invitation;
import com.horcu.apps.peez.common.utilities.consts;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "invitationApi",
        version = "v1",
        resource = "invitation",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "models.backend.peez.apps.horcu.com",
                ownerName = "models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class InvitationEndpoint {

    private static final Logger logger = Logger.getLogger(InvitationEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Invitation.class);
    }

    /**
     * Returns the {@link Invitation} with the corresponding ID.
     *
     * @param invitationKey the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code com.horcu.apps.peez.common.utilities.Invitation} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "invitation/{invitationKey}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Invitation get(@Named("invitationKey") String invitationKey) throws NotFoundException {
        logger.info("Getting com.horcu.apps.peez.common.utilities.Invitation with ID: " + invitationKey);
        Invitation invitation = ofy().load().type(Invitation.class).id(invitationKey).now();
        if (invitation == null) {
            throw new NotFoundException("Could not find com.horcu.apps.peez.common.utilities.Invitation with ID: " + invitationKey);
        }
        return invitation;
    }

    /**
     * Inserts a new {@code com.horcu.apps.peez.common.utilities.Invitation}.
     */
    @ApiMethod(
            name = "insert",
            path = "invitation",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Invitation insert(Invitation invitation) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that invitation.invitationKey has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(invitation).now();
        logger.info("Created com.horcu.apps.peez.common.utilities.Invitation.");

        return ofy().load().entity(invitation).now();
    }

    /**
     * Updates an existing {@code com.horcu.apps.peez.common.utilities.Invitation}.
     *
     * @param invitationKey the ID of the entity to be updated
     * @param invitation    the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code invitationKey} does not correspond to an existing
     *                           {@code com.horcu.apps.peez.common.utilities.Invitation}
     */
    @ApiMethod(
            name = "update",
            path = "invitation/{invitationKey}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Invitation update(@Named("invitationKey") String invitationKey, Invitation invitation) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(invitationKey);
        ofy().save().entity(invitation).now();
        logger.info("Updated com.horcu.apps.peez.common.utilities.Invitation: " + invitation);
        return ofy().load().entity(invitation).now();
    }

    /**
     * Deletes the specified {@code com.horcu.apps.peez.common.utilities.Invitation}.
     *
     * @param invitationKey the ID of the entity to delete
     * @throws NotFoundException if the {@code invitationKey} does not correspond to an existing
     *                           {@code com.horcu.apps.peez.common.utilities.Invitation}
     */
    @ApiMethod(
            name = "remove",
            path = "invitation/{invitationKey}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("invitationKey") String invitationKey) throws NotFoundException {
        checkExists(invitationKey);
        ofy().delete().type(Invitation.class).id(invitationKey).now();
        logger.info("Deleted com.horcu.apps.peez.common.utilities.Invitation with ID: " + invitationKey);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "invitation",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Invitation> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Invitation> query = ofy().load().type(Invitation.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Invitation> queryIterator = query.iterator();
        List<Invitation> invitationList = new ArrayList<Invitation>(limit);
        while (queryIterator.hasNext()) {
            invitationList.add(queryIterator.next());
        }
        return CollectionResponse.<Invitation>builder().setItems(invitationList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String invitationKey) throws NotFoundException {
        try {
            ofy().load().type(Invitation.class).id(invitationKey).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find com.horcu.apps.peez.common.utilities.Invitation with ID: " + invitationKey);
        }
    }
}