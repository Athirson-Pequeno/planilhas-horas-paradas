package com.example.planilhahorasparadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.Producao;

import java.util.List;

public class ProducaoAdapter extends RecyclerView.Adapter<ProducaoAdapter.MyViewHolder> {

    private final List<Producao> listProducao;
    private ItemClickListener itemClickListener;

    public ProducaoAdapter(List<Producao> listProducao, ItemClickListener itemClickListener) {
        this.listProducao = listProducao;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ProducaoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.producao_view_adapter, parent, false
        );

        return new ProducaoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducaoAdapter.MyViewHolder holder, int position) {
        Producao producao = listProducao.get(position);
        holder.cel.setText(producao.getCelula());
        holder.codArtigo.setText(producao.getCodigoArtigo());
        holder.codCor.setText(producao.getCodigoCor());
        holder.descArtigo.setText(producao.getDescricaoArtigo());
        holder.descCor.setText(producao.getDescricaoCor());
        holder.quantidade.setText(String.valueOf(producao.getQuantidadeProduzida()));
        holder.tamanho.setText(producao.getTamanho());

        holder.itemView.setOnLongClickListener(view -> {
            itemClickListener.onLongItemClick(producao);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return this.listProducao.size();
    }

    public interface ItemClickListener{
        void onLongItemClick(Producao producao);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cel, codCor, codArtigo, descCor, descArtigo, quantidade, tamanho;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cel = itemView.findViewById(R.id.textViewCelFragmentProducao);
            codCor = itemView.findViewById(R.id.textViewCodCorFragmentProducao);
            codArtigo = itemView.findViewById(R.id.textViewCodArtigoFragmentProducao);
            descCor = itemView.findViewById(R.id.textViewDesCorFragmentProducao);
            descArtigo = itemView.findViewById(R.id.textViewDesArtigoFragmentProducao);
            quantidade = itemView.findViewById(R.id.textViewQuantidadeFragmentProducao);
            tamanho = itemView.findViewById(R.id.textViewTamahoFragmentProducao);

        }
    }
}
