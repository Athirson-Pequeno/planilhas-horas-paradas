package com.example.planilhahorasparadas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.activities.FragmentsViewActivity;
import com.example.planilhahorasparadas.adapter.ParadaAdapter;
import com.example.planilhahorasparadas.helpers.ParadasDAO;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.util.KeyBoardController;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ParadasFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Paradas> list = new ArrayList<>();
    private Spinner spinner;
    private ImageButton buttonAdd;
    private EditText  editTextHoraInicial, editTextHoraFim, editTextObservacao;
    private static final String DATA_ID = "Data_id";
    private static final String CELULA_SELECIONADA = "Celula_selecionada";
    private static final String HORARIO_SELECIONADO = "Horario_selecionado";
    private Integer dataId;
    private String celulaSelecionada, horarioSelecionado;

    public ParadasFragment() {
    }

    public static ParadasFragment newInstance(Integer data, String celula, String horario) {
        ParadasFragment fragment = new ParadasFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paradas, container, false);

        buttonAdd = view.findViewById(R.id.buttonAddFragmentParadas);
        recyclerView = view.findViewById(R.id.recycleViewParadasFragmentParadas);
        spinner = view.findViewById(R.id.spinnerCodParadasFragmentParadas);
        editTextHoraInicial = view.findViewById(R.id.editTextHoraInicialFragmentParadas);
        editTextHoraFim = view.findViewById(R.id.editTextHoraFimFragmentParadas);
        editTextObservacao = view.findViewById(R.id.editTextObservacaoFragmentParadas);


        editTextObservacao.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE) {
                addParada();
                return true;
            }
            return false;
        });

        editTextHoraFim.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE) {
                KeyBoardController.hiddenKeyboard();

                spinner.requestFocus();
                spinner.performClick();
            }
            return false;
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    editTextObservacao.requestFocus();
                    editTextObservacao.performClick();
                    KeyBoardController.showKeyboard();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        buttonAdd.setOnClickListener(view1 -> addParada());


        ((FragmentsViewActivity) requireActivity()).setFragmentParadasRefreshListener((dataIDAc, celula, horarioSpinner) -> {
            dataId = dataIDAc;
            celulaSelecionada = celula;
            horarioSelecionado = horarioSpinner;
            setRecyclerView(dataId, celulaSelecionada, horarioSelecionado);
        });


        return view;
    }

    private void addParada() {


        String cel = celulaSelecionada;
        String horaI = editTextHoraInicial.getText().toString();
        String horaF = editTextHoraFim.getText().toString();
        String obs = editTextObservacao.getText().toString();
        String cod = spinner.getSelectedItem().toString();

        if (spinner.getSelectedItemId() == 0) {
            cod = "";
        }


        if ((!cel.equals("")) && (!horaI.equals("")) && (!horaF.equals("")) && (!cod.equals(""))) {

            if (Integer.parseInt(horaI) < Integer.parseInt(horaF)) {

                Paradas parada = new Paradas();
                parada.setCelula(cel);
                parada.setHoraI(Integer.parseInt(horaI));
                parada.setHoraF(Integer.parseInt(horaF));
                parada.setCod(cod);
                parada.setObs(obs);
                parada.setDataId(dataId);
                parada.setHorario(horarioSelecionado);

                ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
                if (paradasDAO.save(parada)) {
                    setRecyclerView(dataId, celulaSelecionada, horarioSelecionado);

                    editTextHoraInicial.requestFocus();
                    editTextHoraInicial.performClick();

                    editTextHoraInicial.setText("");
                    editTextHoraFim.setText("");
                    editTextObservacao.setText("");
                    spinner.setSelection(0);

                }
            } else {
                Toast.makeText(MyApplicationContext.getAppContext(), "Hora inicial menor que hora final", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(MyApplicationContext.getAppContext(), "Preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
        }
    }

    public void setRecyclerView(Integer dataID, String celula, String horarioSpinner) {
        ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
        list = paradasDAO.buscarPorDataCelHorario(dataID, celula, horarioSpinner);
        ParadaAdapter adapter = new ParadaAdapter(list, parada -> new AlertDialog.Builder(requireActivity())
                .setTitle("Deletar data")
                .setMessage("Tem certeza que quer apagar essa parada?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    if (deleteData(parada)) {
                        Toast.makeText(requireActivity(), "Parada apagada", Toast.LENGTH_LONG).show();
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

    public boolean deleteData(Paradas parada) {
        ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
        return paradasDAO.delete(parada);
    }

}