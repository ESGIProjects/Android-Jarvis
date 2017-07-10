package com.example.kevin.jarvis.Tools;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by kevin on 09/07/2017.
 */

public class RetrofitInstance {

    static Retrofit retrofit = null;

    public static Retrofit getInstance(String url){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
