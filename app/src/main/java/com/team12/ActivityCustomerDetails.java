package com.team12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class ActivityCustomerDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText,ProductName,ProductPrice,CustomerName,CustomerPhoneNo,CustomerAddress,Note;
    ImageView customerDetailsProductPic;
    Button ResponseBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        InitializeAll();



    }

    private void InitializeAll() {
        toolbar = findViewById(R.id.Cus_detailsToolbar);
        toolbarText = findViewById(R.id.Cus_detailsTxt);

        customerDetailsProductPic =findViewById(R.id.CUS_detailsImg);

        ProductName = findViewById(R.id.Cus_detailsCustomerName);
        ProductPrice = findViewById(R.id.Cus_detailsProductPrice);
        CustomerName = findViewById(R.id.Cus_detailsCustomerName);
        CustomerPhoneNo =findViewById(R.id.Cus_detailsCustomerPhoneNo);
        CustomerAddress = findViewById(R.id.Cus_detailsCustomerAddress);
        Note = findViewById(R.id.Cus_detailsNote);

        ResponseBtn = findViewById(R.id.cus_detailsResponseBtn);



    }
}