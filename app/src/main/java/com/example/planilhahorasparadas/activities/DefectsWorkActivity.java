package com.example.planilhahorasparadas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.adapter.InutilizadoAdapter;
import com.example.planilhahorasparadas.models.Celulas;
import com.example.planilhahorasparadas.models.Inutilizado;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class DefectsWorkActivity extends AppCompatActivity {

    private List<Celulas> listCelulas = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defects_work);

        recyclerView = findViewById(R.id.recyclerViewDefectsWork);
        setRecyclerView();

    }

    public void setRecyclerView() {
        List<Inutilizado> inutilizados = new ArrayList<>();
        Celulas cel = new Celulas();
        cel.setNumCel("722");
        listCelulas.add(cel);



        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        inutilizados.add(new Inutilizado("2","3","4","5"));
        cel.setListaInutilizado(inutilizados);
        listCelulas.add(cel);
        Celulas cel2 = new Celulas();
        cel2.setNumCel("72");
        cel2.setListaInutilizado(inutilizados);
        listCelulas.add(cel2);

        InutilizadoAdapter inutilizadoAdapter = new InutilizadoAdapter(listCelulas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(inutilizadoAdapter);

    }
}