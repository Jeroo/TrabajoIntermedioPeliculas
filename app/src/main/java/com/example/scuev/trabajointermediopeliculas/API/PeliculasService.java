package com.example.scuev.trabajointermediopeliculas.API;

import com.example.scuev.trabajointermediopeliculas.Models.PeliculasRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeliculasService {

   @GET("/")
   Call<PeliculasRespuesta> obtenerListaPeliculas(@Query("s") String s,@Query("apikey") String apikey);
}
