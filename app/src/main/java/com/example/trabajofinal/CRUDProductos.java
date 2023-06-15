package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;

import java.util.List;

public class CRUDProductos extends AppCompatActivity {

    ProductoDataSource productoDataSource;

    private Auth0 account;

    private Toolbar toolbar;
    private ListView lv;

    MyAdaptadorProductos adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudproductos);

        lv=findViewById(R.id.listView);


        //ToolBar
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Titulo);

        account=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));

        productoDataSource=new ProductoDataSource(this);
        productoDataSource.open();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //List<Producto> productos=productoDataSource.obtenerTodos();
        //ArrayAdapter<Producto> adapter=new ArrayAdapter<Producto>(this,android.R.layout.simple_list_item_1,productos);
        //lv.setListAdapter(adapter);
        //lv.setAdapter(adapter);
        Listar();
    }

    public void Agregar(){
        Intent i=new Intent(this,ProductoAltaModificacion.class);
        startActivity(i);
    }

    public void Listar(){
        List<Producto> productos=productoDataSource.obtenerTodos();
        //ArrayAdapter<Producto> adapter=new ArrayAdapter<Producto>(this,android.R.layout.simple_list_item_1,productos);
        //setListAdapter(adapter);
        adaptador=new MyAdaptadorProductos(this,productos);
        lv.setAdapter(adaptador);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logueado, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_login){

        }else if(item.getItemId()==R.id.item_logout){
            logout();
        }else if(item.getItemId()==R.id.item_agregar){
            Agregar();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        Callback<Void, AuthenticationException> logoutCallback = new Callback<Void, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException e) {

            }

            @Override
            public void onSuccess(@Nullable Void payload) {
                Intent i=new Intent(CRUDProductos.this,MainActivity.class);
                startActivity(i);
            }
        };

        //Configure and launch the log out
        WebAuthProvider.logout(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(CRUDProductos.this, logoutCallback);
    }
}