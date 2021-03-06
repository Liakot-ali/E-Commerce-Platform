package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Class.ClassAddProduct;
import com.team12.Class.ClassBuyingNotification;
import com.team12.Class.ClassMyOrder;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassSellingNotification;
import com.team12.Class.ClassUserProfile;
import com.team12.R;

import java.util.UUID;

public class ActivityCustomerAddress extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarText;
    TextInputEditText name, phoneNo, email, deliveryAddress, note;
    TextInputLayout nameLayout, phoneNoLayout, emailLayout, deliveryAddressLayout, noteLayout;
    Button confirmOrder;

    String cusName, cusPhone, cusEmail, cusAddress;
    String userId;
    String productId, productName, sellerUserId;
    long sellerId ;

    String productPicture, sellerName, sellerEmail, sellerPhone;
    long productPrice;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

    private static final String EMAIL_CHECK = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
                String nameSt, phoneSt, emailSt, addressSt, noteSt;
                nameSt = name.getText().toString();
                phoneSt = phoneNo.getText().toString();
                emailSt = email.getText().toString();
                addressSt = deliveryAddress.getText().toString();
                noteSt = note.getText().toString();

                if (nameSt.isEmpty()) {
                    nameLayout.setError("Name is empty");
                    phoneNoLayout.setErrorEnabled(false);
                    emailLayout.setErrorEnabled(false);
                    deliveryAddressLayout.setErrorEnabled(false);
                } else if (phoneSt.isEmpty()) {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setError("Phone is empty");
                    emailLayout.setErrorEnabled(false);
                    deliveryAddressLayout.setErrorEnabled(false);
                } else if (phoneSt.length() < 10 || phoneSt.length() > 11) {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setError("Invalid phone number");
                    emailLayout.setErrorEnabled(false);
                    deliveryAddressLayout.setErrorEnabled(false);
                } else if (emailSt.isEmpty()) {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setErrorEnabled(false);
                    emailLayout.setError("Empty email address");
                    deliveryAddressLayout.setErrorEnabled(false);
                } else if (!emailSt.matches(EMAIL_CHECK)) {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setErrorEnabled(false);
                    emailLayout.setError("Invalid email address");
                    deliveryAddressLayout.setErrorEnabled(false);
                } else if (addressSt.isEmpty()) {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setErrorEnabled(false);
                    emailLayout.setErrorEnabled(false);
                    deliveryAddressLayout.setError("Delivery address is empty");
                } else if (addressSt.length() < 10) {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setErrorEnabled(false);
                    emailLayout.setErrorEnabled(false);
                    deliveryAddressLayout.setError("Enter valid address");
                } else {
                    nameLayout.setErrorEnabled(false);
                    phoneNoLayout.setErrorEnabled(false);
                    emailLayout.setErrorEnabled(false);
                    deliveryAddressLayout.setErrorEnabled(false);

                    //TODO---store the data in firebase-------
                    String sellingId = String.valueOf(System.currentTimeMillis());
                    ClassSellingNotification order = new ClassSellingNotification(productId, productName, String.valueOf(productPrice), productPicture, nameSt, phoneSt, emailSt, addressSt, noteSt, "ConfirmOrder");
                    ClassMyOrder newMyOrder = new ClassMyOrder(productName, getResources().getString(R.string.tk_sign) + productPrice, productPicture, sellerName, sellerPhone, sellerEmail);
                    ClassBuyingNotification newBuy = new ClassBuyingNotification(productName, getResources().getString(R.string.tk_sign) + productPrice, productPicture, sellerName, sellerPhone, sellerEmail, "ConfirmOrder");

                    DatabaseReference sellerRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MySelling").child(sellingId);
                    DatabaseReference notiRef = database.getReference("User").child(sellerUserId).child("Notification").child("SellingNotification").child(sellingId);
                    DatabaseReference buyerNotiRef = database.getReference("User").child(userId).child("Notification").child("BuyingNotification").child(sellingId);
                    DatabaseReference orderRef = database.getReference("User").child(userId).child("MyOrders").child(sellingId);

                    notiRef.setValue(order).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            orderRef.setValue(newMyOrder).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    sellerRef.setValue(sellingId);
                                    buyerNotiRef.setValue(newBuy);
                                    Toast.makeText(ActivityCustomerAddress.this, "Order successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityCustomerAddress.this, ActivityHome.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(ActivityCustomerAddress.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(ActivityCustomerAddress.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void InitializeAll() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //----------show back button in toolbar----------
        toolbar = findViewById(R.id.customerAddressToolbarUser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        productId = getIntent().getStringExtra("productId");
        productName = getIntent().getStringExtra("productName");
        sellerId = getIntent().getLongExtra("sellerId", 0);
        userId = mAuth.getUid();
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

        DatabaseReference productRef = database.getReference("Product").child(productId);
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassAddProduct product = snapshot.getValue(ClassAddProduct.class);
                assert product != null;
                productPrice = product.getPrice();
                productPicture = product.getImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityCustomerAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //------get the sellerUserId form seller profile-------------
        DatabaseReference sellerProfileRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
        sellerProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassSellerProfile profile = snapshot.getValue(ClassSellerProfile.class);
                assert profile != null;
                sellerUserId = profile.getUserId();
                sellerName = profile.getName();
                sellerPhone = profile.getPhone();
                sellerEmail = profile.getEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityCustomerAddress.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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