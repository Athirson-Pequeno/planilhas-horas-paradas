package com.example.planilhahorasparadas.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.fragments.InutilizadoFragment;
import com.example.planilhahorasparadas.fragments.ParadasFragment;
import com.example.planilhahorasparadas.fragments.ProducaoFragment;
import com.example.planilhahorasparadas.helpers.ParadasDAO;
import com.example.planilhahorasparadas.models.Data;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.util.MyApplicationContext;
import com.example.planilhahorasparadas.util.UploadDataToSheets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FragmentsViewActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences = null;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Button buttonProducao, buttonParadas, buttonInutilizado;
    private Data data;
    private final String PRODUCAO_TAG = "producao";
    private final String PARADA_TAG = "paradas";
    private final String INUTILIZADO_TAG = "inutilizado";
    private Spinner spinnerCelulas, spinnerHorario;
    private List<String> listaCelulas = new ArrayList<>();
    private FragmentRefreshListener fragmentRefreshListener;
    private ImageButton imageButtonSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_view);

        data = (Data) getIntent().getSerializableExtra("dataObject");
        Toolbar toolbar = findViewById(R.id.toolbarFragments);
        toolbar.setTitle("Dia: " + data.getDataText());

        sharedPreferences = getSharedPreferences(ConfigActivity.CONFIGURACOES, MODE_PRIVATE);
        spinnerCelulas = findViewById(R.id.spinnerCelulas);
        spinnerHorario = findViewById(R.id.spinnerHorarios);
        String listaCelulasString = sharedPreferences.getString(ConfigActivity.LISTA_CELULAS, "71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96");
        listaCelulas = Arrays.asList(listaCelulasString.split(","));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_celula_item, listaCelulas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCelulas.setAdapter(adapter);

        iniciarFragmentos();
        configurarBotoes();
        spinnerHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getFragmentRefreshListener() != null) {
                    getFragmentRefreshListener().onRefresh(data.getId(), spinnerCelulas.getSelectedItem().toString(), spinnerHorario.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCelulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getFragmentRefreshListener() != null) {
                    getFragmentRefreshListener().onRefresh(data.getId(), spinnerCelulas.getSelectedItem().toString(), spinnerHorario.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void iniciarFragmentos() {

        Fragment fragmentInutilizado = InutilizadoFragment.newInstance(data.getDataText(), data.getId().toString());
        Fragment fragmentParadas = ParadasFragment.newInstance(data.id, spinnerCelulas.getSelectedItem().toString(), spinnerHorario.getSelectedItem().toString());

        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentInutilizado, INUTILIZADO_TAG).commit();

        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentParadas, PARADA_TAG).commit();

        fragmentManager.beginTransaction().add(R.id.frameLayout, new ProducaoFragment(), PRODUCAO_TAG).commit();

        fragmentManager.beginTransaction().hide(fragmentInutilizado).commit();
        fragmentManager.beginTransaction().hide(fragmentParadas).commit();
    }

    private void configurarBotoes() {

        imageButtonSync = findViewById(R.id.imageButtonSyncFragments);
        imageButtonSync.setOnClickListener(view -> {
            ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
            List<Paradas> paradasNaoSalvas = paradasDAO.buscarNaoSalvos();
            if (paradasNaoSalvas.isEmpty()) {
                Toast.makeText(this, "Todos itens já foram salvos", Toast.LENGTH_SHORT).show();
            } else {
                UploadDataToSheets uploadDataToSheets = new UploadDataToSheets();
                uploadDataToSheets.uploadParadas(paradasNaoSalvas, imageButtonSync);
                paradasNaoSalvas.forEach(paradasDAO::atualizarNaoSalvos);
            }

        });

        buttonProducao = findViewById(R.id.buttonFragmentProducao);
        buttonProducao.setOnClickListener(this);
        buttonProducao.setBackgroundColor(Color.parseColor("#412883"));

        buttonParadas = findViewById(R.id.buttonFragmentParadas);
        buttonParadas.setOnClickListener(this);

        buttonInutilizado = findViewById(R.id.buttonFragmentInutilizado);
        buttonInutilizado.setOnClickListener(this);

        findViewById(R.id.buttonCelulaAnterior).setOnClickListener(view -> {
            if (spinnerCelulas.getSelectedItemId() == 0) {
                spinnerCelulas.setSelection(listaCelulas.size() - 1);
            } else {
                spinnerCelulas.setSelection((int) spinnerCelulas.getSelectedItemId() - 1);
            }
            if (getFragmentRefreshListener() != null) {
                getFragmentRefreshListener().onRefresh(data.getId(), spinnerCelulas.getSelectedItem().toString(), spinnerHorario.getSelectedItem().toString());
            }
        });

        findViewById(R.id.buttonProximaCelula).setOnClickListener(view -> {
            if (spinnerCelulas.getSelectedItemId() == listaCelulas.size() - 1) {
                spinnerCelulas.setSelection(0);
            } else {
                spinnerCelulas.setSelection((int) spinnerCelulas.getSelectedItemId() + 1);
            }
            if (getFragmentRefreshListener() != null) {
                getFragmentRefreshListener().onRefresh(data.getId(), spinnerCelulas.getSelectedItem().toString(), spinnerHorario.getSelectedItem().toString());
            }
        });

    }

    @Override
    public void onClick(View view) {

        String selecionado = "";
        String fragment1 = "";
        String fragment2 = "";
        Fragment fragmentSelecionado = new ProducaoFragment();

        if (view.getId() == R.id.buttonFragmentProducao) {
            selecionado = PRODUCAO_TAG;
            fragment1 = PARADA_TAG;
            fragment2 = INUTILIZADO_TAG;
            fragmentSelecionado = new ProducaoFragment();
            setColorButton(buttonProducao);
        }
        if (view.getId() == R.id.buttonFragmentParadas) {
            selecionado = PARADA_TAG;
            fragment1 = PRODUCAO_TAG;
            fragment2 = INUTILIZADO_TAG;
            fragmentSelecionado = new ParadasFragment();
            setColorButton(buttonParadas);
        }
        if (view.getId() == R.id.buttonFragmentInutilizado) {
            selecionado = INUTILIZADO_TAG;
            fragment1 = PARADA_TAG;
            fragment2 = PRODUCAO_TAG;
            fragmentSelecionado = InutilizadoFragment.newInstance(data.getDataText(), data.getId().toString());
            setColorButton(buttonInutilizado);
        }

        if (fragmentManager.findFragmentByTag(selecionado) != null) {
            fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag(selecionado))).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentSelecionado, selecionado).commit();
        }

        if (fragmentManager.findFragmentByTag(fragment1) != null) {
            fragmentManager.beginTransaction().hide(Objects.requireNonNull(fragmentManager.findFragmentByTag(fragment1))).commit();
        }
        if (fragmentManager.findFragmentByTag(fragment2) != null) {
            fragmentManager.beginTransaction().hide(Objects.requireNonNull(fragmentManager.findFragmentByTag(fragment2))).commit();
        }
    }

    public void setColorButton(Button button) {
        buttonProducao.setBackgroundColor(Color.parseColor("#6750A4"));
        buttonParadas.setBackgroundColor(Color.parseColor("#6750A4"));
        buttonInutilizado.setBackgroundColor(Color.parseColor("#6750A4"));
        button.setBackgroundColor(Color.parseColor("#412883"));
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener {
        void onRefresh(Integer dataID, String celula, String horarioSpinner);
    }

}