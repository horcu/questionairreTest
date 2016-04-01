package com.horcu.apps.peez.backend.storage;

import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

/**
 * Created by Horatio on 3/31/2016.
 */
public class MemCache {

    public byte[] GetFromCache(byte[] key) {

        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
        byte[] value = (byte[]) syncCache.get(key); // Read from cache.
        if (value == null)
            return null;
        return value;
    }

    public Future<Object> GetFromCacheAsync(byte[] key) throws ExecutionException, InterruptedException {

        AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();
        asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
        Future<Object> futureValue = asyncCache.get(key); // Read from cache.

        byte[] value = (byte[]) futureValue.get();
        if (value == null)
            return null;
        return futureValue ;

        }

    public Boolean PutInCache(byte[] key, Object value) {
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        if (key != null && value != null) {
            syncCache.put(key, value);
            return true;
        }
        return false;
    }

    public Future<Void> PutInCacheAsync(byte[] key, Object value) {
        AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();
        if (key != null && value != null) {
            return asyncCache.put(key, value);
        }
        return null;
    }
}
