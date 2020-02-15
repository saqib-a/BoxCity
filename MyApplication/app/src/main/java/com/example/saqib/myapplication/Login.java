package com.example.saqib.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private EditText enterEmail;
    private EditText enterPassword;
    private TextView registerHere;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            //profile
            //finish();

            Intent test = new Intent(Login.this, Profile.class);
            startActivity(test);
        }
        enterEmail = (EditText) findViewById(R.id.enterEmail);
        enterPassword = (EditText) findViewById(R.id.enterPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerHere = (TextView) findViewById(R.id.registerHere);

        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
        registerHere.setOnClickListener(this);
    }

    private void userLogin()
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
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();

            //stops execution
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        progressDialog.dismiss();
                        if (task.isSuccessful())
                        {
                            //profile activity
                            finish();
                            //startActivity(new Intent(getApplicationContext(), Profile.class));
                            Intent innn = new Intent(Login.this,Profile.class);
                            startActivity(innn);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v)
    {
        if (v == loginButton)
        {
            userLogin();
            Log.d("Karma: ", "");
        }
        if (v == registerHere)
        {
            finish();
            startActivity(new Intent(this, Register.class));
        }

    }
}
