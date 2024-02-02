package com.example.planilhahorasparadas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planilhahorasparadas.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.buttonLogin).setOnClickListener(this);

        findViewById(R.id.buttonLogin).setOnLongClickListener(view -> {
            signIn();
            return true;
        });

        findViewById(R.id.buttonConfiguracaoLogin).setOnClickListener(view -> {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);


    }

    protected void onStart() {
        super.onStart();
       GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){
            openSelectDataActivity(account);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonLogin){
           signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                System.out.println(result.getResultCode());
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    System.out.println(result.getData());
                    if (data != null){
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);

                    }
                }
            }
    );


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            openSelectDataActivity(account);
            Toast.makeText(getApplicationContext(), "Logado com: " + account.getEmail(), Toast.LENGTH_LONG).show();
        } catch (ApiException e) {
            Log.i(TAG, "Erro ao conectar: " + e.getStatusCode());
            Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();
        }
    }

    private void openSelectDataActivity(GoogleSignInAccount signInCredential) {
        Intent intent = new Intent(this, SelectDateActivity.class);
        intent.putExtra("accountEmail", signInCredential.getEmail());
        intent.putExtra("accountName", signInCredential.getDisplayName());

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}