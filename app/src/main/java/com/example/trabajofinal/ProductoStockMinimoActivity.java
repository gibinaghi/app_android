package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;

import java.util.List;

public class ProductoStockMinimoActivity extends AppCompatActivity {
    ProductoDataSource productoDataSource;

    private Auth0 account;

    private Toolbar toolbar;
    private ListView lv;

    MyAdaptadorProductoMinimo adaptador;

    TextView noexiste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_stock_minimo);

        lv=findViewById(R.id.listView);

        //ToolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Titulo);

        account=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));

        productoDataSource=new ProductoDataSource(this);
        productoDataSource.open();
        noexiste=findViewById(R.id.noexiste);

        // Flecha volver atrás
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Listar();
    }

    public void Agregar(){
        Intent i=new Intent(this,ProductoAltaModificacion.class);
        startActivity(i);
    }

    public void Listar() {
        List<Producto> productos = productoDataSource.obtenerProductosStockMinimo();

        adaptador = new MyAdaptadorProductoMinimo(this, productos);
        adaptador.setNoexiste(noexiste);
        lv.setAdapter(adaptador);

        if (productos.size() == 0) {
            noexiste.setText(R.string.noexistencia);
        } else {
            noexiste.setText(R.string.lista_de_productos_faltantes);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logueado, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.item_logout){
            logout();
        }else if(item.getItemId()==R.id.item_agregar){
            Agregar();
        }
        else if(item.getItemId()==R.id.item_productosFaltantes) {
          ListarProductoStockMinimo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ListarProductoStockMinimo(){
        Intent i=new Intent(this, ProductoStockMinimoActivity.class);
        startActivity(i);
    }

    //Logout
    public void logout(){
        Callback<Void, AuthenticationException> logoutCallback = new Callback<Void, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException e) {

            }

            @Override
            public void onSuccess(@Nullable Void payload) {
                Intent i=new Intent(ProductoStockMinimoActivity.this,MainActivity.class);
                startActivity(i);
            }
        };

        //Configure and launch the log out
        WebAuthProvider.logout(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(ProductoStockMinimoActivity.this, logoutCallback);
    }
}