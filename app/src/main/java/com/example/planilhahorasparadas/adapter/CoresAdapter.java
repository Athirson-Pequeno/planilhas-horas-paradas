package com.example.planilhahorasparadas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Cores;
import com.example.planilhahorasparadas.models.Data;
import com.example.planilhahorasparadas.models.Especificacoes;

import java.util.List;

public class CoresAdapter extends RecyclerView.Adapter<CoresAdapter.MyViewHolder>{

    private List<Especificacoes> listaCores;

    public CoresAdapter(List<Especificacoes> listaCores) {
        this.listaCores = listaCores;
    }

    @NonNull
    @Override
    public CoresAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cores_view, parent, false
        );

        return new CoresAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull CoresAdapter.MyViewHolder holder, int position) {
        Especificacoes cor = listaCores.get(position);
        holder.textCod.setText(cor.getCod());
        holder.textDescricao.setText(cor.getDescricao());
    }

    @Override
    public int getItemCount() {
        return listaCores.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView textCod;
        TextView textDescricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textCod = itemView.findViewById(R.id.textViewCorCod);
            textDescricao = itemView.findViewById(R.id.textViewCorDescricao);
        }
    }
}
