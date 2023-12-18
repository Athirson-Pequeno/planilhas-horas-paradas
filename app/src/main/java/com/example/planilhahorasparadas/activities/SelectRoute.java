package com.example.planilhahorasparadas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planilhahorasparadas.R;
import com.example.planilhahorasparadas.util.GoogleSignInUtil;

public class SelectRoute extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        findViewById(R.id.buttonHoras).setOnClickListener(this);
        findViewById(R.id.buttonInutilizado).setOnClickListener(this);
        findViewById(R.id.buttonSignOutRoutes).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(R.id.buttonHoras == view.getId()){
            Intent intent = new Intent(this, SelectDateActivity.class);
            startActivity(intent);
        }
        if(R.id.buttonInutilizado == view.getId()){
            Intent intent = new Intent(this, DefectsWorkActivity.class);
            startActivity(intent);
        }
        if(R.id.buttonSignOutRoutes == view.getId()){
            GoogleSignInUtil.logout(this);
        }
    }
}