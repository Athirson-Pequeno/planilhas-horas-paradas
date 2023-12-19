package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.planilhahorasparadas.models.Paradas;

import java.util.ArrayList;
import java.util.List;

public class ParadasDAO implements IParadasDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public ParadasDAO(Context context) {
        DBHelper dbParadasHelper = new DBHelper(context);
        this.write = dbParadasHelper.getWritableDatabase();
        this.read = dbParadasHelper.getReadableDatabase();

    }

    @Override
    public boolean save(Paradas parada) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cel", parada.getCelula());
        contentValues.put("horaI", parada.getHoraI());
        contentValues.put("horaF", parada.getHoraF());
        contentValues.put("obs", parada.getObs());
        contentValues.put("cod", parada.getCod());
        contentValues.put("dataId", parada.getDataId());

        try {
            write.insert(DBHelper.PARADA_TABLE_NAME, null, contentValues);
            Log.i("INFO", "Parada Save");
        } catch (Exception e) {
            Log.i("INFO", "Parada not saved: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Paradas parada) {
        return false;
    }

    @Override
    public boolean delete(Paradas parada) {
        try {

            String[] args = {parada.getId().toString()};
            write.delete(DBHelper.PARADA_TABLE_NAME, "id=?", args);
            Log.i("INFO", "Parada deletada");

        } catch (Exception e) {
            Log.i("INFO", "Erro ao deletar" + e.getMessage());
            return false;
        }

        return true;
    }


    @Override
    public List<Paradas> getAll() {

        List<Paradas> listParadas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.PARADA_TABLE_NAME + " ORDER BY id DESC;";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            Paradas paradaDB = new Paradas();

            @SuppressLint("Range") String cel = cursor.getString(cursor.getColumnIndex("cel"));
            @SuppressLint("Range") Integer horaI = cursor.getInt(cursor.getColumnIndex("horaI"));
            @SuppressLint("Range") Integer horaF = cursor.getInt(cursor.getColumnIndex("horaF"));
            @SuppressLint("Range") String obs = cursor.getString(cursor.getColumnIndex("obs"));
            @SuppressLint("Range") String cod = cursor.getString(cursor.getColumnIndex("cod"));
            @SuppressLint("Range") Integer paradaId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") Integer dataId = cursor.getInt(cursor.getColumnIndex("dataId"));

            paradaDB.setCelula(cel);
            paradaDB.setHoraI(horaI);
            paradaDB.setHoraF(horaF);
            paradaDB.setObs(obs);
            paradaDB.setCod(cod);
            paradaDB.setId(paradaId);
            paradaDB.setDataId(dataId);
            listParadas.add(paradaDB);
        }
        cursor.close();
        return listParadas;
    }


    @Override
    public List<Paradas> getAllData(Integer dataID) {
        List<Paradas> listParadas = new ArrayList<>();
        String sql = "SELECT * FROM " + DBHelper.PARADA_TABLE_NAME + " WHERE dataId=" + dataID + " ORDER BY id DESC;";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            Paradas paradaDB = new Paradas();

            @SuppressLint("Range") String cel = cursor.getString(cursor.getColumnIndex("cel"));
            @SuppressLint("Range") Integer horaI = cursor.getInt(cursor.getColumnIndex("horaI"));
            @SuppressLint("Range") Integer horaF = cursor.getInt(cursor.getColumnIndex("horaF"));
            @SuppressLint("Range") String obs = cursor.getString(cursor.getColumnIndex("obs"));
            @SuppressLint("Range") String cod = cursor.getString(cursor.getColumnIndex("cod"));
            @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") Integer dataId = cursor.getInt(cursor.getColumnIndex("dataId"));

            paradaDB.setCelula(cel);
            paradaDB.setHoraI(horaI);
            paradaDB.setHoraF(horaF);
            paradaDB.setObs(obs);
            paradaDB.setCod(cod);
            paradaDB.setId(id);
            paradaDB.setDataId(dataId);
            listParadas.add(paradaDB);
        }
        cursor.close();
        return listParadas;
    }
}
