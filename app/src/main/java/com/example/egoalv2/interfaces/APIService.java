package com.example.egoalv2.interfaces;

import com.example.egoalv2.model.LoginRequest;
import com.example.egoalv2.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
