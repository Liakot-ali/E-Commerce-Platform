package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ActivitySellerApplicationForm extends AppCompatActivity {

    Toolbar toolbar;
    EditText SellerName,SellerAddress,SellerPhone, SellerEmail,Description;
    CheckBox checkBox;
    ImageView SellerPicture;
    Button Submit;

    String name, phone, email, address, picture, description, userId;
    long sellerId;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_application_form);

        InitializeAll();
        OnClick();

    }

    private void OnClick() {
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO---check the validity and store the value in Admin->SellerApproval->SellerId
                Toast.makeText(ActivitySellerApplicationForm.this, "Under construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitializeAll() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        toolbar = findViewById(R.id.formToolbar);
        SellerPicture = findViewById(R.id.sellerPicture);
        SellerName = findViewById(R.id.SellerName);
        SellerAddress = findViewById(R.id.SellerAddress);
        SellerPhone = findViewById(R.id.SellerPhone);
        SellerEmail = findViewById(R.id.SellerEmail);
        Description = findViewById(R.id.Description);
        checkBox = findViewById(R.id.SellerCheckBox);
        Submit = findViewById(R.id.Submit);

        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        address = getIntent().getStringExtra("address");
        picture = getIntent().getStringExtra("picture");
        description = getIntent().getStringExtra("description");
        userId = getIntent().getStringExtra("userId");
        sellerId = getIntent().getLongExtra("sellerId", 0);

        SellerName.setText(name);
        SellerPhone.setText(phone);
        SellerEmail.setText(email);
        SellerAddress.setText(address);
        Description.setText(description);
        if(picture != null){
            Picasso.get().load(picture).into(SellerPicture);
        }else {
            SellerPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);
        }
    }
}