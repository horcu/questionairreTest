package com.horcu.apps.peez.backend.endpoints.league;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;

import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.common.models.league.Team;
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
        name = "teamApi",
        version = "v1",
        resource = "team",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "league.models.backend.peez.apps.horcu.com",
                ownerName = "league.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class TeamEndpoint {

    private static final Logger logger = Logger.getLogger(TeamEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Team.class);
    }

    /**
     * Returns the {@link Team} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Team} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "team/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Team get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting Team with ID: " + id);
        Team team = ofy().load().type(Team.class).id(id).now();
        if (team == null) {
            throw new NotFoundException("Could not find Team with ID: " + id);
        }
        return team;
    }

    /**
     * Inserts a new {@code Team}.
     */
    @ApiMethod(
            name = "insert",
            path = "team",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Team insert(Team team) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that team.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(team).now();
        logger.info("Created Team with ID: " + team.getId());

        return ofy().load().entity(team).now();
    }

    /**
     * Updates an existing {@code Team}.
     *
     * @param id   the ID of the entity to be updated
     * @param team the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Team}
     */
    @ApiMethod(
            name = "update",
            path = "team/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Team update(@Named("id") String id, Team team) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(team).now();
        logger.info("Updated Team: " + team);
        return ofy().load().entity(team).now();
    }

    /**
     * Deletes the specified {@code Team}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Team}
     */
    @ApiMethod(
            name = "remove",
            path = "team/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Team.class).id(id).now();
        logger.info("Deleted Team with ID: " + id);
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
            path = "team",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Team> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Team> query = ofy().load().type(Team.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Team> queryIterator = query.iterator();
        List<Team> teamList = new ArrayList<Team>(limit);
        while (queryIterator.hasNext()) {
            teamList.add(queryIterator.next());
        }
        return CollectionResponse.<Team>builder().setItems(teamList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(Team.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Team with ID: " + id);
        }
    }
}