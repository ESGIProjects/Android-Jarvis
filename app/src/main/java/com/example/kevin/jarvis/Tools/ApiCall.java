package com.example.kevin.jarvis.Tools;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by kevin on 10/07/2017.
 */

public interface ApiCall {

    @FormUrlEncoded
    @POST("/")
    Call<String> login(@Header("Content-Type") String content_type , @Field("password") String password);

}
