package com.example.planilhahorasparadas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.adapter.CoresAdapter;
import com.example.planilhahorasparadas.helpers.CoresDAO;
import com.example.planilhahorasparadas.models.Cores;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class CoresView extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private List<Especificacoes> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cores_view);

        recyclerView = findViewById(R.id.recyclerViewCores);

        setRecyclerView();

    }

    public void setRecyclerView() {
        CoresDAO coresDAO = new CoresDAO(MyApplicationContext.getAppContext());
        list = coresDAO.getAll();
        CoresAdapter adapter = new CoresAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}