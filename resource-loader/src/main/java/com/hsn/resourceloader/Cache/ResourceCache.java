package com.hsn.resourceloader.Cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class ResourceCache extends LruCache<String, Cacheable> {

    public ResourceCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Cacheable cacheable) {
        return cacheable.getByteCount() / 1024;
    }

    public void addResourceToMemoryCache(Cacheable cacheable) {
        if (getResourceFromMemCache(cacheable.getKey()) == null) {
            put(cacheable.getKey(), cacheable);
        }
    }

    public Cacheable getResourceFromMemCache(String key) {
        return get(key);
    }


}
