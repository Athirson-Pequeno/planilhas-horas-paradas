package com.example.planilhahorasparadas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.adapter.ParadaAdapter;
import com.example.planilhahorasparadas.helpers.ParadasDAO;
import com.example.planilhahorasparadas.helpers.RetrofitControler;
import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.util.GoogleSignInUtil;
import com.example.planilhahorasparadas.util.KeyBoardController;
import com.example.planilhahorasparadas.util.MyApplicationContext;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<Paradas> list = new ArrayList<>();
    private Spinner spinner;
    private GoogleSignInAccount account;
    private ImageButton buttonSync;
    private RetrofitControler retrofitControler;
    private Integer dataId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);


        Intent intent = getIntent();
        String dataParada = intent.getStringExtra("data");
        dataId = intent.getIntExtra("dataId", 0);


        Toolbar toolbar = findViewById(R.id.toolbarRoutes);
        toolbar.setTitle(dataParada);
        recyclerView = findViewById(R.id.recycleViewParadas);
        buttonSync = findViewById(R.id.imageButtonSync);
        spinner = findViewById(R.id.spinner);

        retrofitControler = new RetrofitControler();

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSignOutRoutes).setOnClickListener(this);
        findViewById(R.id.imageButtonSync).setOnClickListener(this);


        EditText editTextObs = findViewById(R.id.editTextObs);
        EditText editTextH = findViewById(R.id.editTextHoraF);
        editTextObs.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE) {
                return addParada();
            }
            return false;
        });

        editTextH.setOnEditorActionListener((textView, id, keyEvent) -> {
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
                    editTextObs.requestFocus();
                    editTextObs.performClick();
                    KeyBoardController.showKeyboard();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSignOutRoutes) {
            GoogleSignInUtil.logout(this);
        }
        if (view.getId() == R.id.buttonAdd) {
            addParada();
        }
        if (view.getId() == R.id.imageButtonSync) {
            addOnTable();
        }
    }

    private void addOnTable() {
        retrofitControler.saveParada(list, (account.getDisplayName() + " " + account.getEmail()), getApplicationContext(), buttonSync);
    }

    private boolean addParada() {

        EditText editCel = findViewById(R.id.editTextCel);
        EditText editHoraI = findViewById(R.id.editTextHoraI);
        EditText editHoraF = findViewById(R.id.editTextHoraF);
        EditText editObs = findViewById(R.id.editTextObs);

        String cel = editCel.getText().toString();
        String horaI = editHoraI.getText().toString();
        String horaF = editHoraF.getText().toString();
        String obs = editObs.getText().toString();
        String cod = spinner.getSelectedItem().toString().substring(0, 5);

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

                ParadasDAO paradasDAO = new ParadasDAO(getApplicationContext());
                if (paradasDAO.save(parada)) {
                    setRecyclerView();

                    editCel.requestFocus();
                    editCel.performClick();

                    editCel.setText("");
                    editHoraI.setText("");
                    editHoraF.setText("");
                    editObs.setText("");
                    spinner.setSelection(0);

                    return true;
                }
            }
            Toast.makeText(getApplicationContext(), "Hora inicial menor que hora final", Toast.LENGTH_LONG).show();
            return false;
        }

        Toast.makeText(getApplicationContext(), "Preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount currentAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (currentAccount != null) {
            account = currentAccount;
        }
        setRecyclerView();
    }

    public void setRecyclerView() {
        ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
        list = paradasDAO.getAllData(dataId);
        ParadaAdapter adapter = new ParadaAdapter(list, parada -> new AlertDialog.Builder(this)
                .setTitle("Deletar data")
                .setMessage("Tem certeza que quer apagar essa parada?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    if (deleteData(parada)) {
                        Toast.makeText(this, "Parada apagada", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Não", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    public boolean deleteData(Paradas parada) {
        ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
        if (paradasDAO.delete(parada)) {
            setRecyclerView();
            return true;
        }
        return false;
    }

}