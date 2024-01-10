package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperAC extends SQLiteOpenHelper {

    public static String DB_NAME = "DATABASEAC";
    public static String TABLE_CORES = "cores";
    public static String TABLE_ARTIGOS = "artigos";
    public static int VERSION = 1;

    public DBHelperAC(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CORES +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cod TEXT," +
                "descricao TEXT);";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTIGOS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cod TEXT," +
                "descricao TEXT);";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
