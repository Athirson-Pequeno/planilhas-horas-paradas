package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.ResponseCall;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitServiceInterface {
    @POST("AKfycbym2jfcwDL4YNfxTHlBmG59R001pRa48V6IZaxBdQj8m6_c3yPOdhOWUpEQmV4LHHaWSQ/exec")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ResponseCall> saveParada(@Body HashMap map, @Query("user") String user);

}
