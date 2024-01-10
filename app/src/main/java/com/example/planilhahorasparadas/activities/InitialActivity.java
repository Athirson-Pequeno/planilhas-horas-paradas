package com.example.planilhahorasparadas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.helpers.DBHelperAC;
import com.example.planilhahorasparadas.util.MyApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitialActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;
    public static String PRIMEIRA_EXECUCAO = "primeira execucao";
    private SQLiteDatabase db;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        sharedPreferences = getSharedPreferences(PRIMEIRA_EXECUCAO, MODE_PRIVATE);

        DBHelperAC dbHelper = new DBHelperAC(MyApplicationContext.getAppContext());
        this.db = dbHelper.getWritableDatabase();

        executor.execute(this::carregarBanco);

    }

    protected void carregarBanco() {

        Intent intent = new Intent(MyApplicationContext.getAppContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (sharedPreferences.getBoolean(PRIMEIRA_EXECUCAO, true)) {
            sharedPreferences.edit().putBoolean(PRIMEIRA_EXECUCAO, false).apply();
            try {
                insertFromFile(this, R.raw.datadb);
            } catch (IOException e) {

                e.printStackTrace();
            }
            startActivity(intent);
        } else {
            startActivity(intent);
        }
    }

    public void insertFromFile(Context context, int resourceId) throws IOException {

        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            if (!insertStmt.equals("")){db.execSQL(insertStmt);}

        }
        insertReader.close();
    }

}