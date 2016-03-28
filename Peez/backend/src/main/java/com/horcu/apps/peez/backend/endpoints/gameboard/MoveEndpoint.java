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
import com.horcu.apps.peez.backend.models.Move;

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
        name = "moveApi",
        version = "v1",
        resource = "move",
        namespace = @ApiNamespace(
                ownerDomain = "models.backend.peez.apps.horcu.com",
                ownerName = "models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class MoveEndpoint {

    private static final Logger logger = Logger.getLogger(MoveEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Move.class);
    }

    /**
     * Returns the {@link Move} with the corresponding ID.
     *
     * @param moveId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Move} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "move/{moveId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Move get(@Named("moveId") String moveId) throws NotFoundException {
        logger.info("Getting Move with ID: " + moveId);
        Move move = ofy().load().type(Move.class).id(moveId).now();
        if (move == null) {
            throw new NotFoundException("Could not find Move with ID: " + moveId);
        }
        return move;
    }

    /**
     * Inserts a new {@code Move}.
     */
    @ApiMethod(
            name = "insert",
            path = "move",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Move insert(Move move) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that move.moveId has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(move).now();
        logger.info("Created Move with ID: " + move.getMoveId());

        return ofy().load().entity(move).now();
    }

    /**
     * Updates an existing {@code Move}.
     *
     * @param moveId the ID of the entity to be updated
     * @param move   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code moveId} does not correspond to an existing
     *                           {@code Move}
     */
    @ApiMethod(
            name = "update",
            path = "move/{moveId}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Move update(@Named("moveId") String moveId, Move move) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(moveId);
        ofy().save().entity(move).now();
        logger.info("Updated Move: " + move);
        return ofy().load().entity(move).now();
    }

    /**
     * Deletes the specified {@code Move}.
     *
     * @param moveId the ID of the entity to delete
     * @throws NotFoundException if the {@code moveId} does not correspond to an existing
     *                           {@code Move}
     */
    @ApiMethod(
            name = "remove",
            path = "move/{moveId}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("moveId") String moveId) throws NotFoundException {
        checkExists(moveId);
        ofy().delete().type(Move.class).id(moveId).now();
        logger.info("Deleted Move with ID: " + moveId);
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
            path = "move",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Move> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Move> query = ofy().load().type(Move.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Move> queryIterator = query.iterator();
        List<Move> moveList = new ArrayList<Move>(limit);
        while (queryIterator.hasNext()) {
            moveList.add(queryIterator.next());
        }
        return CollectionResponse.<Move>builder().setItems(moveList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String moveId) throws NotFoundException {
        try {
            ofy().load().type(Move.class).id(moveId).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Move with ID: " + moveId);
        }
    }
}