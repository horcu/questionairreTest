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
import com.horcu.apps.peez.backend.models.Stats;

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
        name = "statsApi",
        version = "v1",
        resource = "stats",
        namespace = @ApiNamespace(
                ownerDomain = "models.backend.peez.apps.horcu.com",
                ownerName = "models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class StatsEndpoint {

    private static final Logger logger = Logger.getLogger(StatsEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Stats.class);
    }

    /**
     * Returns the {@link Stats} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Stats} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "stats/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Stats get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting Stats with ID: " + id);
        Stats stats = ofy().load().type(Stats.class).id(id).now();
        if (stats == null) {
            throw new NotFoundException("Could not find Stats with ID: " + id);
        }
        return stats;
    }

    /**
     * Inserts a new {@code Stats}.
     */
    @ApiMethod(
            name = "insert",
            path = "stats",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Stats insert(Stats stats) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that stats.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(stats).now();
        logger.info("Created Stats with ID: " + stats.getId());

        return ofy().load().entity(stats).now();
    }

    /**
     * Updates an existing {@code Stats}.
     *
     * @param id    the ID of the entity to be updated
     * @param stats the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Stats}
     */
    @ApiMethod(
            name = "update",
            path = "stats/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Stats update(@Named("id") String id, Stats stats) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(stats).now();
        logger.info("Updated Stats: " + stats);
        return ofy().load().entity(stats).now();
    }

    /**
     * Deletes the specified {@code Stats}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Stats}
     */
    @ApiMethod(
            name = "remove",
            path = "stats/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Stats.class).id(id).now();
        logger.info("Deleted Stats with ID: " + id);
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
            path = "stats",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Stats> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Stats> query = ofy().load().type(Stats.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Stats> queryIterator = query.iterator();
        List<Stats> statsList = new ArrayList<Stats>(limit);
        while (queryIterator.hasNext()) {
            statsList.add(queryIterator.next());
        }
        return CollectionResponse.<Stats>builder().setItems(statsList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(Stats.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Stats with ID: " + id);
        }
    }
}