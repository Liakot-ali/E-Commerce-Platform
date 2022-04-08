package com.team12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProductDetails extends AppCompatActivity {

    Toolbar ProductDetailsToolber;
    TextView ProductDetails, SellerName, ProductPrice, ProductName, Discription;
    ImageView ProductPicture;
    CircleImageView SellerPicture;
    Button BuyNow;

    String productId, sellerName, productName, description, sellerPicture, productPicture;
    long sellerId, productPrice;
    Boolean loggedIn = true;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        InitializeAll();
        OnClick();

    }

    private void OnClick() {
        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn){
                    Toast.makeText(ActivityProductDetails.this, "Confirm order", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityProductDetails.this, ActivityCustomerAddress.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void InitializeAll() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ProductDetailsToolber = findViewById(R.id.productDetailsToolbar);
        SellerName = findViewById(R.id.ProductDetailsSellerName);
        ProductDetails = findViewById(R.id.productDetailsToolbarText);
        ProductPrice = findViewById(R.id.ProductDetailsPrice);
        ProductName = findViewById(R.id.ProductDetailsProductName);
        Discription = findViewById(R.id.ProductDetailsDescription);
        ProductPicture = findViewById(R.id.productDetailsPicture);
        SellerPicture = findViewById(R.id.ProductDetailsSellerPicture);
        BuyNow = findViewById(R.id.productDetailsBuyNowBtn);

        productId = getIntent().getStringExtra("productId");
        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getLongExtra("productPrice", 0);
        productPicture = getIntent().getStringExtra("productPicture");
        description = getIntent().getStringExtra("productDescription");

        sellerId = getIntent().getLongExtra("sellerId", 0);
        sellerName = getIntent().getStringExtra("sellerName");
        sellerPicture = getIntent().getStringExtra("sellerPicture");

        authListener = firebaseAuth -> {
            if(FirebaseAuth.getInstance().getCurrentUser() ==  null){
                loggedIn = false;
                //TODO------show a snackbar with login activity link----------
                Toast.makeText(ActivityProductDetails.this, "Log in first", Toast.LENGTH_SHORT).show();
            }else{
                loggedIn = true;
            }
        };


        if(sellerPicture != null){
            Picasso.get().load(sellerPicture).into(SellerPicture);
        }
        else{
            SellerPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);
        }
        if(productPicture != null){
            Picasso.get().load(productPicture).into(ProductPicture);
        }else{
            ProductPicture.setImageResource(R.drawable.ic_product_demo_photo_24);
        }

        SellerName.setText(sellerName);
        ProductName.setText(productName);
        ProductPrice.setText(getResources().getString(R.string.tk_sign) + productPrice);
        Discription.setText(description);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(authListener);
    }
}