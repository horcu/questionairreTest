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
        name = "nflWeekApi",
        version = "v1",
        resource = "nflWeek",
        namespace = @ApiNamespace(
                ownerDomain = "models.backend.peez.apps.horcu.com",
                ownerName = "models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class NflWeekEndpoint {

    private static final Logger logger = Logger.getLogger(NflWeekEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(NflWeek.class);
    }

    /**
     * Returns the {@link NflWeek} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code NflWeek} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "nflWeek/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public NflWeek get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting NflWeek with ID: " + id);
        NflWeek nflWeek = ofy().load().type(NflWeek.class).id(id).now();
        if (nflWeek == null) {
            throw new NotFoundException("Could not find NflWeek with ID: " + id);
        }
        return nflWeek;
    }

    /**
     * Returns the {@link NflWeek} with the corresponding ID.
     *

     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code NflWeek} with the provided ID.
     */
    @ApiMethod(
            name = "current",
            path = "nfLWeek/current",
            httpMethod = ApiMethod.HttpMethod.GET)
    public NflWeek current() throws NotFoundException {
        NflWeek thisWeek = null;
        logger.info("Getting current week number ");
        List<NflWeek> weeks = ofy().load().type(NflWeek.class).order("weekNumber").list();//needs ordering

        for (NflWeek week : weeks)
        {
            if(week.getDateRangeEnd().after(new java.util.Date()))
            {
                return week;
            }
        }
        return thisWeek;
    }



    /**
     * Inserts a new {@code NflWeek}.
     */
    @ApiMethod(
            name = "insert",
            path = "nflWeek",
            httpMethod = ApiMethod.HttpMethod.POST)
    public NflWeek insert(NflWeek nflWeek) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that nflWeek.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(nflWeek).now();
        logger.info("Created NflWeek.");

        return ofy().load().entity(nflWeek).now();
    }

    /**
     * Updates an existing {@code NflWeek}.
     *
     * @param id      the ID of the entity to be updated
     * @param nflWeek the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code NflWeek}
     */
    @ApiMethod(
            name = "update",
            path = "nflWeek/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public NflWeek update(@Named("id") long id, NflWeek nflWeek) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(nflWeek).now();
        logger.info("Updated NflWeek: " + nflWeek);
        return ofy().load().entity(nflWeek).now();
    }

    /**
     * Deletes the specified {@code NflWeek}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code NflWeek}
     */
    @ApiMethod(
            name = "remove",
            path = "nflWeek/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(NflWeek.class).id(id).now();
        logger.info("Deleted NflWeek with ID: " + id);
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
            path = "nflWeek",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<NflWeek> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<NflWeek> query = ofy().load().type(NflWeek.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<NflWeek> queryIterator = query.iterator();
        List<NflWeek> nflWeekList = new ArrayList<NflWeek>(limit);
        while (queryIterator.hasNext()) {
            nflWeekList.add(queryIterator.next());
        }
        return CollectionResponse.<NflWeek>builder().setItems(nflWeekList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(NflWeek.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find NflWeek with ID: " + id);
        }
    }
}