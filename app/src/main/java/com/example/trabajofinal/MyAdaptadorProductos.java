package com.example.trabajofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class MyAdaptadorProductos extends ArrayAdapter<Producto> {

    public MyAdaptadorProductos(@NonNull Context context, List<Producto> objetos) {
        super(context,0, objetos);
    }

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
        //ImageView avatar = (ImageView) convertView.findViewById(R.id.ivImagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView descripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);
        TextView talle = (TextView) convertView.findViewById(R.id.tvtalle);


        // Lead actual.
        Producto p = getItem(position);

        // Setup.
        //Glide.with(getContext()).load(em.getImagen()).into(avatar);
        //   avatar.setImageBitmap();
        nombre.setText("Nombre: " + p.getNombre());
        descripcion.setText("Descripción:" +p.getDescripcion());
        talle.setText("Talle: " +p.getTalle());

        return convertView;
    }
}
