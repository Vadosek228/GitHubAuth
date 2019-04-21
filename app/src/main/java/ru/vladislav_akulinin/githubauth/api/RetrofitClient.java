package ru.vladislav_akulinin.githubauth.api;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vladislav_akulinin.githubauth.api.ApiService;

public class RetrofitClient {

    private static final String ROOT_URL = "https://api.github.com/";

    //GET RETROFIT INSTANCE
    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //get api service
    //return api service
    public static ApiService getApiService(){
        return getRetrofitInstance().create(ApiService.class);
    }
}
