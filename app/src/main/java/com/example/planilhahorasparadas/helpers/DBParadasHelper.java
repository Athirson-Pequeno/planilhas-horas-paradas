package com.example.planilhahorasparadas.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBParadasHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "DB_PARADAS";
    public static String TABLE_NAME = "paradas_table";
    public static int VERSION = 1;


    public DBParadasHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cel TEXT," +
                "horaI INTEGER," +
                "horaF INTEGER," +
                "obs TEXT," +
                "cod TEXT," +
                "data TEXT)";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO-DATABASE_PARADAS","table created");
        }catch (Exception e){
            Log.i("INFO-DATABASE_PARADAS","error create table: "+e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
