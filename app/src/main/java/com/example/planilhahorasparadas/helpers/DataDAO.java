package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.planilhahorasparadas.models.Data;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.ArrayList;
import java.util.List;

public class DataDAO implements IDataDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public DataDAO(Context context) {
        DBDataHelper dbDataHelper = new DBDataHelper(context);
        this.write = dbDataHelper.getWritableDatabase();
        this.read = dbDataHelper.getReadableDatabase();

    }

    @Override
    public boolean save(Data data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data.getDataText());
        try {
            write.insert(DBDataHelper.TABLE_NAME, null, contentValues);
            Log.i("INFO", "Data Save");
        }catch (Exception e){
            Log.i("INFO", "Data not saved: "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Data data) {
        return false;
    }

    @Override
    public boolean delete(Data data) {
        try {

            String[] args = {data.getId().toString()};
            write.delete(DBDataHelper.TABLE_NAME, "id=?", args);
            Log.i("INFO", "Data deletada");

        }catch (Exception e){
            Log.i("INFO", "Erro ao deletar"+ e.getMessage());
            return  false;
        }

        return true;
    }

    @Override
    public List<Data> getAll() {
        List<Data> listaData = new ArrayList<>();

        String sql = "SELECT * FROM " + DBDataHelper.TABLE_NAME + " ORDER BY id DESC;";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()){

            Data novaData = new Data();
            @SuppressLint("Range") String dataText = cursor.getString(cursor.getColumnIndex("data"));
            @SuppressLint("Range") Integer dataId = cursor.getInt(cursor.getColumnIndex("id"));
            novaData.setId(dataId);
            novaData.setDataText(dataText);
            listaData.add(novaData);
            
        }

        return listaData;
    }
}
