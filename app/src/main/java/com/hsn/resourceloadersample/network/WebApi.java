package com.hsn.resourceloadersample.network;

import com.hsn.resourceloadersample.model.UserContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hassanshakeel on 2/15/18.
 */

public interface WebApi {

    @GET("raw/wgkJgazE")
    Call<List<UserContent>> fetchDummyUserContent();
}
