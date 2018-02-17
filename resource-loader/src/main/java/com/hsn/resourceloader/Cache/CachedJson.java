package com.hsn.resourceloader.Cache;

import org.json.JSONObject;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class CachedJson implements Cacheable<JSONObject> {

    private String key;
    private JSONObject value;

    public CachedJson(String key,JSONObject value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public JSONObject getValue() {
        return value;
    }

    @Override
    public int getByteCount() {
        return value.toString().getBytes().length;
    }
}
