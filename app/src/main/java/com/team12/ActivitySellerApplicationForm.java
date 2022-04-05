package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ActivitySellerApplicationForm extends AppCompatActivity {
     //initialize variable
    Toolbar toolbar;
    EditText SellerName,SellerAddress,SellerPhone,SellerEmail,Description;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerappform);

        InitializeAll();



    }

    private void InitializeAll() {
        toolbar = findViewById(R.id.formToolbar);
        SellerName = findViewById(R.id.SellerName);
        SellerAddress = findViewById(R.id.SellerAddress);
        SellerPhone = findViewById(R.id.SellerPhone);
        SellerEmail = findViewById(R.id.SellerEmail);
        Description = findViewById(R.id.Description);
        Submit = findViewById(R.id.Submit);
    }
}