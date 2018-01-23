package com.github.saulocalixto.healthme.BancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthme.db";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE DadosUsuario (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Nome TEXT NOT NULL, " +
            "Sexo TEXT NOT NULL, " +
            "Idade TEXT NOT NULL, " +
            "Altura REAL NOT NULL, " +
            "Peso REAL NOT NULL);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}