package com.team12.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.team12.R;

public class AdminApproveSellerDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView SellerName, SellerEmail, SellerPhone, SellerAddress, SellerDescription;
    ImageView SellerDetailsPic;
    Button Approve, Deny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seller_details);

        InitializeAll();

    }

    private void InitializeAll() {

        //--------show back icon in toolbar----------
        toolbar = findViewById(R.id.Seller_detailsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SellerName = findViewById(R.id.Seller_detailsName);
        SellerEmail = findViewById(R.id.Seller_detailsEmail);
        SellerPhone = findViewById(R.id.Seller_detailsPhoneNo);
        SellerAddress = findViewById(R.id.Seller_detailsAddress);
        SellerDescription = findViewById(R.id.Seller_detailsDescription);

        SellerDetailsPic = findViewById(R.id.Seller_detailsImg);

        Approve = findViewById(R.id.adminSellerDetailsApproveBtn);
        Deny = findViewById(R.id.adminSellerDetailsDenyBtn);
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