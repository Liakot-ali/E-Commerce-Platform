package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProductDetails extends AppCompatActivity {

    Toolbar ProductDetailsToolber;
    TextView ProductDetails, SellerName, ProductPrice, ProductName, Discription;
    ImageView ProductPicture;
    CircleImageView SellerPicture;
    Button BuyNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        InitializeAll();

    }

    private void InitializeAll() {

        ProductDetailsToolber = findViewById(R.id.productDetailsToolbar);
        SellerName = findViewById(R.id.ProductDetailsSellerName);
        ProductDetails = findViewById(R.id.productDetailsToolbarText);
        ProductPrice = findViewById(R.id.ProductDetailsPrice);
        ProductName = findViewById(R.id.ProductDetailsProductName);
        Discription = findViewById(R.id.ProductDetailsDescription);
        ProductPicture = findViewById(R.id.productDetailsPicture);
        SellerPicture = findViewById(R.id.ProductDetailsSellerPicture);
        BuyNow = findViewById(R.id.productDetailsBuyNowBtn);


    }
}