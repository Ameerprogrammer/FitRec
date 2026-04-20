package com.example.fitrec.network;

import com.example.fitrec.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("users") // GET all users
    Call<List<User>> getUsers();

    @POST("users") // Create a new user
    Call<User> createUser(@Body User user);

    @POST("users/login")
    Call<User> loginUser(@Body User user);

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") Long id);

}