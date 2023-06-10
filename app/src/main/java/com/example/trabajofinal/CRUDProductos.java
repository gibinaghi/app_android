package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

public class CRUDProductos extends ListActivity {

    ProductoDataSource productoDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudproductos);


        productoDataSource=new ProductoDataSource(this);
        productoDataSource.open();

        List<Producto> productos=productoDataSource.obtenerTodos();
        ArrayAdapter<Producto> adapter=new ArrayAdapter<Producto>(this,android.R.layout.simple_list_item_1,productos);
        setListAdapter(adapter);
    }

    public void Agregar(View v){
        Intent i=new Intent(this,ProductoAltaModificacion.class);
        startActivity(i);
    }
}