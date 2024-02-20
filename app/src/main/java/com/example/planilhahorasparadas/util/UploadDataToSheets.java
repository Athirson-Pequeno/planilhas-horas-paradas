package com.example.planilhahorasparadas.util;

import android.widget.ImageButton;

import com.example.planilhahorasparadas.helpers.RetrofitControler;
import com.example.planilhahorasparadas.models.Paradas;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;
import java.util.List;

public class UploadDataToSheets {
    private RetrofitControler retrofitControler = new RetrofitControler();

    public boolean uploadParadas(HashMap map, ImageButton buttonSync, String data) {
        GoogleSignInAccount account = GoogleSignInUtil.getAccount();
        return retrofitControler.saveParada(map, (account.getDisplayName() + " " + account.getEmail()), data, MyApplicationContext.getAppContext(), buttonSync);

    }
}
