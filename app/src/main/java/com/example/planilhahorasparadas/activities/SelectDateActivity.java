package com.example.planilhahorasparadas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class SelectDateActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DataDAO dataDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        recyclerView = findViewById(R.id.recyclerViewData);
        toolbar = findViewById(R.id.toolbarRoutes);

        dataDAO = new DataDAO(MyApplicationContext.getAppContext());

        findViewById(R.id.buttonSignOutRoutes).setOnClickListener(this);
        findViewById(R.id.buttonCalendarPicker).setOnClickListener(this);


        setToolbar();
        setRecyclerView();
    }

    private void addData(String diaDoMes) {
        Data data = new Data();


        if (!diaDoMes.equals("")) {
            data.setDataText(diaDoMes);
            try {
                dataDAO.save(data);
                setRecyclerView();
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
        DataAdapter adapter = new DataAdapter(list, data -> new AlertDialog.Builder(this)
                .setTitle("Deletar data")
                .setMessage("Tem certeza que quer apagar a data " + data.getDataText() + " e todas as paradas relacionada a ela?")
                .setPositiveButton("Sim", (dialog, which) -> deleteData(data))
                .setNegativeButton("Não", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show(), data -> {
            Intent intent = new Intent(this, FragmentsViewActivity.class);
            intent.putExtra("data", data.getDataText());
            intent.putExtra("dataId", data.getId());
            intent.putExtra("dataObject", data);
            this.startActivity(intent);
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApplicationContext.getAppContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplicationContext.getAppContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void setToolbar() {
        toolbar.setTitle("Selecione a data");
    }


    public void deleteData(Data data) {
        if (dataDAO.delete(data)) {
            setRecyclerView();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSignOutRoutes) {
            GoogleSignInUtil.logout(this);
        }
        if (view.getId() == R.id.buttonCalendarPicker) {
            calendarPicker();
        }
    }

    private void calendarPicker() {

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione uma data")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            String diaDoMes = String.format("%02d/%02d/%s", calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            addData(diaDoMes);
        });

        datePicker.show(getSupportFragmentManager(), "datePicker");
    }


}