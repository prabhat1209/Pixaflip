package com.example.pixaflip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final VideoView video = (VideoView) findViewById(R.id.videoView);
        video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.pixa_flip);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);

        video.start();



        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {


            public void onCompletion(MediaPlayer mp) {
                Intent in = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });

    }
}
