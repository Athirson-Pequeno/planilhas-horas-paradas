package com.example.planilhahorasparadas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.activities.SelectDateActivity;
import com.example.planilhahorasparadas.activities.WorkActivity;
import com.example.planilhahorasparadas.models.Data;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context context;
    private final List<Data> listaData;

    public DataAdapter(List<Data> list) {
        this.listaData = list;
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
        Data data = listaData.get(position);

        holder.textData.setText(data.getDataText());

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, WorkActivity.class);
            intent.putExtra("data", data.getDataText());
            context.startActivity(intent);

        });

        holder.itemView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Deletar data")
                    .setMessage("Tem certeza que quer apagar a data " + data.getDataText() + " e todas as paradas relacionada a ela?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        SelectDateActivity.deleteData(data);
                        if (SelectDateActivity.deleteData(data)) {
                            Toast.makeText(context, "Data " + data.getDataText() + " apagada", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return this.listaData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textData;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            context = itemView.getContext();
            textData = itemView.findViewById(R.id.textData);

        }
    }
}
