package com.horcu.apps.peez.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.horcu.apps.peez.backend.models.Board;
import com.horcu.apps.peez.backend.models.Tile;
import com.horcu.apps.peez.backend.models.TileDefinition;
import com.horcu.apps.peez.common.utilities.consts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
        name = "boardApi",
        version = "v1",
        resource = "board",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "backend_gameboard.peez.apps.horcu.com",
                ownerName = "backend_gameboard.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class BoardEndpoint {

    private static final Logger logger = Logger.getLogger(BoardEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    private static Map<String,Integer> defaultMap = new HashMap<>( );



    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Board.class);

        //TODO maybe pull this in via json instead
        defaultMap.put("GF", 3);
        defaultMap.put("GH", 4);
        defaultMap.put("BA", 5);
        defaultMap.put("MT", 6);
        defaultMap.put("MO", 18);
    }

    /**
     * Returns the {@link Board} with the corresponding ID.
     *
     * @param boardKey the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Board} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "board/{boardKey}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Board get(@Named("boardKey") String boardKey) throws NotFoundException {
        logger.info("Getting Board with ID: " + boardKey);
        Board board = ofy().load().type(Board.class).id(boardKey).now();
        if (board == null) {
            throw new NotFoundException("Could not find Board with ID: " + boardKey);
        }
        return board;
    }

    /**
     * Build a new {@code Board}.
     */
    @ApiMethod(
            name = "build",
            path = "build",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Board build(@Named("numberOfTiles")int tileCount) {

        ArrayList<Tile> tiles = GenerateTiles(tileCount);
        String key = GenerateUniqueKey();
        Board board = new Board(key,tiles);

    return insert(board);
}

    private String GenerateUniqueKey() {
        Random r = new Random();
        return String.valueOf(r.nextLong());
    }

    private ArrayList<Tile> GenerateTiles(int tileCount) {

        ArrayList<Tile> tempTileList = buildTileList();

        Random r = new Random();
        ArrayList<Tile> masterList = new ArrayList<>();


        //populate the board with game pieces of different types
        //piece types and their occurrences are defined in tilePieceMap
        for(int i =0; i < tileCount; i++)
        {
            String key = (new ArrayList<String>(defaultMap.keySet())).get(i);
            int occurrence = defaultMap.get(key);

            for (int x=0; x < occurrence; x++)
            {
                int idx = r.nextInt(tempTileList.size());
                Tile chosenTile = tempTileList.get(idx);
                chosenTile.setPiece(key);
                masterList.add(i,chosenTile);
                tempTileList.remove(idx);
            }
        }

             Collections.shuffle(masterList);
        return masterList;
    }

    private ArrayList<Tile> buildTileList() {

        ArrayList<Tile> tiles = new ArrayList<>();
        try {
            TileDefinition tileDefinition = new TileDefinitionEndpoint().get("123456");

            Gson gson = new Gson();
             return gson.fromJson(tileDefinition.getDefinition(), new TypeToken<List<Tile>>() {
            }.getType());

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    /**
     * Inserts a new {@code Board}.
     */
    @ApiMethod(
            name = "insert",
            path = "board",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Board insert(Board board) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that board.boardKey has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you sould probably use PUT instead.
        ofy().save().entity(board).now();
        logger.info("Created Board.");

        return ofy().load().entity(board).now();
    }

    /**
     * Updates an existing {@code Board}.
     *
     * @param boardKey the ID of the entity to be updated
     * @param board    the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code boardKey} does not correspond to an existing
     *                           {@code Board}
     */
    @ApiMethod(
            name = "update",
            path = "board/{boardKey}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Board update(@Named("boardKey") String boardKey, Board board) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(boardKey);
        ofy().save().entity(board).now();
        logger.info("Updated Board: " + board);
        return ofy().load().entity(board).now();
    }

    /**
     * Deletes the specified {@code Board}.
     *
     * @param boardKey the ID of the entity to delete
     * @throws NotFoundException if the {@code boardKey} does not correspond to an existing
     *                           {@code Board}
     */
    @ApiMethod(
            name = "remove",
            path = "board/{boardKey}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("boardKey") String boardKey) throws NotFoundException {
        checkExists(boardKey);
        ofy().delete().type(Board.class).id(boardKey).now();
        logger.info("Deleted Board with ID: " + boardKey);
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
            path = "board",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Board> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Board> query = ofy().load().type(Board.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Board> queryIterator = query.iterator();
        List<Board> boardList = new ArrayList<Board>(limit);
        while (queryIterator.hasNext()) {
            boardList.add(queryIterator.next());
        }
        return CollectionResponse.<Board>builder().setItems(boardList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String boardKey) throws NotFoundException {
        try {
            ofy().load().type(Board.class).id(boardKey).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Board with ID: " + boardKey);
        }
    }
}