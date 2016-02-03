package com.horcu.apps.peez.backend.endpoints.gameboard;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.backend.models.gameboard.tile;
import com.horcu.apps.peez.backend.models.league.Team;
import com.horcu.apps.peez.backend.utilities.consts;

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

    private static final int DEFAULT_LIST_LIMIT = 36;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(tile.class);
    }

    /**
     * This method gets the <code>tile</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>tile</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "gettile")
    public tile gettile(@Named("id") Long id) {
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
    public tile inserttile(tile tile) {
        // TODO: Implement this function
        logger.info("Calling inserttile method");
        return tile;
    }

    /**
     * This gets the server defined list of tiles.
     */
    @ApiMethod(
            name = "list",
            path = "tile",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<tile> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<tile> query = ofy().load().type(tile.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<tile> queryIterator = query.iterator();
        List<tile> tileList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            tileList.add(queryIterator.next());
        }
        return CollectionResponse.<tile>builder().setItems(tileList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
}