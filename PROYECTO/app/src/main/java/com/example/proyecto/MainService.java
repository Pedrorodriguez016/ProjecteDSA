package com.example.proyecto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MainService {
    @POST("game/user/login")
    @Headers("Content-Type: application/json")
    Call<Datos> loginUser(@Body Datos d);
}
