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

public class PesquisaAdapater extends RecyclerView.Adapter<PesquisaAdapater.MyViewHolder> {
    private List<Especificacoes> listaUser;
    private ItemClickListener itemClickListener;

    public PesquisaAdapater(List<Especificacoes> list, ItemClickListener itemClickListener) {
        this.listaUser = list;
        this.itemClickListener = itemClickListener;
    }

    public PesquisaAdapater(List<Especificacoes> list) {
        this.listaUser = list;
    }

    @NonNull
    @Override
    public PesquisaAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cores_view, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PesquisaAdapater.MyViewHolder holder, int position) {
        Especificacoes especificacao = listaUser.get(position);
        holder.textViewCod.setText(especificacao.getCod());
        holder.textViewDescricao.setText(especificacao.getDescricao());


        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(especificacao);
        });

    }

    public interface ItemClickListener {
        void onItemClick(Especificacoes user);
    }

    @Override
    public int getItemCount() {
        return listaUser.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCod;
        TextView textViewDescricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCod = itemView.findViewById(R.id.textViewCorCod);
            textViewDescricao = itemView.findViewById(R.id.textViewCorDescricao);
        }
    }
}
