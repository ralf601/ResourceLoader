package com.hsn.resourceloadersample.network;

import android.content.Context;

import com.hsn.resourceloadersample.common.Constants;
import com.hsn.resourceloadersample.model.UserContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hassanshakeel on 2/15/18.
 */

public class WebApiManager {

    private Context context;

    private static WebApiManager webApiManager;

    public static WebApiManager getInstance(Context context) {
        if (webApiManager == null) {
            synchronized (WebApiManager.class) {
                webApiManager = new WebApiManager(context);
            }
        }
        return webApiManager;
    }

    private WebApiManager(Context context) {
        this.context = context;
    }

    public void fetchDummyUserContent(final WebApiResponseListener<List<UserContent>> listener) {
        try {
            RetrofitHelper.getWebApi(context, Constants.BASE_URL)
                    .fetchDummyUserContent()
                    .enqueue(new Callback<List<UserContent>>() {
                        @Override
                        public void onResponse(Call<List<UserContent>> call, Response<List<UserContent>> response) {
                            listener.onDataFetched(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<UserContent>> call, Throwable t) {
                            t.printStackTrace();
                            listener.onError(WebApiError.Exception, t.getMessage());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(WebApiError.NoNetworkAvailable, e.getMessage());
        }


    }
}
