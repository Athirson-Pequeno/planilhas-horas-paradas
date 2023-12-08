package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBDataHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "DB_DATA";
    public static String TABLE_NAME = "DATA_table";
    public static int VERSION = 1;

    public DBDataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT)";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO-DATABASE_DATA", "table " + TABLE_NAME + " created");
        } catch (Exception e) {
            Log.i("INFO-DATABASE_DATA", "error create table: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
