package com.example.planilhahorasparadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Data;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private final List<Data> lista;
    private final LongItemClickListener longItemClickListener;
    private final ItemClickListener itemClickListener;

    public DataAdapter(List<Data> lista, LongItemClickListener longItemClickListener, ItemClickListener itemClickListener) {
        this.lista = lista;
        this.longItemClickListener = longItemClickListener;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.data, parent, false
        );

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data data = lista.get(position);

        holder.textData.setText(data.getDataText());


        holder.itemView.setOnLongClickListener(view -> {
            longItemClickListener.onLongItemClick(data);
            return true;
        });

        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(data));
    }

    public interface LongItemClickListener {
        void onLongItemClick(Data data);
    }

    public interface ItemClickListener {
        void onItemClick(Data data);

    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textData;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            textData = itemView.findViewById(R.id.textData);

        }
    }
}
