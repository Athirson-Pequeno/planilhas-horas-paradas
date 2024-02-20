package com.example.planilhahorasparadas.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.activities.FragmentsViewActivity;
import com.example.planilhahorasparadas.adapter.ProducaoAdapter;
import com.example.planilhahorasparadas.helpers.ArtigosDAO;
import com.example.planilhahorasparadas.helpers.CoresDAO;
import com.example.planilhahorasparadas.helpers.ProducaoDAO;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.models.Producao;
import com.example.planilhahorasparadas.util.DialogPesquisaEspecificacoes;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProducaoFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Producao> list = new ArrayList<>();
    private ImageButton buttonAdd;
    private EditText editTextQuantidade;
    private List<Especificacoes> listaCores = new ArrayList<>();
    private List<Especificacoes> listaArtigos = new ArrayList<>();
    private static final String DATA_ID = "Data_id";
    private static final String CELULA_SELECIONADA = "Celula_selecionada";
    private static final String HORARIO_SELECIONADO = "Horario_selecionado";
    private Integer dataId;
    private String celulaSelecionada, horarioSelecionado;
    private CoresDAO coresDAO = new CoresDAO(MyApplicationContext.getAppContext());
    private ArtigosDAO artigosDAO = new ArtigosDAO(MyApplicationContext.getAppContext());
    private DialogPesquisaEspecificacoes dialogPesquisaEspecificacoes, dialogPesquisaArtigos;
    private Integer idCor;
    private Integer idArtigo;
    private Spinner spinnerTamanho;
    private List<String> itensSpinnerTamanho = new ArrayList<>();

    public ProducaoFragment() {

    }

    public static ProducaoFragment newInstance(Integer data, String celula, String horario) {
        ProducaoFragment fragment = new ProducaoFragment();
        Bundle args = new Bundle();
        args.putString(HORARIO_SELECIONADO, horario);
        args.putString(CELULA_SELECIONADA, celula);
        args.putInt(DATA_ID, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataId = getArguments().getInt(DATA_ID);
            celulaSelecionada = getArguments().getString(CELULA_SELECIONADA);
            horarioSelecionado = getArguments().getString(HORARIO_SELECIONADO);
        }


        listaCores = coresDAO.getAll();
        listaArtigos = artigosDAO.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producao, container, false);

        itensSpinnerTamanho.addAll(Arrays.asList("Tamanho","23/4","25/6","27/8","29/0","31/2","33/4","35/6","37/8","39/0","41/2","43/4","45/6","47/8"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, itensSpinnerTamanho);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTamanho = view.findViewById(R.id.spinnerTamanho);
        spinnerTamanho.setAdapter(adapter);



        TextView textViewArtigos = view.findViewById(R.id.textViewArtigosFragmentProducao);
        TextView textViewCores = view.findViewById(R.id.textViewCoresFragmentProducao);
        buttonAdd = view.findViewById(R.id.buttonAddProducaoFragment);
        recyclerView = view.findViewById(R.id.recyclerViewProducaoFragment);
        editTextQuantidade = view.findViewById(R.id.editTextQuantidadeFragmentProducao);


        dialogPesquisaEspecificacoes = new DialogPesquisaEspecificacoes(listaCores);
        dialogPesquisaArtigos = new DialogPesquisaEspecificacoes(listaArtigos);

        dialogPesquisaEspecificacoes.MostrarDialog(getActivity(), textViewCores, requireActivity());
        dialogPesquisaArtigos.MostrarDialog(getActivity(), textViewArtigos, requireActivity());


        buttonAdd.setOnClickListener(viewOnClick -> addProducao());

        ((FragmentsViewActivity) requireActivity()).setFragmentProducaoRefreshListener((dataIDAc, celula, horarioSpinner) -> {
            dataId = dataIDAc;
            celulaSelecionada = celula;
            horarioSelecionado = horarioSpinner;

            setRecyclerView(dataId, celulaSelecionada, horarioSelecionado);
        });
        return view;
    }

    private void setRecyclerView(Integer dataID, String celula, String horarioSpinner) {
        ProducaoDAO producaoDAO = new ProducaoDAO(MyApplicationContext.getAppContext());
        list = producaoDAO.buscarPorDataCelHorario(dataID, celula, horarioSpinner);

        List<Producao> listDataCel = producaoDAO.buscarPorDataCel(dataID, celula);
        if (!listDataCel.isEmpty()) {
            dialogPesquisaArtigos.setTextView(artigosDAO.getById(listDataCel.get(0).getIdArtigo()).get(0));
            dialogPesquisaEspecificacoes.setTextView(coresDAO.getByID(listDataCel.get(0).getIdCor()).get(0));
            spinnerTamanho.setSelection(itensSpinnerTamanho.indexOf(listDataCel.get(0).getTamanho()));
        }else {
            dialogPesquisaArtigos.setTextViewDefault("Clique aqui para selecionar o artigo.");
            dialogPesquisaEspecificacoes.setTextViewDefault("Clique aqui para selecionar a cor.");
            spinnerTamanho.setSelection(0);
        }

        ProducaoAdapter adapter = new ProducaoAdapter(list, producao ->
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Deletar data")
                        .setMessage("Tem certeza que quer apagar essa produção?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            if (producaoDAO.delete(producao)) {
                                Toast.makeText(requireActivity(), "Produção apagada.", Toast.LENGTH_LONG).show();
                                setRecyclerView(dataID, celula, horarioSpinner);
                            }
                        })
                        .setNegativeButton("Não", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    private void addProducao() {
        Producao producao = new Producao();

        idCor = dialogPesquisaEspecificacoes.getItemId();
        idArtigo = dialogPesquisaArtigos.getItemId();
        String quantidade = editTextQuantidade.getText().toString();

        String tamanho = spinnerTamanho.getSelectedItem().toString();

        if (!idArtigo.toString().equals("") && !idCor.toString().equals("") && !quantidade.equals("") && !tamanho.equals("Tamanho")){
            Especificacoes corSelecionada = coresDAO.getByID(dialogPesquisaEspecificacoes.getItemId()).get(0);
            Especificacoes artigoSelecionado = artigosDAO.getById(dialogPesquisaArtigos.getItemId()).get(0);

            producao.setTamanho(tamanho);
            producao.setIdCor(idCor);
            producao.setIdArtigo(idArtigo);
            producao.setCelula(celulaSelecionada);
            producao.setHorario(horarioSelecionado);
            producao.setDataId(dataId);
            producao.setCodigoArtigo(artigoSelecionado.getCod());
            producao.setDescricaoArtigo(artigoSelecionado.getDescricao());
            producao.setCodigoCor(corSelecionada.getCod());
            producao.setDescricaoCor(corSelecionada.getDescricao());
            producao.setQuantidadeProduzida(Integer.valueOf(quantidade));

            ProducaoDAO producaoDAO = new ProducaoDAO(MyApplicationContext.getAppContext());
            producaoDAO.save(producao);
            setRecyclerView(dataId, celulaSelecionada, horarioSelecionado);
            editTextQuantidade.setText("");
        }else {
             Toast.makeText(requireActivity(), "Preencha todos os dados.", Toast.LENGTH_SHORT).show();
        }

    }
}