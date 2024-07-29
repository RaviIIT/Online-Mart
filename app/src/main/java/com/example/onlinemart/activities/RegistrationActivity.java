package com.example.onlinemart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinemart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    //for input of name etc.
    EditText name, email, password;

    //for authorisation
    private FirebaseAuth auth;

    //for storing some small infos in local storage using key value pairs
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the layout using activity_registration xml file
        setContentView(R.layout.activity_registration);
        
        // for authorisation
        auth = FirebaseAuth.getInstance();

        // checks for an active user on this device primarily with tokens and sessions
        if(auth.getCurrentUser() != null)
        {
            //displays a text message that this is not a new user
            Toast.makeText(RegistrationActivity.this,"Active user", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }

        //for a new user
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        //when app installed for 1st time, firstTime key isn't present as sharedPreference is empty 
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

        //only opens if app in installed first time
        if(isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            startActivity(new Intent(RegistrationActivity.this, OnBoardingActivity.class));
            finish();
        }

    }

    //calling this function by onClick attribute on a button
    public void signup(View view)
    {
        //getting texts and storing into strings
        String userName = name.getText().toString();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString();

        //showing errors for particular cases
        if(TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, "Enter Email Id!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length() < 6)
        {
            Toast.makeText(this, "Too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        //predefined API by Firebase to create a user with mail and password
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                //going to different activites based on the execution
                if (task.isSuccessful())
                {
                    Toast.makeText(RegistrationActivity.this, "Successfully Register ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void signin(View view)
    {
        Toast.makeText(RegistrationActivity.this,"Sign In", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
    }
}
