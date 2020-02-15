package com.example.saqib.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

//credit to: https://www.youtube.com/watch?v=W4hTJybfU7s
public class Jab extends YouTubeBaseActivity implements View.OnClickListener {

    private static final String TAG = "Jab";

    private YouTubePlayerView jabVideo;
    private YouTubePlayer.OnInitializedListener jabInitializedListener;
    private Button playJabButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jab);

        jabVideo = (YouTubePlayerView) findViewById(R.id.jabVideo);
        playJabButton = (Button) findViewById(R.id.playJabButton);
        backButton = (Button) findViewById(R.id.backButton);
        jabInitializedListener = new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {
                youTubePlayer.loadVideo("WLqt5VnrSkk");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        playJabButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                jabVideo.initialize(YouTubeVideoImp.getApiKey(),jabInitializedListener);
            }
        });
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if (v == backButton)
        {
            finish();
            startActivity(new Intent(this, Guides.class));
        }
    }
}