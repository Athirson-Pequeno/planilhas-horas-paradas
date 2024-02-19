package com.example.planilhahorasparadas.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.adapter.PesquisaAdapter;
import com.example.planilhahorasparadas.models.Especificacoes;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DialogPesquisaEspecificacoes {
    private Dialog dialog;
    private TextView textView;
    private final List<Especificacoes> list;
    private Integer itemId = 0;

    public DialogPesquisaEspecificacoes(List<Especificacoes> listItens) {
        this.list = listItens;
    }

    public void MostrarDialog(Context context, TextView textViewAc, Activity activity) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        textView = textViewAc;

        textView.setOnClickListener(view -> {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.view_dialog_pesquisa);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) Math.round(width * 0.85), (int) Math.round(height * 0.6));
            dialog.show();


            PesquisaAdapter pesquisaAdapter = new PesquisaAdapter(list, especificacao -> {
                setTextView(especificacao);
                dialog.dismiss();
            });


            EditText editTextPesquisa = dialog.findViewById(R.id.editTextPesquisaDialog);
            RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewDialog);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(pesquisaAdapter);


            editTextPesquisa.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    List<Especificacoes> listaFiltro = list.stream().filter(c -> (c.getCod() + c.getDescricao().toLowerCase()).contains(charSequence.toString().toLowerCase())).sorted(Comparator.comparingInt(especificacoes -> especificacoes.getDescricao().length())).collect(Collectors.toList());

                    PesquisaAdapter novo = new PesquisaAdapter(listaFiltro, especificacao -> {
                        setTextView(especificacao);
                        dialog.dismiss();

                    });

                    recyclerView.setAdapter(novo);

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.toString().equals("")) {
                        PesquisaAdapter novo = new PesquisaAdapter(list, especificacao -> {
                            setTextView(especificacao);
                            dialog.dismiss();

                        });
                        recyclerView.setAdapter(novo);
                    }
                }
            });
        });
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setTextView(Especificacoes especificacao) {
        textView.setText(String.format("%s - %s", especificacao.getCod(), especificacao.getDescricao()));
        setItemId(especificacao.getId());
    }

    public void setTextViewDefault(String texto) {
        textView.setText(texto);
    }
}
