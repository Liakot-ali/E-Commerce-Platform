package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.team12.R;

public class ActivityMyOrderDetails extends AppCompatActivity {

    ImageView productPicture;
    TextView productName, productPrice, sellerName, sellerPhone, sellerEmail;
    Toolbar toolbar;

    String pName, pPrice, pPicture, sName, sPhone, sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);
        InitializeAll();
    }

    private void InitializeAll() {
        //-------------get the value from the previous activity-------
        pPicture = getIntent().getStringExtra("ProductPicture");
        pName = getIntent().getStringExtra("ProductName");
        pPrice = getIntent().getStringExtra("ProductPrice");
        sName = getIntent().getStringExtra("SellerName");
        sEmail = getIntent().getStringExtra("SellerEmail");
        sPhone = getIntent().getStringExtra("SellerPhone");

        //------------show back icon in toolbar------------
        toolbar = findViewById(R.id.orderDetailsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        productName = findViewById(R.id.orderDetailsProductName);
        productPrice = findViewById(R.id.orderDetailsProductPrice);
        sellerName = findViewById(R.id.orderDetailsSellerName);
        sellerPhone = findViewById(R.id.orderDetailsSellerPhoneNo);
        sellerEmail = findViewById(R.id.orderDetailsSellerEmail);
        productPicture = findViewById(R.id.orderDetailsPicture);

        //---------set the value in the desired field-------------
        productName.setText(pName);
        productPrice.setText(pPrice);
        sellerName.setText(sName);
        sellerPhone.setText(sPhone);
        sellerEmail.setText(sEmail);

        Picasso.get().load(pPicture).into(productPicture);

    }

    //---------for back to previous activity-------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}