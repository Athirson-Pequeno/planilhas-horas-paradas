package com.example.planilhahorasparadas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.activities.WorkActivity;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public class ParadaAdapter extends RecyclerView.Adapter<ParadaAdapter.MyViewHolder> {

    private final List<Paradas> listaParadas;
    private Context context;
    private ItemClickListener itemClickListener;

    public ParadaAdapter(List<Paradas> list) {
        this.listaParadas = list;
    }

    public ParadaAdapter(List<Paradas> listaParadas, ItemClickListener itemClickListener) {
        this.listaParadas = listaParadas;
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
        Paradas parada = listaParadas.get(position);
        holder.cel.setText(parada.getCelula());
        holder.horaI.setText(String.format("%s", parada.getHoraI()));
        holder.horaF.setText(String.format("%s", parada.getHoraF()));
        holder.obs.setText(parada.getObs());
        holder.cod.setText(parada.getCod());


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
        return this.listaParadas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cel, horaI, horaF, obs, cod;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            cel = itemView.findViewById(R.id.textCel);
            horaI = itemView.findViewById(R.id.textHoraI);
            horaF = itemView.findViewById(R.id.textHoraF);
            obs = itemView.findViewById(R.id.textObs);
            cod = itemView.findViewById(R.id.textCod);

        }
    }
}
