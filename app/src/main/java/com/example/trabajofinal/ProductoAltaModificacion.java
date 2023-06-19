package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;

public class ProductoAltaModificacion extends AppCompatActivity {

    ProductoDataSource productoDataSource;
    EditText txtNombre,txtDescripcion,txtTalle;
    EditText txtPrecio, txtStock, txtStockMinimo;
    private Auth0 account;
    private Toolbar toolbar;
    Button btnBoton;
    Producto prod;

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

        btnBoton=findViewById(R.id.btnAgregar);
        btnBoton.setText("Agregar");

        productoDataSource=new ProductoDataSource(this);
        productoDataSource.open();

        //ToolBar
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Titulo);

        //Auth0
        account=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));

        //Flecha volver atrás
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();

        Bundle b=intent.getExtras();
        if(b!=null){
            prod=new Producto();
            prod.setId(intent.getExtras().getInt("id"));
            prod.setNombre(intent.getExtras().getString("nombre"));
            prod.setDescripcion(intent.getExtras().getString("descripcion"));
            prod.setTalle(intent.getExtras().getString("talle"));
            prod.setPrecio(intent.getExtras().getDouble("precio"));
            prod.setStock(intent.getExtras().getInt("stock"));
            prod.setStockMinimo(intent.getExtras().getInt("stockMinimo"));

            btnBoton.setText("Modificar");

            txtNombre.setText(prod.getNombre());
            txtDescripcion.setText(prod.getDescripcion());
            txtTalle.setText(prod.getTalle());
            txtPrecio.setText(prod.getPrecio()+"");
            txtStockMinimo.setText(prod.getStockMinimo()+"");
            txtStock.setText(prod.getStock()+"");
        }
    }

    public void AgregarModificar(View v){
        Producto prodNuevo=new Producto();

        if(validation()) {
            prodNuevo.setNombre(txtNombre.getText().toString());
            prodNuevo.setDescripcion(txtDescripcion.getText().toString());
            prodNuevo.setTalle(txtTalle.getText().toString());
            prodNuevo.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
            prodNuevo.setStock(Integer.parseInt(txtStock.getText().toString()));
            prodNuevo.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().toString()));

            if(btnBoton.getText().equals("Agregar")){
                productoDataSource.agregar(prodNuevo);
                Toast.makeText(this, "El producto " + prodNuevo.getNombre() + " se ha agregado exitosamente", Toast.LENGTH_LONG).show();
            }else if(btnBoton.getText().equals("Modificar")) {
                prodNuevo.setId(prod.getId());
                productoDataSource.modificacion(prodNuevo);
                Toast.makeText(this, "El producto se ha modificado exitosamente", Toast.LENGTH_LONG).show();
            }

            Intent i = new Intent(this, CRUDProductos.class);
            startActivity(i);
        }
    }


    //Logout
    public void logout(){
        Callback<Void, AuthenticationException> logoutCallback = new Callback<Void, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException e) {

            }

            @Override
            public void onSuccess(@Nullable Void payload) {
                Intent i=new Intent(ProductoAltaModificacion.this,MainActivity.class);
                startActivity(i);
            }
        };

        //Configure and launch the log out
        WebAuthProvider.logout(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(ProductoAltaModificacion.this, logoutCallback);
    }

    //Validation
    private boolean validation(){
        if(txtNombre.getText().toString().isEmpty()){
            Toast.makeText(this, "El nombre no puede estar vacio", Toast.LENGTH_LONG).show();
            return false;
        }
        if(txtDescripcion.getText().toString().isEmpty()){
            Toast.makeText(this, "La descripción no puede estar vacia", Toast.LENGTH_LONG).show();
            return false;
        }
        if(txtPrecio.getText().toString().isEmpty()){
            Toast.makeText(this, "El precio no puede estar vacio", Toast.LENGTH_LONG).show();
            return false;
        }
        if(txtStockMinimo.getText().toString().isEmpty()){
            Toast.makeText(this, "El stock mínimo no puede estar vacio", Toast.LENGTH_LONG).show();
            return false;
        }
        if(txtStock.getText().toString().isEmpty()){
            Toast.makeText(this, "El stock no puede estar vacio", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}