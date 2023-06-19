package com.example.trabajofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

public class ProductoDataSource {
    private SQLiteDatabase database;
    private SqlHelper helper;

    private String[] ColumnasSelect={SqlHelper.TP_ID,SqlHelper.TP_NOMBRE, SqlHelper.TP_STOCK};
    private String[] ColumnasSelectObtenerTodos={SqlHelper.TP_ID,SqlHelper.TP_NOMBRE,SqlHelper.TP_DESCRIPCION, SqlHelper.TP_TALLE,SqlHelper.TP_PRECIO,SqlHelper.TP_STOCK,SqlHelper.TP_STOCKMINIMO};


    public ProductoDataSource(Context context) {
        super();
        helper=new SqlHelper(context);
    }
    public void open() throws SQLiteException {
        database=helper.getWritableDatabase();
    }
    public void close() throws SQLiteException{
        database.close();
    }

    public void agregar(Producto pProducto){
        ContentValues contentValues=new ContentValues();
        contentValues.put(SqlHelper.TP_NOMBRE, pProducto.getNombre());
        contentValues.put(SqlHelper.TP_DESCRIPCION, pProducto.getDescripcion());
        contentValues.put(SqlHelper.TP_TALLE, pProducto.getTalle());
        contentValues.put(SqlHelper.TP_PRECIO, pProducto.getPrecio());
        contentValues.put(SqlHelper.TP_STOCKMINIMO, pProducto.getStockMinimo());
        contentValues.put(SqlHelper.TP_STOCK, pProducto.getStock());

        Long Id=database.insert(SqlHelper.TABLAPRODUCTOS,null,contentValues);

    }
    public void borrar(Producto p){
        long id=p.getId();
        database.delete(SqlHelper.TABLAPRODUCTOS,SqlHelper.TP_ID+"="+id,null);
    }
    public List<Producto> obtenerTodos(){
        List<Producto> productos=new ArrayList<Producto>();
        Cursor cursor=database.query(SqlHelper.TABLAPRODUCTOS,ColumnasSelectObtenerTodos,null,null,null,null,SqlHelper.TP_NOMBRE);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Producto p =new Producto();
            p.setId(cursor.getInt(0));
            p.setNombre(cursor.getString(1));
            p.setDescripcion(cursor.getString(2));
            p.setTalle(cursor.getString(3));
            p.setPrecio(cursor.getDouble(4));
            p.setStock(cursor.getInt(5));
            p.setStockMinimo(cursor.getInt(6));
            productos.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return productos;
    }
    public List<Producto> obtenerProductosStockMinimo(){
        List<Producto> productos=new ArrayList<Producto>();
        Cursor cursor=database.query(SqlHelper.TABLAPRODUCTOS,ColumnasSelectObtenerTodos,SqlHelper.TP_STOCKMINIMO+">"+SqlHelper.TP_STOCK,null,null,null,SqlHelper.TP_NOMBRE);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Producto p =new Producto();
            p.setId(cursor.getInt(0));
            p.setNombre(cursor.getString(1));
            p.setDescripcion(cursor.getString(2));
            p.setTalle(cursor.getString(3));
            p.setPrecio(cursor.getDouble(4));
            p.setStock(cursor.getInt(5));
            p.setStockMinimo(cursor.getInt(6));
            productos.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return productos;
    }


    public void modificacion(Producto pProducto){
        ContentValues contentValues=new ContentValues();
        contentValues.put(SqlHelper.TP_NOMBRE, pProducto.getNombre());
        contentValues.put(SqlHelper.TP_DESCRIPCION, pProducto.getDescripcion());
        contentValues.put(SqlHelper.TP_TALLE, pProducto.getTalle());
        contentValues.put(SqlHelper.TP_PRECIO, pProducto.getPrecio());
        contentValues.put(SqlHelper.TP_STOCKMINIMO, pProducto.getStockMinimo());
        contentValues.put(SqlHelper.TP_STOCK, pProducto.getStock());

        int Id=database.update(SqlHelper.TABLAPRODUCTOS,contentValues,SqlHelper.TP_ID+"="+pProducto.getId(),null);

    }
}
