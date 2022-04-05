package com.team12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityCustomerAddress extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarText;
    TextInputEditText  name, phoneNo, email, deliveryAddress, note;
    TextInputLayout nameLayout, phoneNoLayout, emailLayout, deliveryAddressLayout, noteLayout;
    Button confirmOrder;

    String cusName, cusPhone, cusEmail, cusAddress;


    FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);
        InitializeAll();
        OnClick();
    }

    private void OnClick() {
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCustomerAddress.this, "Under construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitializeAll() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        toolbar = findViewById(R.id.customerAddressToolbarUser);
        toolbarText = findViewById(R.id.CustomerAddressToolbarText);

        name = findViewById(R.id.cusAddressName);
        phoneNo = findViewById(R.id.cusAddressPhone);
        email = findViewById(R.id.cusAddressEmail);
        deliveryAddress = findViewById(R.id.cusAddressDeliveryAddress);
        note = findViewById(R.id.cusAddressNote);

        nameLayout = findViewById(R.id.cusAddressNameLayout);
        phoneNoLayout = findViewById(R.id.cusAddressPhoneLayout);
        emailLayout = findViewById(R.id.cusAddressEmailLayout);
        deliveryAddressLayout = findViewById(R.id.cusAddressDeliveryAddressLayout);
        noteLayout = findViewById(R.id.cusAddressNoteLayout);

        confirmOrder = findViewById(R.id.cusAddressConfirmOrderBtn);


        String userId = mAuth.getUid();
        assert userId != null;
        DatabaseReference profileRef = database.getReference("User").child(userId).child("Profile");
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassUserProfile user = snapshot.getValue(ClassUserProfile.class);
                assert user != null;
                cusName = user.getName();
                cusPhone = user.getPhone();
                cusEmail = user.getEmail();
                cusAddress = user.getAddress();

                name.setText(cusName);
                phoneNo.setText(cusPhone);
                email.setText(cusEmail);
                deliveryAddress.setText(cusAddress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityCustomerAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}