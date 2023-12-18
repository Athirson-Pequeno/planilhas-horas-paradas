package com.example.planilhahorasparadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Celulas;
import com.example.planilhahorasparadas.models.Inutilizado;

import java.util.List;

public class InutilizadoRowAdapter extends RecyclerView.Adapter<InutilizadoRowAdapter.MyViewHolder> {
    private List<Inutilizado> inutilizadosList;

    public InutilizadoRowAdapter(List<Inutilizado> inutilizadosList) {
        this.inutilizadosList = inutilizadosList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.inutilizado_row_adapter, parent, false
        );

        return new InutilizadoRowAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Inutilizado inutilizado = inutilizadosList.get(position);
        holder.textPicncaD.setText(inutilizado.getPincaD());
        holder.textPicncaE.setText(inutilizado.getPincaE());
        holder.textFuracao.setText(inutilizado.getFuracao());
        holder.textCorte.setText(inutilizado.getCorte());

        if (position != inutilizadosList.size()-1){
            holder.buttonAdd.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return inutilizadosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textPicncaD;
        TextView textPicncaE;
        TextView textFuracao;
        TextView textCorte;
        Button buttonAdd;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textPicncaD = itemView.findViewById(R.id.textViewPincaD);
            textPicncaE = itemView.findViewById(R.id.textViewPincaE);
            textFuracao = itemView.findViewById(R.id.textViewFuracao);
            textCorte = itemView.findViewById(R.id.textViewCorte);
            buttonAdd = itemView.findViewById(R.id.buttonAddInutilizado);
        }
    }
}
