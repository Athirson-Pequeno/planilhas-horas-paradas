package com.example.planilhahorasparadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Celulas;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.List;

public class InutilizadoAdapter extends RecyclerView.Adapter<InutilizadoAdapter.MyViewHolder> {
    private List<Celulas> celulasList;

    public InutilizadoAdapter(List<Celulas> celulasList) {
        this.celulasList = celulasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.inutilizado_adapter, parent, false
        );

        return new InutilizadoAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Celulas celulas = celulasList.get(position);
        holder.numCel.setText(celulas.getNumCel());
        RecyclerView recyclerView = holder.recyclerView;

        InutilizadoRowAdapter inutilizadoRowAdapter = new InutilizadoRowAdapter(celulas.getListaInutilizado());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.HORIZONTAL));
        recyclerView.setAdapter(inutilizadoRowAdapter);

    }

    @Override
    public int getItemCount() {
        return celulasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numCel;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numCel = itemView.findViewById(R.id.textViewCelInutilizado);
            recyclerView = itemView.findViewById(R.id.recyclerViewRowInutilizado);
        }
    }
}
