package com.example.planilhahorasparadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.planilhahorasparadas.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ConfigActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;
    public static String LISTA_CELULAS = "listaDeCelulas";
    public static String CONFIGURACOES = "configuracoes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        EditText editText = findViewById(R.id.editTextConfigCelulas);
        Button button = findViewById(R.id.buttonConfigCelulas);

        sharedPreferences = getSharedPreferences(CONFIGURACOES,MODE_PRIVATE);

        String celulasListas = sharedPreferences.getString(LISTA_CELULAS, "71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96");
        editText.setText(celulasListas);


        button.setOnClickListener(view -> {

            String celulas = editText.getText().toString();
            sharedPreferences.edit().putString(LISTA_CELULAS,celulas).apply();


        });
    }
}