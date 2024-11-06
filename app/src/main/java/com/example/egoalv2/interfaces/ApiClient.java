package com.example.egoalv2.interfaces;

import com.example.egoalv2.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    // Create a method to get the Retrofit instance
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)  // Base URL from the Config class
                    .addConverterFactory(GsonConverterFactory.create())  // Using Gson for JSON conversion
                    .build();
        }
        return retrofit;
    }

    // Create an instance of ApiService interface
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}