package com.hsn.resourceloader.Cache;

import android.graphics.Bitmap;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class CachedBitmap implements Cacheable<Bitmap> {

    private Bitmap bitmap;
    private String key;

    public CachedBitmap(String key, Bitmap bitmap) {
        this.bitmap = bitmap;
        this.key = key;
    }

    @Override
    public String getKey() {
        return  key;
    }

    @Override
    public Bitmap getValue() {
        return bitmap;
    }

    @Override
    public int getByteCount() {
        return bitmap.getByteCount();
    }
}
