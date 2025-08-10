package com.example.fitrec.network;

import com.example.fitrec.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @GET("users") // GET all users
    Call<List<User>> getUsers();

    @POST("users") // Create a new user
    Call<User> createUser(@Body User user);
}