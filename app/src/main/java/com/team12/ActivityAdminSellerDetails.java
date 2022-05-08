package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAdminSellerDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView SellerToolbar,SellerName,SellerEmail,SellerPhone,SellerAddress,SellerDescription;
    ImageView SellerDetailsPic;
    Button Approve,Deny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seller_details);

        InitializeAll();
    }
    private void InitializeAll(){
        toolbar = findViewById(R.id.Seller_detailsToolbar);
        SellerToolbar = findViewById(R.id.Seller_detailsTxt);

        SellerName = findViewById(R.id.Seller_detailsName);
        SellerEmail = findViewById(R.id.Seller_detailsEmail);
        SellerPhone = findViewById(R.id.Seller_detailsPhoneNo);
        SellerAddress = findViewById(R.id.Seller_detailsAddress);
        SellerDescription = findViewById(R.id.Seller_detailsDescription);

        SellerDetailsPic = findViewById(R.id.Seller_detailsImg);

        Approve = findViewById(R.id.Seller_detailsApprove);
        Deny = findViewById(R.id.Seller_detailsDeny);

    }
}