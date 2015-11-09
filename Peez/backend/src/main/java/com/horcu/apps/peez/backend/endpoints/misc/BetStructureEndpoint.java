package com.horcu.apps.peez.backend.endpoints.misc;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.backend.models.misc.BetStructure;
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
        name = "betStructureApi",
        version = "v1",
        resource = "betStructure",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "misc.models.backend.peez.apps.horcu.com",
                ownerName = "misc.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class BetStructureEndpoint {

    private static final Logger logger = Logger.getLogger(BetStructureEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(BetStructure.class);
    }

    /**
     * Returns the {@link BetStructure} with the corresponding ID.
     *
     * @param Id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code BetStructure} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "betStructure/{Id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public BetStructure get(@Named("Id") String Id) throws NotFoundException {
        logger.info("Getting BetStructure with ID: " + Id);
        BetStructure betStructure = ofy().load().type(BetStructure.class).id(Id).now();
        if (betStructure == null) {
            throw new NotFoundException("Could not find BetStructure with ID: " + Id);
        }
        return betStructure;
    }

    /**
     * Inserts a new {@code BetStructure}.
     */
    @ApiMethod(
            name = "insert",
            path = "betStructure",
            httpMethod = ApiMethod.HttpMethod.POST)
    public BetStructure insert(BetStructure betStructure) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that betStructure.Id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(betStructure).now();
        logger.info("Created BetStructure.");

        return ofy().load().entity(betStructure).now();
    }

    /**
     * Updates an existing {@code BetStructure}.
     *
     * @param Id           the ID of the entity to be updated
     * @param betStructure the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code BetStructure}
     */
    @ApiMethod(
            name = "update",
            path = "betStructure/{Id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public BetStructure update(@Named("Id") String Id, BetStructure betStructure) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(Id);
        ofy().save().entity(betStructure).now();
        logger.info("Updated BetStructure: " + betStructure);
        return ofy().load().entity(betStructure).now();
    }

    /**
     * Deletes the specified {@code BetStructure}.
     *
     * @param Id the ID of the entity to delete
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code BetStructure}
     */
    @ApiMethod(
            name = "remove",
            path = "betStructure/{Id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("Id") String Id) throws NotFoundException {
        checkExists(Id);
        ofy().delete().type(BetStructure.class).id(Id).now();
        logger.info("Deleted BetStructure with ID: " + Id);
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
            path = "betStructure",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<BetStructure> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<BetStructure> query = ofy().load().type(BetStructure.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<BetStructure> queryIterator = query.iterator();
        List<BetStructure> betStructureList = new ArrayList<BetStructure>(limit);
        while (queryIterator.hasNext()) {
            betStructureList.add(queryIterator.next());
        }
        return CollectionResponse.<BetStructure>builder().setItems(betStructureList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String Id) throws NotFoundException {
        try {
            ofy().load().type(BetStructure.class).id(Id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find BetStructure with ID: " + Id);
        }
    }
}