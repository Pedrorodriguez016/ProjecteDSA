package com.example.proyecto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MainService {
    @POST("/user/login")
    @Headers("Content-Type: application/json")
    Call<List<Datos>> CreateDATOS(@Body Datos d);
    @POST("/user")
    @Headers("Content-Type: application/json")
    Call<List<DatosRegistro>> CreateDATOSregistro(@Body DatosRegistro d);
}
