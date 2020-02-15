package com.example.saqib.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Guides extends AppCompatActivity implements View.OnClickListener {

    private Button stanceButton;
    private Button theJabButton;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);

        homeButton = (Button) findViewById(R.id.homeButton);
        stanceButton = (Button) findViewById(R.id.stanceButton);
        theJabButton = (Button) findViewById(R.id.theJabButton);

        homeButton.setOnClickListener(this);
        stanceButton.setOnClickListener(this);
        theJabButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
     if (v == homeButton)
     {
         finish();
         startActivity(new Intent(this, Profile.class));
     }
     if (v == stanceButton)
     {
         finish();
         startActivity(new Intent(this, Stance.class));
     }
     if (v == theJabButton)
     {
         finish();
         startActivity(new Intent(this, Jab.class));
     }
    }
}
