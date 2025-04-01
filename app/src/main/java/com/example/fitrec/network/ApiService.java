package com.example.fitrec.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

public interface ApiService {
    @GET("your-endpoint") // Change "your-endpoint" to the actual API path
    Call<List<Object>> getItems(); // Change `Object` to your model class

}
