package com.example.fitrec.network;

import com.example.fitrec.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("api/users")
    Call<List<User>> getUsers();

    @POST("api/users")
    Call<User> createUser(@Body User user);
}