package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassSellerProfile;
import com.team12.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivitySellerProfile extends AppCompatActivity {

    Toolbar SellerToolber;
    TextView SellerProfile, SellerName, SellerPhone, SellerEmail, SellerId;
    Button Report;
    CircleImageView SellerPicture;

    String nameSt, pictureSt, emailSt, phoneSt;
    long sellerId;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        InitializeAll();
        OnClick();

    }

    private void OnClick() {
        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySellerProfile.this, "Under construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitializeAll() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //--------show back icon in toolbar----------
        SellerToolber = findViewById(R.id.SellerProfileToolbar);
        setSupportActionBar(SellerToolber);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SellerProfile = findViewById(R.id.SellerProfileToolbarText);
        SellerName = findViewById(R.id.SellerProfileName);
        SellerPhone = findViewById(R.id.SellerProfilePhoneNo);
        SellerEmail = findViewById(R.id.SellerProfileEmail);
        SellerId = findViewById(R.id.SellerProfileId);
        Report = findViewById(R.id.SellerProfileReportBtn);
        SellerPicture = findViewById(R.id.SellerProfilePicture);

        sellerId = getIntent().getLongExtra("sellerId", 0);
        SellerId.setText(String.valueOf(sellerId));

        //-------Get the seller info from firebase------------
        DatabaseReference sellerProfileRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
        sellerProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassSellerProfile seller = snapshot.getValue(ClassSellerProfile.class);
                assert seller != null;
                nameSt = seller.getName();
                phoneSt = seller.getPhone();
                emailSt = seller.getEmail();
                pictureSt = seller.getPicture();

                SellerName.setText(nameSt);
                SellerPhone.setText(phoneSt);
                SellerEmail.setText(emailSt);
                if (pictureSt != null) {
                    Picasso.get().load(pictureSt).into(SellerPicture);
                } else {
                    SellerPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivitySellerProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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