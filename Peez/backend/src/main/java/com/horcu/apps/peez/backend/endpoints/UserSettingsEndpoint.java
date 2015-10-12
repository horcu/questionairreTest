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
import com.horcu.apps.peez.backend.models.UserSettings;

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
        name = "userSettingsApi",
        version = "v1",
        resource = "userSettings",
        namespace = @ApiNamespace(
                ownerDomain = "models.backend.peez.apps.horcu.com",
                ownerName = "models.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class UserSettingsEndpoint {

    private static final Logger logger = Logger.getLogger(UserSettingsEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(UserSettings.class);
    }

    /**
     * Returns the {@link UserSettings} with the corresponding ID.
     *
     * @param email the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code UserSettings} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "userSettings/{email}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UserSettings get(@Named("email") String email) throws NotFoundException {
        logger.info("Getting UserSettings with ID: " + email);
        UserSettings userSettings = ofy().load().type(UserSettings.class).id(email).now();
        if (userSettings == null) {
            throw new NotFoundException("Could not find UserSettings with ID: " + email);
        }
        return userSettings;
    }

    /**
     * Inserts a new {@code UserSettings}.
     */
    @ApiMethod(
            name = "insert",
            path = "userSettings",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserSettings insert(UserSettings userSettings) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that userSettings.email has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(userSettings).now();
        logger.info("Created UserSettings with ID: " + userSettings.getEmail());

        return ofy().load().entity(userSettings).now();
    }

    /**
     * Updates an existing {@code UserSettings}.
     *
     * @param email        the ID of the entity to be updated
     * @param userSettings the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code email} does not correspond to an existing
     *                           {@code UserSettings}
     */
    @ApiMethod(
            name = "update",
            path = "userSettings/{email}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public UserSettings update(@Named("email") String email, UserSettings userSettings) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(email);
        ofy().save().entity(userSettings).now();
        logger.info("Updated UserSettings: " + userSettings);
        return ofy().load().entity(userSettings).now();
    }

    /**
     * Deletes the specified {@code UserSettings}.
     *
     * @param email the ID of the entity to delete
     * @throws NotFoundException if the {@code email} does not correspond to an existing
     *                           {@code UserSettings}
     */
    @ApiMethod(
            name = "remove",
            path = "userSettings/{email}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("email") String email) throws NotFoundException {
        checkExists(email);
        ofy().delete().type(UserSettings.class).id(email).now();
        logger.info("Deleted UserSettings with ID: " + email);
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
            path = "userSettings",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<UserSettings> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<UserSettings> query = ofy().load().type(UserSettings.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<UserSettings> queryIterator = query.iterator();
        List<UserSettings> userSettingsList = new ArrayList<UserSettings>(limit);
        while (queryIterator.hasNext()) {
            userSettingsList.add(queryIterator.next());
        }
        return CollectionResponse.<UserSettings>builder().setItems(userSettingsList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String email) throws NotFoundException {
        try {
            ofy().load().type(UserSettings.class).id(email).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find UserSettings with ID: " + email);
        }
    }
}