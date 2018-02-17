package com.hsn.resourceloader.Cache;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public interface Cacheable<T>{

    String getKey();

    T getValue();

    int getByteCount();
}
