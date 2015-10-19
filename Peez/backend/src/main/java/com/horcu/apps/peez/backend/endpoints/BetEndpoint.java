package com.horcu.apps.peez.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.backend.models.Bet;
import com.horcu.apps.peez.backend.utilities.consts;

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
        name = "betApi",
        version = "v1",
        resource = "bet",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "models.backend.peez.apps.horcu.com",
                ownerName = "models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class BetEndpoint {

    private static final Logger logger = Logger.getLogger(BetEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Bet.class);
    }

    /**
     * Returns the {@link Bet} with the corresponding ID.
     *
     * @param BetId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Bet} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "bet/{BetId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Bet get(@Named("BetId") String BetId) throws NotFoundException {
        logger.info("Getting Bet with ID: " + BetId);
        Bet bet = ofy().load().type(Bet.class).id(BetId).now();
        if (bet == null) {
            throw new NotFoundException("Could not find Bet with ID: " + BetId);
        }
        return bet;
    }

    /**
     * Inserts a new {@code Bet}.
     */
    @ApiMethod(
            name = "insert",
            path = "bet",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Bet insert(Bet bet) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that bet.BetId has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(bet).now();
        logger.info("Created Bet with ID: " + bet.getBetId());

        return ofy().load().entity(bet).now();
    }

    /**
     * Updates an existing {@code Bet}.
     *
     * @param BetId the ID of the entity to be updated
     * @param bet   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code BetId} does not correspond to an existing
     *                           {@code Bet}
     */
    @ApiMethod(
            name = "update",
            path = "bet/{BetId}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Bet update(@Named("BetId") String BetId, Bet bet) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(BetId);
        ofy().save().entity(bet).now();
        logger.info("Updated Bet: " + bet);
        return ofy().load().entity(bet).now();
    }

    /**
     * Deletes the specified {@code Bet}.
     *
     * @param BetId the ID of the entity to delete
     * @throws NotFoundException if the {@code BetId} does not correspond to an existing
     *                           {@code Bet}
     */
    @ApiMethod(
            name = "remove",
            path = "bet/{BetId}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("BetId") String BetId) throws NotFoundException {
        checkExists(BetId);
        ofy().delete().type(Bet.class).id(BetId).now();
        logger.info("Deleted Bet with ID: " + BetId);
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
            path = "bet",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Bet> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Bet> query = ofy().load().type(Bet.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Bet> queryIterator = query.iterator();
        List<Bet> betList = new ArrayList<Bet>(limit);
        while (queryIterator.hasNext()) {
            betList.add(queryIterator.next());
        }
        return CollectionResponse.<Bet>builder().setItems(betList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String BetId) throws NotFoundException {
        try {
            ofy().load().type(Bet.class).id(BetId).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Bet with ID: " + BetId);
        }
    }
}