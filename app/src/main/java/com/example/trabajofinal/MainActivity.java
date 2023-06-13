package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String VIDEO = "mivideo";
    private VideoView videoView;
    private int posicion = 0;
    private static final String PLAYBACK_TIME = "play_time";
    /*



    private TextView mBufferingTextView;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoview);

        if (savedInstanceState != null) {
            posicion = savedInstanceState.getInt(PLAYBACK_TIME);
        }


        MediaController controlador = new MediaController(this);
        controlador.setMediaPlayer(videoView);

        videoView.setMediaController(controlador);
  /*






        mBufferingTextView = findViewById(R.id.buffering_textview);
        */
    }

    public void onClick(View v){
        Intent i=new Intent(this, CRUDProductos.class);
        startActivity(i);

    }

    // Video
    private Uri getMedia(String mediaName) {
        //if (URLUtil.isValidUrl(mediaName)) {
            // media name is an external URL
        //    return Uri.parse(mediaName);
      //  } else {
            // media name is a raw resource embedded in the app
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
      //  }
    }

    private void initializePlayer() {
       // mBufferingTextView.setVisibility(VideoView.VISIBLE);

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
                //mBufferingTextView.setVisibility(VideoView.INVISIBLE);
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