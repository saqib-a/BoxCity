package com.example.saqib.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

//credit to: https://www.youtube.com/watch?v=W4hTJybfU7s
public class Stance extends YouTubeBaseActivity implements View.OnClickListener {

    private static final String TAG = "Stance";

    private YouTubePlayerView stanceVideo;
    private YouTubePlayer.OnInitializedListener stanceInitializedListener;
    private Button playStanceButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stance);

        stanceVideo = (YouTubePlayerView) findViewById(R.id.stanceVideo);
        playStanceButton = (Button) findViewById(R.id.playStanceButton);
        backButton = (Button) findViewById(R.id.backButton);
        stanceInitializedListener = new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {
                youTubePlayer.loadVideo("DFTa81YQRmw");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        playStanceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                stanceVideo.initialize(YouTubeVideoImp.getApiKey(),stanceInitializedListener);
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
