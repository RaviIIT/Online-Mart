package com.example.onlinemart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onlinemart.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signIn(View view)
    {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    public void signUp(View view)
    {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}