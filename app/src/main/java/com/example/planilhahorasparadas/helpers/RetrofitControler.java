package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.widget.Toast;

import com.example.planilhahorasparadas.activities.SelectDateActivity;
import com.example.planilhahorasparadas.activities.WorkActivity;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.ResponseCall;
import com.example.planilhahorasparadas.models.ResponseTableId;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitControler {
    public String tableID;
    public String sucess;
    public String saveParada(List<Paradas> parada, String user, Context context){

        RetrofitServiceInterface retrofitServiceInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitServiceInterface.class);
        Call<ResponseCall> call = retrofitServiceInterface.saveParada(parada, user);
        call.enqueue(new Callback<ResponseCall>() {
            @Override
            public void onResponse(Call<ResponseCall> call, Response<ResponseCall> response) {

                if(response.isSuccessful()) {
                    WorkActivity.stopBtnAnimation();
                    Toast.makeText(context, "Sucesso ao salvar.", Toast.LENGTH_LONG).show();
                    sucess = "item salvo";
                } else {
                    WorkActivity.stopBtnAnimation();
                    Toast.makeText(context, "Erro: " + response.message() , Toast.LENGTH_LONG).show();
                    System.out.println(response);
                    sucess = "erro ao salvar";
                }
            }

            @Override
            public void onFailure(Call<ResponseCall> call, Throwable t) {
                WorkActivity.stopBtnAnimation();
                System.out.println(t);

                Toast.makeText(context, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                sucess = "erro ao salvar";
            }
        });


        return sucess;
    }

    /*public String getTableId(Context context) {
        RetrofitServiceInterface retrofitServiceInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitServiceInterface.class);
        Call<ResponseTableId> call = retrofitServiceInterface.getTableID();
        call.enqueue(new Callback<ResponseTableId>() {
            @Override
            public void onResponse(Call<ResponseTableId> call, Response<ResponseTableId> response) {
                if (response.body() != null) {
                    tableID = response.body().getCod();
                }
            }
            @Override
            public void onFailure(Call<ResponseTableId> call, Throwable t) {
                Toast.makeText(context, "Erro ao carregar id do projeto", Toast.LENGTH_LONG).show();
            }
        });
        return tableID;
    }*/
}
