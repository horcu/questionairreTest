package com.horcu.apps.peez.backend.endpoints.gameboard;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.backend.models.Piece;
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
        name = "pieceApi",
        version = "v1",
        resource = "piece",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "gameboard.models.backend.peez.apps.horcu.com",
                ownerName = "gameboard.models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class PieceEndpoint {

    private static final Logger logger = Logger.getLogger(PieceEndpoint.class.getName());
    private static final Integer DEFAULT_LIST_LIMIT = 5;

    /**
     * This method gets the <code>piece</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>piece</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getpiece")
    public Piece getpiece(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getpiece method");
        return null;
    }

    /**
     * This inserts a new <code>piece</code> object.
     *
     * @param piece The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertpiece")
    public Piece insertpiece(Piece piece) {
        // TODO: Implement this function
        logger.info("Calling insertpiece method");
        return piece;
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
    public CollectionResponse<Piece> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Piece> query = ofy().load().type(Piece.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Piece> queryIterator = query.iterator();
        List<Piece> userList = new ArrayList<Piece>(limit);
        while (queryIterator.hasNext()) {
            userList.add(queryIterator.next());
        }
        return CollectionResponse.<Piece>builder().setItems(userList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
}