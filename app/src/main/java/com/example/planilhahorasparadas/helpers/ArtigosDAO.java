package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.planilhahorasparadas.models.Artigos;
import com.example.planilhahorasparadas.models.Cores;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.ArrayList;
import java.util.List;

public class ArtigosDAO implements IArtigosDAO{
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    public ArtigosDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }
    @Override
    public boolean save(Paradas parada) {
        return false;
    }

    @Override
    public boolean update(Paradas parada) {
        return false;
    }

    @Override
    public boolean delete(Paradas parada) {
        return false;
    }

    @Override
    public List<Especificacoes> getAll() {
        String sql = "SELECT * FROM " + DBHelper.ARTIGOS_TABLE_NAME + " ORDER BY descricao;";

        return executarQuery(sql);
    }

    @Override
    public List<Especificacoes> getById(Integer id) {
        String sql = "SELECT * FROM " + DBHelper.ARTIGOS_TABLE_NAME + " WHERE id = " + id + " ORDER BY descricao;";
        return executarQuery(sql);
    }

    @Override
    public List<Especificacoes> getByCod(String codigo) {
        return null;
    }

    private List<Especificacoes> executarQuery(String query) {
        List<Especificacoes> listaArtigos = new ArrayList<>();

        Cursor cursor = read.rawQuery(query, null);

        while (cursor.moveToNext()) {

            Artigos artigos = new Artigos();
            @SuppressLint("Range") String corCod = cursor.getString(cursor.getColumnIndex("cod"));
            @SuppressLint("Range") String corDescricao = cursor.getString(cursor.getColumnIndex("descricao"));
            @SuppressLint("Range") Integer corID = cursor.getInt(cursor.getColumnIndex("id"));
            artigos.setId(corID);
            artigos.setDescricao(corDescricao);
            artigos.setCod(corCod);
            listaArtigos.add(artigos);

        }

        cursor.close();
        return listaArtigos;
    }
}
