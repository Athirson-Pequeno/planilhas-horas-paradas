package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.planilhahorasparadas.models.Cores;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.ArrayList;
import java.util.List;

public class ArtigosDAO implements IArtigosDAO{
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    public ArtigosDAO(Context context) {
        DBHelperAC dbHelperAC = new DBHelperAC(context);
        this.write = dbHelperAC.getWritableDatabase();
        this.read = dbHelperAC.getReadableDatabase();
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
        List<Especificacoes> listaCores = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelperAC.TABLE_ARTIGOS + " ORDER BY descricao;";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()){

            Cores novaCor = new Cores();
            @SuppressLint("Range") String corCod = cursor.getString(cursor.getColumnIndex("cod"));
            @SuppressLint("Range") String corDescricao = cursor.getString(cursor.getColumnIndex("descricao"));
            @SuppressLint("Range") Integer corID = cursor.getInt(cursor.getColumnIndex("id"));
            novaCor.setId(corID);
            novaCor.setDescricao(corDescricao);
            novaCor.setCod(corCod);
            listaCores.add(novaCor);

        }

        cursor.close();
        return listaCores;
    }
}
