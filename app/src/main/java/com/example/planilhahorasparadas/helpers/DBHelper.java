package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "DATABASE";
    public static String DATA_TABLE_NAME = "data_table";
    public static String PARADA_TABLE_NAME = "parada_table";
    public static int VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");

        String sql = "CREATE TABLE IF NOT EXISTS " + DATA_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT," +
                "UNIQUE (data));";

    String sql2 = "CREATE TABLE IF NOT EXISTS " + PARADA_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cel TEXT," +
                "horaI INTEGER," +
                "horaF INTEGER," +
                "obs TEXT," +
                "cod TEXT," +
                "dataId INTEGER," +
                "FOREIGN KEY (dataId) REFERENCES "+DATA_TABLE_NAME+" (id) ON DELETE CASCADE);";
        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO-DATABASE_DATA", "table " + DATA_TABLE_NAME + " created");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }
        try {
            sqLiteDatabase.execSQL(sql2);
            Log.i("INFO-DATABASE_DATA", "table " + PARADA_TABLE_NAME + " created");
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
