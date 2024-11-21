package com.example.proyecto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistroService {
    @POST("game/user")
    @Headers("Content-Type: application/json")
    Call<List<DatosRegistro>> newUser(@Body DatosRegistro d);
}
