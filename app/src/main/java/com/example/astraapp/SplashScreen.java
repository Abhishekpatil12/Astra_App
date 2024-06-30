package com.example.astraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.astraapp.activities.MainActivity;
import com.example.astraapp.activities.StartActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        VideoView astraVideo = findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.splash;

        Uri uri = Uri.parse(videoPath.toString());

        astraVideo.setVideoURI(uri);

        astraVideo.start();

        astraVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(new Intent(SplashScreen.this, StartActivity.class));
                finish();
            }
        });
    }


}