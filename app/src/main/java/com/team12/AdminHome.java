package com.team12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminHome extends AppCompatActivity {
    ImageView product, seller, sellerList, feedback;
    LinearLayout productLay, sellerLay, sellerListLay, feedbackLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Initialize();
        OnClick();
    }

    private void OnClick() {
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminApproveProduct.class);
                startActivity(intent);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminApproveSeller.class);
                startActivity(intent);
            }
        });

        sellerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminHome.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminHome.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Initialize() {

        product = findViewById(R.id.adminHomeProduct);
        seller = findViewById(R.id.adminHomeSeller);
        sellerList = findViewById(R.id.adminHomeSellerList);
        feedback = findViewById(R.id.adminHomeFeedback);

        productLay = findViewById(R.id.adminHomeProductLayout);
        sellerLay= findViewById(R.id.adminHomeSellerLayout);
        sellerListLay = findViewById(R.id.adminHomeSellerListLayout);
        feedbackLay = findViewById(R.id.adminHomeFeedbackLayout);

    }
}