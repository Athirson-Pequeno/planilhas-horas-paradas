package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.ResponseCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitServiceInterface {

    @POST("teste/exec")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ResponseCall> saveParada(@Body List<Paradas> parada, @Query("user") String user);


}
