package com.example.trabajofinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class MyAdaptadorProductos extends ArrayAdapter<Producto> {
    private ProductoDataSource productoDataSource;
    public MyAdaptadorProductos(@NonNull Context context, List<Producto> objetos) {
        super(context,0, objetos);
    }

    public void setNoexiste(TextView noexiste) {
        this.noexiste = noexiste;
    }

    private TextView noexiste;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.lista,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView descripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
        TextView talle = (TextView) convertView.findViewById(R.id.tvtalle);

        // Lead actual.
        Producto p = getItem(position);

        // Setup.
        nombre.setText("Nombre: " + p.getNombre());
        descripcion.setText("Descripción: " +p.getDescripcion());
        talle.setText("Talle: " +(!p.getTalle().equals("") ? p.getTalle() : "S/T"));

        productoDataSource=new ProductoDataSource(getContext());
        productoDataSource.open();

        Button botonEliminar=convertView.findViewById(R.id.btnEliminar);
        Button botonModificar=convertView.findViewById(R.id.btnModificar);

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productoDataSource.borrar(p);
                Toast.makeText(getContext(), "El producto " + p.getNombre() + " se ha eliminado exitosamente", Toast.LENGTH_LONG).show();
                Borrar(p);
            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modificar(p,v);
            }
        });

        return convertView;
    }

     private void Borrar (Producto producto){
        this.remove(producto);
        if(this.getCount() == 0){
            noexiste.setText(R.string.noexistencia);
        } else {
            noexiste.setText(R.string.lista_de_productos);
        }

     }

    public void Modificar(Producto p, View v) {
        Intent i=new Intent(getContext(),ProductoAltaModificacion.class);

        Bundle parametros=new Bundle();
        parametros.putInt("id", p.getId());
        parametros.putString("nombre", p.getNombre());
        parametros.putString("descripcion",p.getDescripcion());
        parametros.putString("talle",p.getTalle());
        parametros.putDouble("precio",p.getPrecio());
        parametros.putInt("stockMinimo",p.getStockMinimo());
        parametros.putInt("stock",p.getStock());
        i.putExtras(parametros);

        getContext().startActivity(i);
    }
}
