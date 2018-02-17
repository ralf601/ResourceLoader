package com.hsn.resourceloadersample.network;

import retrofit2.Callback;

/**
 * Created by hassanshakeel on 2/15/18.
 */

public interface WebApiResponseListener<T> {

    void onDataFetched(T callback);
    void onError(WebApiError error , String message);

}
