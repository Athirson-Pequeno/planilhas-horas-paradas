package com.example.planilhahorasparadas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class WorkActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private static RecyclerView recyclerView;
    private static ParadaAdapter adapter;
    private static List<Paradas> list = new ArrayList<>();
    private Toolbar toolbar;
    private static ImageButton buttonSync;
    private EditText editText;
    private EditText editTextH;
    private Spinner spinner;
    private RetrofitControler retrofitControler;
    private static String dataParada;

    private static Context contextApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        Intent intent = getIntent();
        dataParada = intent.getStringExtra("data");

        contextApp = getApplicationContext();

        toolbar = findViewById(R.id.toolbarDate);
        toolbar.setTitle(dataParada);
        recyclerView = findViewById(R.id.recycleViewParadas);
        buttonSync = findViewById(R.id.imageButtonSync);
        spinner = findViewById(R.id.spinner);

        retrofitControler = new RetrofitControler();

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSignOutSelect).setOnClickListener(this);
        findViewById(R.id.imageButtonSync).setOnClickListener(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);

        editText = findViewById(R.id.editTextObs);
        editTextH = findViewById(R.id.editTextHoraF);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == EditorInfo.IME_ACTION_DONE){
                    addParada();
                }
                return false;
            }
        });

        editTextH.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == EditorInfo.IME_ACTION_DONE){
                    hidenKeyboard();

                    spinner.requestFocus();
                    spinner.performClick();
                }
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
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
        switch (view.getId()){
            case R.id.buttonSignOutSelect:
                logOut();
                break;
            case R.id.buttonAdd:
                addParada();
                break;
            case R.id.imageButtonSync:
                addOnTable();
                break;
        }
    }

    private void addOnTable() {
        spinButtonSync();
        retrofitControler.saveParada(list, (account.getDisplayName() + " " + account.getEmail()) ,getApplicationContext());
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
        String cod = spinner.getSelectedItem().toString().substring(0,5);

        if(spinner.getSelectedItemId() == 0){
            cod = "";
        }


        if((!cel.equals("")) && (!horaI.equals("")) && (!horaF.equals(""))&& (!cod.equals(""))){

            if(Integer.valueOf(horaI) < Integer.valueOf(horaF)){

            Paradas parada = new Paradas();
            parada.setCelula(cel);
            parada.setHoraI(Integer.valueOf(horaI));
            parada.setHoraF(Integer.valueOf(horaF));
            parada.setCod(cod);
            parada.setObs(obs);
            parada.setData(dataParada);

            ParadasDAO paradasDAO = new ParadasDAO(getApplicationContext());
            paradasDAO.save(parada);
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
            Toast.makeText(getApplicationContext(), "Hora inicial menor que hora final", Toast.LENGTH_LONG).show();
            return false;
        }

        Toast.makeText(getApplicationContext(), "Preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
        return false;
    }

    private void hidenKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void showKeyboard(){

        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void logOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Logout", Toast.LENGTH_LONG).show();
                        openLoginActivity();
                    }
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
        if (currentAccount != null){
            account = currentAccount;

        }
        setRecyclerView();
    }

    public static void setRecyclerView(){
        ParadasDAO paradasDAO = new ParadasDAO(contextApp);
        list = paradasDAO.getAllData(dataParada);

        adapter = new ParadaAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(contextApp);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(contextApp, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void spinButtonSync(){
        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_spin);
        buttonSync.startAnimation(rotation);
    }
    public static void stopBtnAnimation() {
        buttonSync.clearAnimation();
    }

    public static boolean deleteData(Paradas parada) {
        ParadasDAO paradasDAO = new ParadasDAO(contextApp);
        paradasDAO.delete(parada);
        setRecyclerView();
        return true;
    }


}