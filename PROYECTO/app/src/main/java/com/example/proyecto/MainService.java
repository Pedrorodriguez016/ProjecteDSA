package com.example.proyecto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MainService {
    @POST("/login")
    Call<List<Datos>> CreateDATOS(@Body Datos d);
    @POST("/reister")
    Call<List<DatosRegistro>> CreateDATOSregistro(@Body DatosRegistro d);
}
