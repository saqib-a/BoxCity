package com.example.saqib.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

//credit to: https://www.youtube.com/watch?v=0NFwF7L-YA8
public class Profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView userEmail;
    private Button logoutButton;
    private Button guidesButton;
    private Button mapButton;
    private Button newsButton;
    private Button discussionButton;
    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null)
        {
            //profile
            finish();
            startActivity(new Intent(this, Login.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        userEmail = (TextView) findViewById(R.id.userEmail);
        userEmail.setText("Signed in as: " + user.getEmail());
        logoutButton = (Button) findViewById(R.id.logoutButton);
        guidesButton = (Button) findViewById(R.id.guidesButton);
        mapButton = (Button) findViewById(R.id.mapButton);
        newsButton = (Button) findViewById(R.id.newsButton);
        discussionButton = (Button) findViewById(R.id.discussion);
        chatButton = (Button) findViewById(R.id.privateMessage);

        mapButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        guidesButton.setOnClickListener(this);
        newsButton.setOnClickListener(this);
        discussionButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logoutButton)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }

        if (v == guidesButton)
        {
            finish();
            startActivity(new Intent(this, Guides.class));
        }

       /* if (v == mapButton)
        {
            finish();
            startActivity(new Intent(this, Maps.class));
        } */

        if (v == newsButton)
        {
            finish();
            startActivity(new Intent(this, News.class));
        }

        if (v == discussionButton)
        {
            finish();
            startActivity(new Intent(this, Discussion.class));
        }
    }
}
