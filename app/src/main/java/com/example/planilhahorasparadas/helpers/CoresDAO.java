package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.planilhahorasparadas.models.Cores;
import com.example.planilhahorasparadas.models.Especificacoes;

import java.util.ArrayList;
import java.util.List;

public class CoresDAO implements ICoresDAO{

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public CoresDAO(Context context) {
        DBHelperAC dbHelperAC = new DBHelperAC(context);
        this.write = dbHelperAC.getWritableDatabase();
        this.read = dbHelperAC.getReadableDatabase();
    }

    @Override
    public boolean save(Cores cores) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod", cores.getCod());
        contentValues.put("cod", cores.getDescricao());
        try {
            write.insert(DBHelper.DATA_TABLE_NAME, null, contentValues);
            Log.i("INFO", "Sucesso ao salvar cor");
        }catch (Exception e){
            Log.i("INFO", "Erro ao salvar cor: "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Cores cores) {
        return false;
    }

    @Override
    public boolean delete(Cores cores) {
        return false;
    }

    @Override
    public List<Especificacoes> getAll() {
        List<Especificacoes> listaCores = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelperAC.TABLE_CORES + " ORDER BY descricao;";
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
