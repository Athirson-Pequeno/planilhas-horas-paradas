package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.Producao;
import com.example.planilhahorasparadas.models.ResponseCall;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitControler {

    private boolean sucess;

    public boolean saveParada(HashMap map, String user, Context context, ImageButton buttonSync) {

        spinButtonSync(context, buttonSync);
        RetrofitServiceInterface retrofitServiceInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitServiceInterface.class);
        Call<ResponseCall> call = retrofitServiceInterface.saveParada(map, user);
        call.enqueue(new Callback<ResponseCall>() {
            @Override
            public void onResponse(Call<ResponseCall> call, Response<ResponseCall> response) {

                System.out.println(response.message());

                if (response.code() == 200) {
                    stopBtnAnimation(buttonSync);
                    Toast.makeText(context, "Sucesso ao salvar.", Toast.LENGTH_LONG).show();

                    ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
                    ProducaoDAO producaoDAO = new ProducaoDAO(MyApplicationContext.getAppContext());

                    List<Producao> producao = (List<Producao>) map.get("producao");
                    List<Paradas> paradas = (List<Paradas>) map.get("paradas");
                    List<Paradas> paradas2 = (List<Paradas>) map.get("paradas2");

                    if (paradas != null) {
                        paradas.forEach(paradasDAO::atualizarNaoSalvos);
                    }
                    if (producao != null) {
                        producao.forEach(producaoDAO::atualizarNaoSalvos);
                    }

                    sucess = true;
                } else {
                    stopBtnAnimation(buttonSync);
                    Toast.makeText(context, "Erro: A " + response.message(), Toast.LENGTH_LONG).show();
                    Log.i("SAVE_ON_TABLE: ", response.message());
                    sucess = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseCall> call, Throwable t) {
                stopBtnAnimation(buttonSync);
                Toast.makeText(context, "Erro: B " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("SAVE_ON_TABLE: ", t.getMessage());
                sucess = false;
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


    private void spinButtonSync(Context context, ImageButton buttonSync) {
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.button_spin);
        buttonSync.startAnimation(rotation);
    }

    public static void stopBtnAnimation(ImageButton buttonSync) {
        buttonSync.clearAnimation();
    }
}
