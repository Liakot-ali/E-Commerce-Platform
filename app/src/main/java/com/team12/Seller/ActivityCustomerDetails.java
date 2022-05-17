package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.team12.R;


public class ActivityCustomerDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText, ProductName, ProductPrice, CustomerName, CustomerPhoneNo, CustomerEmail, CustomerAddress, Note;
    ImageView customerDetailsProductPic;
    Button ResponseBtn;

    String pName, pPrice, pPicture, pId, cusName, cusPhone, cusEmail, cusAddress, cusNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        InitializeAll();
        ResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCustomerDetails.this, "Under construction", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void InitializeAll() {
        toolbar = findViewById(R.id.Cus_detailsToolbar);
        toolbarText = findViewById(R.id.Cus_detailsTxt);
        //----------show back icon in toolbar---------
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        customerDetailsProductPic = findViewById(R.id.CUS_detailsImg);

        ProductName = findViewById(R.id.Cus_detailsProductName);
        ProductPrice = findViewById(R.id.Cus_detailsProductPrice);
        CustomerName = findViewById(R.id.Cus_detailsCustomerName);
        CustomerPhoneNo = findViewById(R.id.Cus_detailsCustomerPhoneNo);
        CustomerEmail = findViewById(R.id.Cus_detailsCustomerEmail);
        CustomerAddress = findViewById(R.id.Cus_detailsCustomerAddress);
        Note = findViewById(R.id.Cus_detailsNote);
        ResponseBtn = findViewById(R.id.cus_detailsResponseBtn);

        //-----------get the value from previous activity------
        pName = getIntent().getStringExtra("ProductName");
        pPrice = getIntent().getStringExtra("ProductPrice");
        pPicture = getIntent().getStringExtra("ProductPicture");
        pId = getIntent().getStringExtra("ProductId");

        cusName = getIntent().getStringExtra("CustomerName");
        cusPhone = getIntent().getStringExtra("CustomerPhone");
        cusEmail = getIntent().getStringExtra("CustomerEmail");
        cusAddress = getIntent().getStringExtra("CustomerAddress");
        cusNote = getIntent().getStringExtra("CustomerNote");

        Picasso.get().load(pPicture).into(customerDetailsProductPic);
        ProductName.setText(pName);
        ProductPrice.setText(getResources().getString(R.string.tk_sign) + pPrice);

        CustomerName.setText(cusName);
        CustomerPhoneNo.setText(cusPhone);
        CustomerEmail.setText(cusEmail);
        CustomerAddress.setText(cusAddress);
        Note.setText(cusNote);

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