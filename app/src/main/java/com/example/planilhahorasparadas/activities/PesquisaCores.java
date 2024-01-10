package com.example.planilhahorasparadas.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.helpers.ArtigosDAO;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.util.DialogPesquisaCores;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class PesquisaCores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArtigosDAO coresDAO = new ArtigosDAO(MyApplicationContext.getAppContext());
        List<Especificacoes> listCores = coresDAO.getAll();

        DialogPesquisaCores dialogPesquisaCores = new DialogPesquisaCores(this, listCores);
        setContentView(R.layout.activity_pesquisa_cores);
        TextView textView = findViewById(R.id.TextViewCoresPesquisa);

        dialogPesquisaCores.MostrarDialog(this, textView, PesquisaCores.this);

    }
}