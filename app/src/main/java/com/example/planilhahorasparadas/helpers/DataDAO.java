package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.planilhahorasparadas.models.Data;

import java.util.ArrayList;
import java.util.List;

public class DataDAO implements IDataDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public DataDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();

    }

    @Override
    public void save(Data data) throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data.getDataText());
        write.insertOrThrow(DBHelper.DATA_TABLE_NAME, null, contentValues);

    }

    @Override
    public boolean update(Data data) {
        return false;
    }

    @Override
    public boolean delete(Data data) {
        try {
            String[] args = {data.getId().toString()};
            write.delete(DBHelper.DATA_TABLE_NAME, "id=?", args);
            Log.i("INFO", "Data deletada");

        } catch (Exception e) {
            Log.i("INFO", "Erro ao deletar" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Data> getAll() {
        List<Data> listaData = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.DATA_TABLE_NAME + " ORDER BY id DESC;";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            Data novaData = new Data();
            @SuppressLint("Range") String dataText = cursor.getString(cursor.getColumnIndex("data"));
            @SuppressLint("Range") Integer dataId = cursor.getInt(cursor.getColumnIndex("id"));
            novaData.setId(dataId);
            novaData.setDataText(dataText);
            listaData.add(novaData);

        }
        cursor.close();
        return listaData;
    }
}
