package com.example.planilhahorasparadas.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.planilhahorasparadas.models.Paradas;
import com.example.planilhahorasparadas.models.Producao;

import java.util.ArrayList;
import java.util.List;

public class ProducaoDAO implements IProducaoDAO{
    private SQLiteDatabase write;
    private SQLiteDatabase read;
    public ProducaoDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean save(Producao producao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("celula", producao.getCelula());
        contentValues.put("codigoCor", producao.getCodigoCor());
        contentValues.put("codigoArtigo", producao.getCodigoArtigo());
        contentValues.put("descricaoCor", producao.getDescricaoCor());
        contentValues.put("descricaoArtigo", producao.getDescricaoArtigo());
        contentValues.put("quantidadeProduzida", producao.getQuantidadeProduzida());
        contentValues.put("dataId", producao.getDataId());
        contentValues.put("horario", producao.getHorario());
        contentValues.put("idCor", producao.getIdCor());
        contentValues.put("idArtigo", producao.getIdArtigo());
        contentValues.put("salvo", 0);


        try {
            write.insert(DBHelper.PRODUCAO_TABLE_NAME, null, contentValues);
            Log.i("INFO", "Producao Save");
        } catch (Exception e) {
            Log.i("INFO", "Producao not saved: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Producao producao) {
        return false;
    }

    @Override
    public boolean delete(Producao producao) {
        try {

            String[] args = {producao.getId().toString()};
            write.delete(DBHelper.PRODUCAO_TABLE_NAME, "id=?", args);
            Log.i("INFO", "Producao deletada");

        } catch (Exception e) {
            Log.i("INFO", "Erro ao deletar" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Producao> getAll() {
        String sql = "SELECT * FROM " + DBHelper.PRODUCAO_TABLE_NAME + " ORDER BY id DESC;";
        return executarQuery(sql);
    }

    @Override
    public List<Producao> buscarPorDataCelHorario(Integer dataID, String celula, String horarioSpinner) {
        String sql = "SELECT * FROM " + DBHelper.PRODUCAO_TABLE_NAME + " WHERE dataId= " + dataID + " AND celula= " + "'" + celula + "'" + " AND horario= " + "'" + horarioSpinner + "'" + " ORDER BY id DESC;";
        return executarQuery(sql);
    }

    @Override
    public List<Producao> buscarPorDataCel(Integer dataID, String celula) {
        String sql = "SELECT * FROM " + DBHelper.PRODUCAO_TABLE_NAME + " WHERE dataId= " + dataID + " AND celula= " + "'" + celula + "'" + " ORDER BY horario DESC, id DESC;";
        return executarQuery(sql);
    }

    @Override
    public List<Producao> buscarNaoSalvos() {
        String sql = "SELECT * FROM " + DBHelper.PRODUCAO_TABLE_NAME + " WHERE salvo= 0;";
        return executarQuery(sql);
    }

    public void atualizarNaoSalvos(Producao producao){
        ContentValues cv =  new ContentValues();
        cv.put("salvo",1);
        try {
            String[] args = {producao.getId().toString()};
            write.update(DBHelper.PRODUCAO_TABLE_NAME,cv,"id=?",args);
            Log.i("INFO", "Parada atualizada");
        }catch (Exception e){
            Log.i("INFO", "Erro ao atualizar parada: "+ e.getMessage());
        }
    }

    private List<Producao> executarQuery(String query) {
        List<Producao> listProducao = new ArrayList<>();
        Cursor cursor =  read.rawQuery(query, null);
        while (cursor.moveToNext()) {

            Producao producaoDB = new Producao();

            @SuppressLint("Range") Integer producaoID = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String celula = cursor.getString(cursor.getColumnIndex("celula"));
            @SuppressLint("Range") String codigoCor = cursor.getString(cursor.getColumnIndex("codigoCor"));
            @SuppressLint("Range") String codigoArtigo = cursor.getString(cursor.getColumnIndex("codigoArtigo"));
            @SuppressLint("Range") String descricaoCor = cursor.getString(cursor.getColumnIndex("descricaoCor"));
            @SuppressLint("Range") String descricaoArtigo = cursor.getString(cursor.getColumnIndex("descricaoArtigo"));
            @SuppressLint("Range") Integer quantidadeProduzida = cursor.getInt(cursor.getColumnIndex("quantidadeProduzida"));
            @SuppressLint("Range") Integer dataId = cursor.getInt(cursor.getColumnIndex("dataId"));
            @SuppressLint("Range") Integer idCor = cursor.getInt(cursor.getColumnIndex("idCor"));
            @SuppressLint("Range") Integer idArtigo = cursor.getInt(cursor.getColumnIndex("idArtigo"));
            @SuppressLint("Range") String horario = cursor.getString(cursor.getColumnIndex("horario"));
            @SuppressLint("Range") Integer salvo = cursor.getInt(cursor.getColumnIndex("salvo"));

            producaoDB.setId(producaoID);
            producaoDB.setCelula(celula);
            producaoDB.setCodigoCor(codigoCor);
            producaoDB.setCodigoArtigo(codigoArtigo);
            producaoDB.setDescricaoCor(descricaoCor);
            producaoDB.setDescricaoArtigo(descricaoArtigo);
            producaoDB.setQuantidadeProduzida(quantidadeProduzida);
            producaoDB.setDataId(dataId);
            producaoDB.setHorario(horario);
            producaoDB.setIdArtigo(idArtigo);
            producaoDB.setIdCor(idCor);
            producaoDB.setSalvo(salvo);

            listProducao.add(producaoDB);
        }
        cursor.close();
        return listProducao;
    }


}
