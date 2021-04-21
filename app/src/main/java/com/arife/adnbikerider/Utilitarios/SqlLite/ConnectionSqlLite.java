package com.arife.adnbikerider.Utilitarios.SqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class ConnectionSqlLite extends SQLiteOpenHelper {

    public ConnectionSqlLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(Constantes.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void ExectSql(String SQL){

        db.execSQL(SQL);

    }

}
