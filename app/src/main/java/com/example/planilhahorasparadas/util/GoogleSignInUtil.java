package com.example.planilhahorasparadas.util;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.planilhahorasparadas.activities.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleSignInUtil {

    public static GoogleSignInClient setMGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(MyApplicationContext.getAppContext(), googleSignInOptions);
    }

    public static void logout(Activity activity) {

        GoogleSignInClient mGoogleSignInClient = setMGoogleSignInClient();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, task -> {
                    Toast.makeText(activity, "Logout", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                });
    }
}
