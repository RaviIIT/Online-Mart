package com.example.onlinemart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.onlinemart.R;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button placeOrder;
    TextView subTotal, discount, shipping, total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Toolbar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount_total);
        shipping = findViewById(R.id.shipping_total);
        total = findViewById(R.id.total_amt);

        subTotal.setText("Rs " +amount);
        total.setText("Rs " + amount);
        placeOrder = findViewById(R.id.pay_btn);


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PaymentActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}