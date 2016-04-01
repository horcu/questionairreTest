package com.horcu.apps.peez.backend.endpoints.misc;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.horcu.apps.peez.backend.storage.MemCache;
import com.horcu.apps.peez.common.utilities.consts;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "memCacheApi",
        version = "v1",
        resource = "memCache",
        clientIds = {consts.WEB_CLIENT_IDS,
                consts.ANDROID_CLIENT_IDS},
        audiences = {consts.WEB_CLIENT_IDS},
        namespace = @ApiNamespace(
                ownerDomain = "storage.backend.peez.apps.horcu.com",
                ownerName = "storage.backend.peez.apps.horcu.com",
                packagePath = ""
        )
)
public class MemCacheEndpoint {

    private static final Logger logger = Logger.getLogger(MemCacheEndpoint.class.getName());

    /**
     * This method gets the <code>MemCache</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>MemCache</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getMemCache")
    public MemCache getMemCache(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getMemCache method");
        return null;
    }

    /**
     * This inserts a new <code>MemCache</code> object.
     *
     * @param memCache The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertMemCache")
    public MemCache insertMemCache(MemCache memCache) {
        // TODO: Implement this function
        logger.info("Calling insertMemCache method");
        return memCache;
    }
}