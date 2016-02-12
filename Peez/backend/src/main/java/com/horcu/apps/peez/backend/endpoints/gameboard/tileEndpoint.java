package com.horcu.apps.peez.backend.endpoints.gameboard;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.common.models.gameboard.Tile;
import com.horcu.apps.peez.common.utilities.consts;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "tileApi",
        version = "v1",
        resource = "tile",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "gameboard.models.backend.peez.apps.horcu.com",
                ownerName = "gameboard.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class tileEndpoint {

    private static final Logger logger = Logger.getLogger(tileEndpoint.class.getName());
    private static final Integer DEFAULT_LIST_LIMIT = 36;

    /**
     * This method gets the <code>tile</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>tile</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "gettile")
    public Tile gettile(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling gettile method");
        return null;
    }

    /**
     * This inserts a new <code>tile</code> object.
     *
     * @param tile The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "inserttile")
    public Tile inserttile(Tile tile) {
        // TODO: Implement this function
        logger.info("Calling inserttile method");
        return tile;
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
            path = "tile",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Tile> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Tile> query = ofy().load().type(Tile.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Tile> queryIterator = query.iterator();
        List<Tile> userList = new ArrayList<Tile>(limit);
        while (queryIterator.hasNext()) {
            userList.add(queryIterator.next());
        }
        return CollectionResponse.<Tile>builder().setItems(userList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
}