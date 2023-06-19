package com.example.trabajofinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class MyAdaptadorProductoMinimo extends ArrayAdapter<Producto> {
    private ProductoDataSource productoDataSource;
    public MyAdaptadorProductoMinimo (@NonNull Context context, List<Producto> objetos) {
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
                    R.layout.lista_productostockminimo,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView descripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
        TextView detalle = (TextView) convertView.findViewById(R.id.tvdetalle);

        // Lead actual.
        Producto p = getItem(position);

        // Setup.
        nombre.setText("Nombre: " + p.getNombre());
        descripcion.setText("Descripción: " +p.getDescripcion());
        detalle.setText("Talle: " +(!p.getTalle().equals("") ? p.getTalle() : "S/T")+" - Stock minimo: " + p.getStockMinimo() + " - Stock: " + p.getStock());

        productoDataSource=new ProductoDataSource(getContext());
        productoDataSource.open();

        return convertView;
    }
}
