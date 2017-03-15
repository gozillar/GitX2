package com.example.android.gitx.rest;

import android.provider.SyncStateContract;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by @g_okeke on 10/03/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "https://api.github.com";
    private Retrofit retrofit;

    public ApiClient() {

        retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    public ApiInterface getService() {
        return retrofit.create(ApiInterface.class);
    }

}
