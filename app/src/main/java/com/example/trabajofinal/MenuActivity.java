package com.example.trabajofinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

public class MenuActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Auth0 account;
    private AuthenticationAPIClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        account=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));

        //ToolBar
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Titulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_login){
            loginWithBrowser();
        }else if(item.getItemId()==R.id.item_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    //public void loginWithBrowser(View v){
    public void loginWithBrowser(){
        Callback<Credentials, AuthenticationException> callback = new Callback<Credentials, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException exception) {
                //failed with an exception
            }

            @Override
            public void onSuccess(@Nullable Credentials credentials) {
                //succeeded!
                Intent i=new Intent(MenuActivity.this, CRUDProductos.class);
                startActivity(i);
            }
        };
        WebAuthProvider.login(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(this, callback);
    }

    //public void logout(View v){
    public void logout(){
        Callback<Void, AuthenticationException> logoutCallback = new Callback<Void, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException e) {

            }

            @Override
            public void onSuccess(@Nullable Void payload) {
                Toast.makeText(MenuActivity.this, "CHAU", Toast.LENGTH_SHORT).show();
            }
        };

        //Configure and launch the log out
        WebAuthProvider.logout(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(MenuActivity.this, logoutCallback);
    }
}
