package com.example.planilhahorasparadas.util;

import android.widget.ImageButton;

import com.example.planilhahorasparadas.helpers.RetrofitControler;
import com.example.planilhahorasparadas.models.Paradas;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class UploadDataToSheets {
    private RetrofitControler retrofitControler = new RetrofitControler();

    public boolean uploadParadas(List<Paradas> list, ImageButton buttonSync) {
        GoogleSignInAccount account = GoogleSignInUtil.getAccount();
        return retrofitControler.saveParada(list, (account.getDisplayName() + " " + account.getEmail()), MyApplicationContext.getAppContext(), buttonSync);

    }
}
