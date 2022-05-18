package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassBuyingNotification;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassSellingNotification;
import com.team12.Class.ClassUserProfile;
import com.team12.R;
import com.team12.User.ActivityNotification;


public class ActivityCustomerDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText, ProductName, ProductPrice, CustomerName, CustomerPhoneNo, CustomerEmail, CustomerAddress, Note;
    ImageView customerDetailsProductPic;
    Button ResponseBtn;

    String pName, pPrice, pPicture, pId, cusName, cusPhone, cusEmail, cusAddress, cusNote;
    String userId, sellingId;
    long sellerId;
    String sellerName, sellerPhone, sellerEmail;

    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        InitializeAll();
        ResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClassSellingNotification conOrder = new ClassSellingNotification(pId, pName, pPrice, pPicture, cusName, cusPhone, cusEmail, cusAddress, cusNote, "Response");
                DatabaseReference sellingRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MySelling").child(String.valueOf(System.currentTimeMillis()));
                DatabaseReference notiRef = database.getReference("User").child(userId).child("Notification").child("SellingNotification").child(sellingId);

                //----TODO------sent userNotification-------
//                DatabaseReference userNotiRef = database.getReference("User").child("cusUserID");
//                ClassBuyingNotification response = new ClassBuyingNotification(pName, pPrice, pPicture, sellerName, sellerPhone, sellerEmail, "SellerResponse");

                sellingRef.setValue(conOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notiRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ActivityCustomerDetails.this, "Your confirm the order. Check the order in your My Selling list", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ActivityCustomerDetails.this, ActivityNotification.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                });
//                Toast.makeText(ActivityCustomerDetails.this, "Under construction", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void InitializeAll() {

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userId = auth.getUid();

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
        sellingId = getIntent().getStringExtra("SellingId");
        String passCode = getIntent().getStringExtra("PassCode");
        if(passCode.equals("MySelling")){
            ResponseBtn.setVisibility(View.GONE);
        }else{
            ResponseBtn.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(pPicture).into(customerDetailsProductPic);
        ProductName.setText(pName);
        ProductPrice.setText(getResources().getString(R.string.tk_sign) + pPrice);

        CustomerName.setText(cusName);
        CustomerPhoneNo.setText(cusPhone);
        CustomerEmail.setText(cusEmail);
        CustomerAddress.setText(cusAddress);
        Note.setText(cusNote);

        DatabaseReference profileRef = database.getReference("User").child(userId).child("Profile");
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassUserProfile profile = snapshot.getValue(ClassUserProfile.class);
                assert profile != null;
                sellerId = profile.getSellerId();
                DatabaseReference sellerRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
                sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ClassSellerProfile profile = snapshot.getValue(ClassSellerProfile.class);
                        assert profile != null;
                        sellerName = profile.getName();
                        sellerPhone = profile.getPhone();
                        sellerEmail = profile.getEmail();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ActivityCustomerDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityCustomerDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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