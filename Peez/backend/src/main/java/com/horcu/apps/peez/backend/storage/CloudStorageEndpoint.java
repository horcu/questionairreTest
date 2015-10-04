package com.horcu.apps.peez.backend.storage;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.horcu.apps.peez.backend.utilities.consts;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "cloudStorageApi",
        version = "v1",
        resource = "cloudStorage",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "storage.backend.peez.apps.horcu.com",
                ownerName = "storage.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class CloudStorageEndpoint {

    private static final Logger logger = Logger.getLogger(CloudStorageEndpoint.class.getName());

    /**
     * This method gets the <code>CloudStorage</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>CloudStorage</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getCloudStorage")
    public CloudStorage getCloudStorage(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getCloudStorage method");
        return null;
    }

    /**
     * This inserts a new <code>CloudStorage</code> object.
     *
     * @param cloudStorage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertCloudStorage")
    public CloudStorage insertCloudStorage(CloudStorage cloudStorage) {
        // TODO: Implement this function
        logger.info("Calling insertCloudStorage method");
        return cloudStorage;
    }
}