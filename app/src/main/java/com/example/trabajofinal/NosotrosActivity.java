package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

public class NosotrosActivity extends AppCompatActivity {
    private Auth0 account;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);

        account=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));


        //ToolBar
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Titulo);

        //Flecha volver atrás
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    //Crea el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_login){
            loginWithBrowser();
        }else if(item.getItemId()==R.id.item_nosotros){
            llamarNosotros();
        }
        return super.onOptionsItemSelected(item);
    }

    //Nosotros
    public void llamarNosotros(){
        Intent i = new Intent(this, NosotrosActivity.class);
        startActivity(i);
    }

    //Login
    public void loginWithBrowser(){
        Callback<Credentials, AuthenticationException> callback = new Callback<Credentials, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException exception) {
                //failed with an exception
            }

            @Override
            public void onSuccess(@Nullable Credentials credentials) {
                //succeeded!
                Intent i=new Intent(NosotrosActivity.this, CRUDProductos.class);
                startActivity(i);
            }
        };
        WebAuthProvider.login(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(this, callback);
    }
}