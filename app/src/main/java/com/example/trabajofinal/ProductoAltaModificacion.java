package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProductoAltaModificacion extends AppCompatActivity {

    ProductoDataSource productoDataSource;
    EditText txtNombre,txtDescripcion,txtTalle;
    EditText txtPrecio, txtStock, txtStockMinimo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_alta_modificacion);

        txtNombre=findViewById(R.id.txtNombre);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtTalle=findViewById(R.id.txtTalle);
        txtPrecio=findViewById(R.id.txtPrecio);
        txtStock=findViewById(R.id.txtStock);
        txtStockMinimo=findViewById(R.id.txtStockMinimo);

        productoDataSource=new ProductoDataSource(this);
        productoDataSource.open();
    }

    public void Agregar(View v){
        Producto prodNuevo=new Producto();
        prodNuevo.setNombre(txtNombre.getText().toString());
        prodNuevo.setDescripcion(txtDescripcion.getText().toString());
        prodNuevo.setTalle(txtTalle.getText().toString());
        prodNuevo.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
        prodNuevo.setStock(Integer.parseInt(txtStock.getText().toString()));
        prodNuevo.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().toString()));
       // Toast.makeText(this,prodNuevo.toString(), Toast.LENGTH_LONG).show();
        //Log.w("aa",prodNuevo.toString());
        productoDataSource.agregar(prodNuevo);

    }
}