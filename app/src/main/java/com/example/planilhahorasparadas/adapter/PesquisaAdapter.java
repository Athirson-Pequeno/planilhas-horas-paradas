package com.example.planilhahorasparadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Especificacoes;

import java.util.List;

public class PesquisaAdapter extends RecyclerView.Adapter<PesquisaAdapter.MyViewHolder> {
    private final List<Especificacoes> lista;
    private final ItemClickListener itemClickListener;

    public PesquisaAdapter(List<Especificacoes> list, ItemClickListener itemClickListener) {
        this.lista = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PesquisaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cores_view, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PesquisaAdapter.MyViewHolder holder, int position) {
        Especificacoes especificacao = lista.get(position);
        holder.textViewCod.setText(especificacao.getCod());
        holder.textViewDescricao.setText(especificacao.getDescricao());

        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(especificacao));

    }

    public interface ItemClickListener {
        void onItemClick(Especificacoes user);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCod, textViewDescricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCod = itemView.findViewById(R.id.textViewCorCod);
            textViewDescricao = itemView.findViewById(R.id.textViewCorDescricao);
        }
    }
}
