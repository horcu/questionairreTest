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
import com.horcu.apps.peez.backend.models.misc.BetStruct;

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
        name = "betStructApi",
        version = "v1",
        resource = "betStruct",
        namespace = @ApiNamespace(
                ownerDomain = "misc.models.backend.peez.apps.horcu.com",
                ownerName = "misc.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class BetStructEndpoint {

    private static final Logger logger = Logger.getLogger(BetStructEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(BetStruct.class);
    }

    /**
     * Returns the {@link BetStruct} with the corresponding ID.
     *
     * @param Id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code BetStruct} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "betStruct/{Id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public BetStruct get(@Named("Id") String Id) throws NotFoundException {
        logger.info("Getting BetStruct with ID: " + Id);
        BetStruct betStruct = ofy().load().type(BetStruct.class).id(Id).now();
        if (betStruct == null) {
            throw new NotFoundException("Could not find BetStruct with ID: " + Id);
        }
        return betStruct;
    }

    /**
     * Inserts a new {@code BetStruct}.
     */
    @ApiMethod(
            name = "insert",
            path = "betStruct",
            httpMethod = ApiMethod.HttpMethod.POST)
    public BetStruct insert(BetStruct betStruct) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that betStruct.Id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(betStruct).now();
        logger.info("Created BetStruct.");

        return ofy().load().entity(betStruct).now();
    }

    /**
     * Updates an existing {@code BetStruct}.
     *
     * @param Id        the ID of the entity to be updated
     * @param betStruct the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code BetStruct}
     */
    @ApiMethod(
            name = "update",
            path = "betStruct/{Id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public BetStruct update(@Named("Id") String Id, BetStruct betStruct) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(Id);
        ofy().save().entity(betStruct).now();
        logger.info("Updated BetStruct: " + betStruct);
        return ofy().load().entity(betStruct).now();
    }

    /**
     * Deletes the specified {@code BetStruct}.
     *
     * @param Id the ID of the entity to delete
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code BetStruct}
     */
    @ApiMethod(
            name = "remove",
            path = "betStruct/{Id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("Id") String Id) throws NotFoundException {
        checkExists(Id);
        ofy().delete().type(BetStruct.class).id(Id).now();
        logger.info("Deleted BetStruct with ID: " + Id);
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
            path = "betStruct",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<BetStruct> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<BetStruct> query = ofy().load().type(BetStruct.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<BetStruct> queryIterator = query.iterator();
        List<BetStruct> betStructList = new ArrayList<BetStruct>(limit);
        while (queryIterator.hasNext()) {
            betStructList.add(queryIterator.next());
        }
        return CollectionResponse.<BetStruct>builder().setItems(betStructList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String Id) throws NotFoundException {
        try {
            ofy().load().type(BetStruct.class).id(Id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find BetStruct with ID: " + Id);
        }
    }
}