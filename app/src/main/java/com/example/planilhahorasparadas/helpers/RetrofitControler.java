package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.Producao;
import com.example.planilhahorasparadas.models.ResponseCall;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitControler {

    private boolean success;

    public boolean saveParada(HashMap map, String user,String data, Context context, ImageButton buttonSync) {

        spinButtonSync(context, buttonSync);
        RetrofitServiceInterface retrofitServiceInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitServiceInterface.class);
        Call<ResponseCall> call = retrofitServiceInterface.saveParada(map, user,data);
        call.enqueue(new Callback<ResponseCall>() {
            @Override
            public void onResponse(Call<ResponseCall> call, Response<ResponseCall> response) {

                System.out.println(response.message());

                if (response.isSuccessful()) {
                    stopBtnAnimation(buttonSync);
                    Toast.makeText(context, "Sucesso ao salvar.", Toast.LENGTH_LONG).show();

                    ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
                    ProducaoDAO producaoDAO = new ProducaoDAO(MyApplicationContext.getAppContext());

                    List<Producao> producao = (List<Producao>) map.get("producao");
                    List<Paradas> paradas = (List<Paradas>) map.get("paradas");

                    if (paradas != null) {
                        paradas.forEach(paradasDAO::atualizarNaoSalvos);
                    }
                    if (producao != null) {
                        producao.forEach(producaoDAO::atualizarNaoSalvos);
                    }

                    success = true;
                } else {
                    stopBtnAnimation(buttonSync);
                    Toast.makeText(context, "Erro: A " + response.message(), Toast.LENGTH_LONG).show();
                    Log.i("SAVE_ON_TABLE: ", response.message());
                    success = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCall> call, @NonNull Throwable t) {
                stopBtnAnimation(buttonSync);
                Toast.makeText(context, "Erro: B " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("SAVE_ON_TABLE: ", Objects.requireNonNull(t.getMessage()));
                success = false;
            }
        });
        return success;

    }

    private void spinButtonSync(Context context, ImageButton buttonSync) {
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.button_spin);
        buttonSync.startAnimation(rotation);
    }

    public static void stopBtnAnimation(ImageButton buttonSync) {
        buttonSync.clearAnimation();
    }
}
