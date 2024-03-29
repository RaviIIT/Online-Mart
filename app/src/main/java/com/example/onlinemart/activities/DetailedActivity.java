package com.example.onlinemart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onlinemart.R;
import com.example.onlinemart.models.NewProductsModel;
import com.example.onlinemart.models.PopularProductsModel;
import com.example.onlinemart.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;
    int totalQuantity = 1, totalPrice = 0;

    //New Products
    NewProductsModel newProductsModel = null;

    //Popular Products
    PopularProductsModel popularProductsModel = null;

    //Show All
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();

        // getting data from intent passed from previous activity
        final Object obj = getIntent().getSerializableExtra("detailed");

        // checking if the upper data is from this model
        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }        // checking if the upper data is from this model
        else if (obj instanceof  PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }        // checking if the upper data is from this model
        else if (obj instanceof  ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        rating = findViewById(R.id.my_rating);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        //filling data accordingly what item was clicked
        //New Products
        if(newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }

        //Popular Products
        if(popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));

            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }

        //Show All Products
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }

                if(newProductsModel != null){
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                }
                if(popularProductsModel != null){
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                }
                if(showAllModel != null){
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                }

            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });

        //Buy Now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

            //passing an extra data with intent
            if(newProductsModel != null){
                intent.putExtra("item", newProductsModel);
            }
            if(popularProductsModel != null){
                intent.putExtra("item", popularProductsModel);
            }
            if(showAllModel != null){
                intent.putExtra("item", showAllModel);
            }

            startActivity(intent);
            }
        });

        //Addtocart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });

    }

    private void addtoCart() {
        //entering time and date along with the prices into our database
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        //you have to add authentication in the end wherever you used authentication
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

}