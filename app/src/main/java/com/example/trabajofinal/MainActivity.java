package com.example.trabajofinal;

import androidx.annotation.NonNull;
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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
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
    
    public void onClick(View v){
        Intent i=new Intent(this, CRUDProductos.class);
        startActivity(i);

    }

    // Video
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
}