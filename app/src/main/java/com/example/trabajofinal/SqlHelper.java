package com.example.trabajofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlHelper extends SQLiteOpenHelper {

    public static final String BASEDEDATOS="TiendaRopa";
    public static final int BASEDEDATOS_VERSION=1;

    public static final String TABLAPRODUCTOS="Productos";
    //TP=TablaProductos
    public static final String TP_ID="Id";
    public static final String TP_NOMBRE="Nombre";
    public static final String TP_DESCRIPCION="Descripcion";
    public static final String TP_TALLE="Talle";
    public static final String TP_PRECIO="Precio";
    public static final String TP_STOCKMINIMO="StockMinimo";
    public static final String TP_STOCK="Stock";

    public SqlHelper(@Nullable Context context) {
        super(context, BASEDEDATOS, null, BASEDEDATOS_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLAPRODUCTOS + " ("+ TP_ID + " integer primary key autoincrement, "
                + TP_NOMBRE + " text not null, "
                + TP_DESCRIPCION + " text not null, "
                + TP_TALLE + " text, "
                + TP_PRECIO + " double not null, "
                + TP_STOCKMINIMO + " integer not null, "
                + TP_STOCK + " integer not null" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLAPRODUCTOS);
        onCreate(db);
    }
}
