package com.example.elogbookapp;


import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST("/api/SyncData/")
    Call<String> postupdateDetails(@Query("token") String token);

}
