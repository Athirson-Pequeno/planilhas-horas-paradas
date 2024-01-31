package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.activities.WorkActivity;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.Producao;
import com.example.planilhahorasparadas.models.ResponseCall;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitControler {
    public String tableID;
    private HashMap<String, Object> map = new HashMap<>();

    public boolean saveParada(List<Paradas> paradas, String user, Context context, ImageButton buttonSync){
        Producao producao = new Producao();
        map.put("paradas", paradas);
        map.put("producao", producao);

        final Boolean[] sucess = {false};
        spinButtonSync(context, buttonSync);
        RetrofitServiceInterface retrofitServiceInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitServiceInterface.class);
        Call<ResponseCall> call = retrofitServiceInterface.saveParada(paradas,user);
        call.enqueue(new Callback<ResponseCall>() {
            @Override
            public void onResponse(Call<ResponseCall> call, Response<ResponseCall> response) {

                if(response.isSuccessful()) {
                    stopBtnAnimation(buttonSync);
                    Toast.makeText(context, "Sucesso ao salvar.", Toast.LENGTH_LONG).show();
                    sucess[0] = true;
                } else {
                    stopBtnAnimation(buttonSync);
                    Toast.makeText(context, "Erro: " + response.message() , Toast.LENGTH_LONG).show();
                    Log.i("SAVE_ON_TABLE: ", response.message());
                    sucess[0] = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseCall> call, Throwable t) {
                stopBtnAnimation(buttonSync);
                Toast.makeText(context, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("SAVE_ON_TABLE: ", t.getMessage());
                sucess[0] = false;
            }
        });

        return sucess[0];

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
