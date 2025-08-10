package com.example.fitrec.repository;

import com.example.fitrec.model.User;
import com.example.fitrec.network.RetrofitClient;
import com.example.fitrec.network.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserApi userApi;

    public UserRepository() {
        userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
    }

    // Example: Get all users
    public void getUsers(Callback<List<User>> callback) {
        Call<List<User>> call = userApi.getUsers();
        call.enqueue(callback);
    }

    // Example: Create a new user
    public void createUser(User user, Callback<User> callback) {
        Call<User> call = userApi.createUser(user);
        call.enqueue(callback);
    }
}