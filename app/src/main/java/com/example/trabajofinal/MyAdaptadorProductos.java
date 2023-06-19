package com.example.trabajofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
        productoDataSource=new ProductoDataSource(getContext());
        productoDataSource.open();
        Button botonEliminar=convertView.findViewById(R.id.btnEliminar);
        ListView lvListar=convertView.findViewById(R.id.listView);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productoDataSource.borrar(p);
                Toast.makeText(getContext(), "El producto " + p.getNombre() + " se ha eliminado exitosamente", Toast.LENGTH_LONG).show();
                Borrar(p);
            }

        });
        return convertView;
    }

     private void Borrar (Producto producto){
        this.remove(producto);
        if(this.getCount() == 0){
             noexiste.setVisibility(View.VISIBLE);
        }
        else {
             noexiste.setVisibility(View.GONE);
             }


     }
}
