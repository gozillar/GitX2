package com.example.android.gitx.rest;

import com.example.android.gitx.model.User;
import com.example.android.gitx.model.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by @g_okeke on 10/03/2017.
 */

public interface ApiInterface {

    @GET("/search/users")
    Call<UserList> getAllUsers(@Query("q") String filters);

}
