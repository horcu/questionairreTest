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
import com.horcu.apps.peez.backend.models.TileDefinition;
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
        name = "tileDefinitionApi",
        version = "v1",
        resource = "tileDefinition",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "backend_gameboard.peez.apps.horcu.com",
                ownerName = "backend_gameboard.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class TileDefinitionEndpoint {

    private static final Logger logger = Logger.getLogger(TileDefinitionEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(TileDefinition.class);
    }

    /**
     * Returns the {@link TileDefinition} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code TileDefinition} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "tileDefinition/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public TileDefinition get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting TileDefinition with ID: " + id);
        TileDefinition tileDefinition = ofy().load().type(TileDefinition.class).id(id).now();
        if (tileDefinition == null) {
            throw new NotFoundException("Could not find TileDefinition with ID: " + id);
        }
        return tileDefinition;
    }

    /**
     * Inserts a new {@code TileDefinition}.
     */
    @ApiMethod(
            name = "insert",
            path = "tileDefinition",
            httpMethod = ApiMethod.HttpMethod.POST)
    public TileDefinition insert(TileDefinition tileDefinition) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that tileDefinition.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(tileDefinition).now();
        logger.info("Created TileDefinition with ID: " + tileDefinition.getId());

        return ofy().load().entity(tileDefinition).now();
    }

    /**
     * Updates an existing {@code TileDefinition}.
     *
     * @param id             the ID of the entity to be updated
     * @param tileDefinition the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code TileDefinition}
     */
    @ApiMethod(
            name = "update",
            path = "tileDefinition/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public TileDefinition update(@Named("id") String id, TileDefinition tileDefinition) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(tileDefinition).now();
        logger.info("Updated TileDefinition: " + tileDefinition);
        return ofy().load().entity(tileDefinition).now();
    }

    /**
     * Deletes the specified {@code TileDefinition}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code TileDefinition}
     */
    @ApiMethod(
            name = "remove",
            path = "tileDefinition/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(TileDefinition.class).id(id).now();
        logger.info("Deleted TileDefinition with ID: " + id);
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
            path = "tileDefinition",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<TileDefinition> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<TileDefinition> query = ofy().load().type(TileDefinition.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<TileDefinition> queryIterator = query.iterator();
        List<TileDefinition> tileDefinitionList = new ArrayList<TileDefinition>(limit);
        while (queryIterator.hasNext()) {
            tileDefinitionList.add(queryIterator.next());
        }
        return CollectionResponse.<TileDefinition>builder().setItems(tileDefinitionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(TileDefinition.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find TileDefinition with ID: " + id);
        }
    }
}