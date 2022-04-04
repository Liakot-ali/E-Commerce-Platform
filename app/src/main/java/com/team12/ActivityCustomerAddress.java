package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityCustomerAddress extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarText;
    TextInputEditText  name, phoneNo, email, deliveryAddress, note;
    TextInputLayout nameLayout, phoneNoLayout, emailLayout, deliveryAddressLayout, noteLayout;
    Button confirmOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);
        InitializeAll();
    }

    private void InitializeAll() {


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



    }
}