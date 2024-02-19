package com.example.planilhahorasparadas.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planilhahorasparadas.R;

public class ConfigActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;
    public static String LISTA_CELULAS = "listaDeCelulas";
    public static String CONFIGURACOES = "configuracoes";
    private Boolean bool;
    private Button buttonteste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        EditText editText = findViewById(R.id.editTextConfigCelulas);
        Button button = findViewById(R.id.buttonConfigCelulas);
        buttonteste = findViewById(R.id.buttonTeste);
        buttonteste.setOnClickListener(view -> mudarTeste());


        sharedPreferences = getSharedPreferences(CONFIGURACOES,MODE_PRIVATE);

        String celulasListas = sharedPreferences.getString(LISTA_CELULAS, "71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96");
        editText.setText(celulasListas);

        bool = sharedPreferences.getBoolean("teste", false);
        mudarcorteste(bool);


        button.setOnClickListener(view -> {

            String celulas = editText.getText().toString();
            sharedPreferences.edit().putString(LISTA_CELULAS,celulas).apply();


        });
    }

    private void mudarTeste() {

        bool = !bool;
        sharedPreferences.edit().putBoolean("teste", bool).apply();
        mudarcorteste(bool);
        Toast.makeText(this, bool.toString(),Toast.LENGTH_SHORT).show();

    }

    private void mudarcorteste(Boolean bool){

        if (bool){
            buttonteste.setBackgroundColor(Color.GREEN);
        }else {
            buttonteste.setBackgroundColor(Color.RED);
        }
    }
}