package com.horcu.apps.peez.backend.storage;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "cloudStorageApi",
        version = "v1",
        resource = "cloudStorage",
        namespace = @ApiNamespace(
                ownerDomain = "storage.backend.peez.apps.horcu.com",
                ownerName = "storage.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class CloudStorageEndpoint {

    private static final Logger logger = Logger.getLogger(CloudStorageEndpoint.class.getName());

    /**
     * This method gets the <code>cloudStorage</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>cloudStorage</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getcloudStorage")
    public cloudStorage getcloudStorage(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getcloudStorage method");
        return null;
    }

    /**
     * This inserts a new <code>cloudStorage</code> object.
     *
     * @param cloudStorage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertcloudStorage")
    public cloudStorage insertcloudStorage(cloudStorage cloudStorage) {
        // TODO: Implement this function
        logger.info("Calling insertcloudStorage method");
        return cloudStorage;
    }
}