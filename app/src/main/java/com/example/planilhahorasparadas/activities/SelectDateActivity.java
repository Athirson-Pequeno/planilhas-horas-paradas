package com.example.planilhahorasparadas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.adapter.DataAdapter;
import com.example.planilhahorasparadas.helpers.DataDAO;
import com.example.planilhahorasparadas.models.Data;
import com.example.planilhahorasparadas.util.GoogleSignInUtil;
import com.example.planilhahorasparadas.util.MyApplicationContext;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.List;
import java.util.Objects;

public class SelectDateActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText editText;
    private DataDAO dataDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        recyclerView = findViewById(R.id.recyclerViewData);
        toolbar = findViewById(R.id.toolbarRoutes);
        editText = findViewById(R.id.editTextData);

        dataDAO = new DataDAO(MyApplicationContext.getAppContext());

        findViewById(R.id.buttonAddData).setOnClickListener(this);
        findViewById(R.id.buttonSignOutRoutes).setOnClickListener(this);


        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                addData();
            }
            return false;
        });

        setMGoogleSignInClient();
        setToolbar();
        setRecyclerView();
    }

    private void addData() {
        Data data = new Data();
        String dataText = editText.getText().toString();

        if (!dataText.equals("")) {
            data.setDataText(dataText);
            try {
                dataDAO.save(data);
                setRecyclerView();
                hiddenKeyboard();
            } catch (Exception e) {
                if (Objects.requireNonNull(e.getMessage()).contains("UNIQUE constraint failed")) {

                    Toast.makeText(MyApplicationContext.getAppContext(), "Data já existente", Toast.LENGTH_SHORT).show();
                }
                Log.i("INFO", "Data not saved: " + e.getMessage());
            }
        } else {
            Toast.makeText(MyApplicationContext.getAppContext(), "Preencha o campo nome da data", Toast.LENGTH_SHORT).show();
        }
    }


    public void setRecyclerView() {
        List<Data> list = dataDAO.getAll();
        DataAdapter adapter = new DataAdapter(list, data -> {
            new AlertDialog.Builder(this)
                    .setTitle("Deletar data")
                    .setMessage("Tem certeza que quer apagar a data " + data.getDataText() + " e todas as paradas relacionada a ela?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        if (deleteData(data)) {
                            Toast.makeText(this, "Data " + data.getDataText() + " apagada", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void setToolbar() {
        toolbar.setTitle("Selection uma data e horário");
    }


    private void setMGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);
    }

    public boolean deleteData(Data data) {
        if (dataDAO.delete(data)) {
            setRecyclerView();
            return true;
        }
        return false;
    }

    private void hiddenKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddData) {
            addData();
        }
        if (view.getId() == R.id.buttonSignOutRoutes) {
            GoogleSignInUtil.logout(this);
        }
    }


}