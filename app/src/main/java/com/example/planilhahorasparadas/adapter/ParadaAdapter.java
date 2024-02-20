package com.example.planilhahorasparadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public class ParadaAdapter extends RecyclerView.Adapter<ParadaAdapter.MyViewHolder> {

    private final List<Paradas> lista;
    private final ItemClickListener itemClickListener;

    public ParadaAdapter(List<Paradas> lista, ItemClickListener itemClickListener) {
        this.lista = lista;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.parada, parent, false
        );

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Paradas parada = lista.get(position);
        holder.cel.setText(parada.getCelula());
        holder.horaI.setText(String.format("%s", parada.getHoraI()));
        holder.horaF.setText(String.format("%s", parada.getHoraF()));
        holder.obs.setText(parada.getObs());
        holder.cod.setText(parada.getCod().substring(0, 5));


        holder.itemView.setOnLongClickListener(view -> {
            itemClickListener.onLongItemClick(parada);
            return true;
        });
    }

    public interface ItemClickListener {
        void onLongItemClick(Paradas parada);
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cel, horaI, horaF, obs, cod;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cel = itemView.findViewById(R.id.textCel);
            horaI = itemView.findViewById(R.id.textHoraI);
            horaF = itemView.findViewById(R.id.textHoraF);
            obs = itemView.findViewById(R.id.textObs);
            cod = itemView.findViewById(R.id.textCod);

        }
    }
}
