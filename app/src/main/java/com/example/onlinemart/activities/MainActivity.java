package com.example.onlinemart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlinemart.R;
import com.example.onlinemart.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declaring a fragment object to inflate fragment into an activity
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        Button viewCart;
        viewCart = findViewById(R.id.view_cart_button);

        //starts cartActivity when view cart button clicked
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewcart();
            }
        });
    }

    private void viewcart() {
        startActivity(new Intent(MainActivity.this, CartActivity.class));
        finish();
    }

    //function to inflate fragment
    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();

    }
}