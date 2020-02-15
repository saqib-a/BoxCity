 package com.example.saqib.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//credit to: https://www.youtube.com/watch?v=0NFwF7L-YA8
 public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button registrationButton;
    private EditText enterEmail;
    private EditText enterPassword;
    private TextView alreadyRegistered;
    private TextView pleaseSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            //profile
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        progressDialog = new ProgressDialog(this);

        registrationButton = (Button) findViewById(R.id.registrationButton);

        enterEmail = (EditText) findViewById(R.id.enterEmail);

        enterPassword = (EditText) findViewById(R.id.enterPassword);

        alreadyRegistered = (TextView) findViewById(R.id.alreadyRegistered);

        pleaseSignUp = (TextView) findViewById(R.id.pleaseSignUp);

        registrationButton.setOnClickListener(this);
        alreadyRegistered.setOnClickListener(this);


    }

    private void registerUser()
    {
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();

        //if the email is empty
        if (TextUtils.isEmpty(email))
        {
            //stops execution
            Toast.makeText(this,"Enter Email Address", Toast.LENGTH_SHORT).show();

            return;
        }

        //if the password is empty
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();

            //stops execution
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        //registration and login successful
                        {
                            Toast.makeText(Register.this,"Registered!", Toast.LENGTH_SHORT).show();
                            //profile
                            finish();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this,"Please try again", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

     @Override
     public void onClick(View v) {
        if(v == registrationButton)
        {
            registerUser();
        }

        //leads to login page
        if(v == alreadyRegistered)
        {
            startActivity(new Intent(this, Login.class));
        }
     }
 }
