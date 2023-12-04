package com.example.planilhahorasparadas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.adapter.DataAdapter;
import com.example.planilhahorasparadas.adapter.ParadaAdapter;
import com.example.planilhahorasparadas.helpers.DataDAO;
import com.example.planilhahorasparadas.helpers.ParadasDAO;
import com.example.planilhahorasparadas.helpers.RetrofitControler;
import com.example.planilhahorasparadas.models.Data;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class SelectDateActivity extends AppCompatActivity implements View.OnClickListener {
    private static List<Data> list = new ArrayList<>();
    private static RecyclerView recyclerView;
    private static DataAdapter adapter;
    private Toolbar toolbar;
    private Intent intent;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText editText;
    private static Context contextApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        intent = getIntent();
        recyclerView = findViewById(R.id.recyclerViewData);
        toolbar = findViewById(R.id.toolbarDate);
        editText = findViewById(R.id.editTextData);
        contextApp = getApplicationContext();

        findViewById(R.id.buttonAddData).setOnClickListener(this);
        findViewById(R.id.buttonSignOutSelect).setOnClickListener(this);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    addData();
                }
                return false;
            }
        });

        setmGoogleSignInClient();
        setToolbar();
        setRecyclerView();
    }

    private void addData() {

        Data data = new Data();
        data.setDataText(editText.getText().toString());
        DataDAO dataDAO = new DataDAO(getApplicationContext());
        dataDAO.save(data);
        editText.setText("");
        setRecyclerView();
        hidenKeyboard();
    }


    public static void setRecyclerView(){
        DataDAO dataDAO = new DataDAO(contextApp);
        list = dataDAO.getAll();
        adapter = new DataAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(contextApp);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(contextApp, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void setToolbar(){
        toolbar.setTitle(intent.getStringExtra("accountName"));
        Toast.makeText(getApplicationContext(),"Conectado com " +  intent.getStringExtra("accountEmail"), Toast.LENGTH_LONG).show();
    }

    private void logout() {
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
        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void setmGoogleSignInClient(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);
    }

    public static boolean deleteData(Data data){
        DataDAO dataDAO = new DataDAO(contextApp);
        ParadasDAO paradasDAO = new ParadasDAO(contextApp);
        dataDAO.delete(data);
        paradasDAO.deleteByData(data.getDataText());
        setRecyclerView();
        return true;
    }
    private void hidenKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAddData:
                addData();
                break;
            case R.id.buttonSignOutSelect:
                logout();
                break;
        }
    }


}