package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivitySellerProfile extends AppCompatActivity {

    Toolbar SellerToolber;
    TextView SellerProfile,SellerName,SellerPhone,SellerEmail,SellerId;
    Button Report;
    CircleImageView SellerPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        InitializeAll();

    }

    private void InitializeAll() {
        SellerToolber=findViewById(R.id.SellerProfileToolbar);
        SellerProfile=findViewById(R.id.SellerProfileToolbarText);
        SellerName=findViewById(R.id.SellerProfileName);
        SellerPhone=findViewById(R.id.SellerProfilePhoneNo);
        SellerEmail=findViewById(R.id.SellerProfileEmail);
        SellerId=findViewById(R.id.SellerProfileId);
        Report=findViewById(R.id.SellerProfileReportBtn)  ;
       SellerPicture=findViewById(R.id.SellerProfilePicture);


    }
}