package com.team12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdminHome extends AppCompatActivity {
    ImageView product, seller, sellerList, feedback;
    LinearLayout productLay, sellerLay, sellerListLay, feedbackLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Initialize();
    }

    private void Initialize() {

        product = findViewById(R.id.adminHomeProduct);
        product = findViewById(R.id.adminHomeProduct);
        product = findViewById(R.id.adminHomeProduct);
        product = findViewById(R.id.adminHomeProduct);

        product = findViewById(R.id.adminHomeProduct);
        product = findViewById(R.id.adminHomeProduct);
        product = findViewById(R.id.adminHomeProduct);
        product = findViewById(R.id.adminHomeProduct);

    }
}