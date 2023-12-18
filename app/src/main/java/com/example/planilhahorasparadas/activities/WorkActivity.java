package com.example.planilhahorasparadas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.planilhahorasparadas.util.MyApplicationContext;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private static RecyclerView recyclerView;
    private static List<Paradas> list = new ArrayList<>();
    private ImageButton buttonSync;
    private EditText editText;
    private Spinner spinner;
    private RetrofitControler retrofitControler;
    private static String dataParada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);


        Intent intent = getIntent();
        dataParada = intent.getStringExtra("data");


        Toolbar toolbar = findViewById(R.id.toolbarRoutes);
        toolbar.setTitle(dataParada);
        recyclerView = findViewById(R.id.recycleViewParadas);
        buttonSync = findViewById(R.id.imageButtonSync);
        spinner = findViewById(R.id.spinner);

        retrofitControler = new RetrofitControler();

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSignOutRoutes).setOnClickListener(this);
        findViewById(R.id.imageButtonSync).setOnClickListener(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);

        editText = findViewById(R.id.editTextObs);
        EditText editTextH = findViewById(R.id.editTextHoraF);
        editText.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE) {
                return addParada();
            }
            return false;
        });

        editTextH.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE) {
                hiddenKeyboard();

                spinner.requestFocus();
                spinner.performClick();
            }
            return false;
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    editText.requestFocus();
                    editText.performClick();
                    showKeyboard();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSignOutRoutes){
            logOut();
        }
        if (view.getId() == R.id.buttonAdd){
            addParada();
        }
        if (view.getId() ==R.id.imageButtonSync){
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
                parada.setData(dataParada);

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

    private void hiddenKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void showKeyboard() {

        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void logOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                    openLoginActivity();
                });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

    public static void setRecyclerView() {
        ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
        list = paradasDAO.getAllData(dataParada);
        ParadaAdapter adapter = new ParadaAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    public static boolean deleteData(Paradas parada) {
        ParadasDAO paradasDAO = new ParadasDAO(MyApplicationContext.getAppContext());
        if (paradasDAO.delete(parada)) {
            setRecyclerView();
            return true;
        }
        return false;
    }


}