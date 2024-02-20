package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DATABASE";
    public static final String DATA_TABLE_NAME = "data_table";
    public static final String PARADA_TABLE_NAME = "parada_table";
    public static final String PRODUCAO_TABLE_NAME = "producao_table";
    public static final String CORES_TABLE_NAME = "cores_table";
    public static final String ARTIGOS_TABLE_NAME = "artigos_table";
    public static final String COD_PARADAS_TABLE_NAME = "cod_paradas_table";
    public static final int VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");

        String sqlTabelaDatas = "CREATE TABLE IF NOT EXISTS " + DATA_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data TEXT, " +
                "UNIQUE (data));";

        String sqlTabelaParadas = "CREATE TABLE IF NOT EXISTS " + PARADA_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cel TEXT, " +
                "horaI INTEGER, " +
                "horaF INTEGER ," +
                "obs TEXT, " +
                "cod TEXT, " +
                "dataId INTEGER, " +
                "horario TEXT, " +
                "salvo BOOLEAN NOT NULL CHECK (salvo IN (0, 1)), " +
                "FOREIGN KEY (dataId) REFERENCES " + DATA_TABLE_NAME + " (id) ON DELETE CASCADE);";

        String sqlTabelaProducao = "CREATE TABLE IF NOT EXISTS " + PRODUCAO_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "celula TEXT, " +
                "codigoCor TEXT, " +
                "codigoArtigo TEXT, " +
                "descricaoCor TEXT, " +
                "descricaoArtigo TEXT, " +
                "tamanho TEXT, " +
                "quantidadeProduzida INTEGER, " +
                "idCor INTEGER, " +
                "idArtigo INTEGER, " +
                "dataId INTEGER, " +
                "horario TEXT, " +
                "salvo BOOLEAN NOT NULL CHECK (salvo IN (0, 1)), " +
                "FOREIGN KEY (dataId) REFERENCES " + DATA_TABLE_NAME + " (id) ON DELETE CASCADE);";


        String sqlTabelaCores = "CREATE TABLE IF NOT EXISTS " + CORES_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cod TEXT," +
                "descricao TEXT);";

        String sqlTabelaArtigos = "CREATE TABLE IF NOT EXISTS " + ARTIGOS_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cod TEXT," +
                "descricao TEXT);";

        String sqlTabelasCodArtifos = "CREATE TABLE IF NOT EXISTS " + COD_PARADAS_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cod TEXT," +
                "descricao TEXT);";

        try {
            sqLiteDatabase.execSQL(sqlTabelaDatas);
            Log.i("INFO-DATABASE_DATA", "Tabela " + DATA_TABLE_NAME + " criada");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

        try {
            sqLiteDatabase.execSQL(sqlTabelaParadas);
            Log.i("INFO-DATABASE_DATA", "Tabela " + PARADA_TABLE_NAME + " criada");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

        try {
            sqLiteDatabase.execSQL(sqlTabelaProducao);
            Log.i("INFO-DATABASE_DATA", "Tabela " + PRODUCAO_TABLE_NAME + " criada");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

        try {
            sqLiteDatabase.execSQL(sqlTabelaCores);
            Log.i("INFO-DATABASE_DATA", "Tabela " + CORES_TABLE_NAME + " criada");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

        try {
            sqLiteDatabase.execSQL(sqlTabelaArtigos);
            Log.i("INFO-DATABASE_DATA", "Tabela " + PARADA_TABLE_NAME + " criada");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

        try {
            sqLiteDatabase.execSQL(sqlTabelasCodArtifos);
            Log.i("INFO-DATABASE_DATA", "Tabela " + COD_PARADAS_TABLE_NAME + " criada");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }
}
