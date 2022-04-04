package com.team12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class ActivityCustomerAddress extends AppCompatActivity {

    Toolbar cusToolbar;
    TextView DeliveryAddToolbar;
    EditText LayoutName,Name,LayoutPhoneNo,PhoneNo ,LayoutEmail,Email,LayoutDeliveryAdd,DeliveryAdd,LayoutNote,Note;
    Button ConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);
        InitializeAll();
    }

    private void InitializeAll() {
        cusToolbar = findViewById(R.id.customerAddressToolbarUser);


        DeliveryAddToolbar = findViewById(R.id.CustomerAddressToolbarText);


        LayoutName = findViewById(R.id.cusAddressNameLayout);
        Name = findViewById(R.id.cusAddressName);

        LayoutPhoneNo = findViewById(R.id.cusAddressPhoneLayout);
        PhoneNo = findViewById(R.id.cusAddressPhone);

        LayoutEmail = findViewById(R.id.cusAddressEmailLayout);
        Email = findViewById(R.id.cusAddressEmail);

        LayoutDeliveryAdd = findViewById(R.id.cusAddressDeliveryAddressLayout);
        DeliveryAdd = findViewById(R.id.cusAddressDeliveryAddress);

        LayoutNote = findViewById(R.id.cusAddressNoteLayout);
        Note = findViewById(R.id.cusAddressNote);


        ConfirmBtn = findViewById(R.id.cusAddressConfirmOrderBtn);

    }
}