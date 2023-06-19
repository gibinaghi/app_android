package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

public class MainActivity extends AppCompatActivity {

    private Auth0 account;

    private static final String VIDEO = "https://winaysa.com/coke/mivideo.mp4";
    private VideoView videoView;
    private int posicion = 0;
    private static final String PLAYBACK_TIME = "play_time";

    private TextView tvBuffering;


    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account=new Auth0(getString(R.string.com_auth0_client_id),getString(R.string.com_auth0_domain));

        //ToolBar
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Titulo);


        videoView = findViewById(R.id.videoview);
        tvBuffering = findViewById(R.id.buffering_textview);

        if (savedInstanceState != null) {
            posicion = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        MediaController controlador = new MediaController(this);
        controlador.setMediaPlayer(videoView);

        videoView.setMediaController(controlador);
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

    //Video
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        } else {
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
        }
    }

    private void initializePlayer() {
       tvBuffering.setVisibility(VideoView.VISIBLE);

        Uri videoUri = getMedia(VIDEO);
        videoView.setVideoURI(videoUri);

        if (posicion > 0) {
            videoView.seekTo(posicion);
        } else {
            // Saltar a 1 muestra el primer cuadro del video.
            videoView.seekTo(1);
        }
        videoView.start();

        videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override public void onPrepared(MediaPlayer mediaPlayer) {
                tvBuffering.setVisibility(VideoView.INVISIBLE);
                if (posicion > 0) {
                    videoView.seekTo(posicion);
                } else {
                    videoView.seekTo(1);
                }
                videoView.start();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override public void onCompletion(MediaPlayer mediaPlayer) {
                //Toast.makeText(MainActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
                videoView.seekTo(1);
                initializePlayer();
            }
        });
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
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
                Intent i=new Intent(MainActivity.this, CRUDProductos.class);
                startActivity(i);
            }
        };
        WebAuthProvider.login(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(this, callback);
    }

    //Logout
    public void logout(){
        Callback<Void, AuthenticationException> logoutCallback = new Callback<Void, AuthenticationException>() {
            @Override
            public void onFailure(@NonNull AuthenticationException e) {

            }

            @Override
            public void onSuccess(@Nullable Void payload) {
                Toast.makeText(MainActivity.this, "CHAU", Toast.LENGTH_SHORT).show();
            }
        };

        //Configure and launch the log out
        WebAuthProvider.logout(account)
                .withScheme(getString(R.string.com_auth0_schema))
                .start(MainActivity.this, logoutCallback);
    }

    //Nosotros
    public void llamarNosotros(){
        Intent i = new Intent(this, NosotrosActivity.class);
        startActivity(i);
    }
}