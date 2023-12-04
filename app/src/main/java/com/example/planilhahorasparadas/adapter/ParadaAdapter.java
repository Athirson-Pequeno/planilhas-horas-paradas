package com.example.planilhahorasparadas.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public class ParadaAdapter extends RecyclerView.Adapter<ParadaAdapter.MyViewHolder> {


    private List<Paradas> listaParadas;
    private Context context;
    public ParadaAdapter(List<Paradas> list){
        this.listaParadas = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.parada,parent, false
        );

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Paradas parada = listaParadas.get(position);
        holder.cel.setText(parada.getCelula());
        holder.horaI.setText(parada.getHoraI().toString());
        holder.horaF.setText(parada.getHoraF().toString());
        holder.obs.setText(parada.getObs());
        holder.cod.setText(parada.getCod());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Deletar data")
                        .setMessage("Tem certeza que quer apagar essa parada?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(WorkActivity.deleteData(parada)){
                                    Toast.makeText(context, "Parada apagada" + parada.getId().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Não", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.listaParadas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cel;
        TextView horaI;
        TextView horaF;
        TextView obs;
        TextView cod;


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
