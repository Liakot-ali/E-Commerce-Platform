package com.team12.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassAddProduct;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassSellingNotification;
import com.team12.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminApproveProductsDetails extends AppCompatActivity {

    Toolbar toolbar;
    TextView price, name, desc, SName;
    CircleImageView SPicture;
    ImageView PPicture;
    Button approveBtn, denyBtn;

    String productName, productPicture, description, sellerName, sellerPicture, productId, sellerUserId;
    long productPrice, sellerId;

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approve_product_details);
        InitializeAll();
        OnClick();
    }

    private void OnClick() {
        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO-----------send a notification to the seller as 'your product is approved by admin'--------

                DatabaseReference adminRef = database.getReference("Admin").child("ProductApprove").child(productId);
                DatabaseReference sellerRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MyProduct").child(productId);
                DatabaseReference productRef = database.getReference("Product").child(productId);
                DatabaseReference notiRef = database.getReference("User").child(sellerUserId).child("Notification").child("SellingNotification");
                ClassAddProduct newProduct = new ClassAddProduct(productId, productName, description, productPicture, productPrice, sellerName, sellerId);

                productRef.setValue(newProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            adminRef.removeValue();
                            sellerRef.setValue(productId);
                            Intent intent = new Intent(AdminApproveProductsDetails.this, AdminApproveProductList.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            Toast.makeText(AdminApproveProductsDetails.this, "Product approved", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AdminApproveProductsDetails.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO-----------send a notification to the seller for edit the product--------
                ClassAddProduct newProduct = new ClassAddProduct(productId, productName, description, productPicture, productPrice, sellerId, "Edit");
                DatabaseReference adminRef = database.getReference("Admin").child("ProductApprove").child(productId);

                adminRef.setValue(newProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdminApproveProductsDetails.this, "Product denyd", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminApproveProductsDetails.this, AdminApproveProductList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });

//                Toast.makeText(AdminApproveProductsDetails.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitializeAll() {
        database = FirebaseDatabase.getInstance();

        //------------show back button in toolbar------------
        toolbar = findViewById(R.id.adminProductDetailsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //------------get the value form previous activity--------------
        productId = getIntent().getStringExtra("ProductId");
        productName = getIntent().getStringExtra("ProductName");
        productPicture = getIntent().getStringExtra("ProductPicture");
        description = getIntent().getStringExtra("Description");
        productPrice = getIntent().getLongExtra("ProductPrice", 0);

        sellerId = getIntent().getLongExtra("SellerId", 0);

        //--------find the Id form layout file------------
        price = findViewById(R.id.adminProductDetailsPrice);
        name = findViewById(R.id.adminProductDetailsProductName);
        desc = findViewById(R.id.adminProductDetailsDescription);
        PPicture = findViewById(R.id.adminProductDetailsPicture);
        SPicture = findViewById(R.id.adminProductDetailsSellerPicture);
        SName = findViewById(R.id.adminProductDetailsSellerName);

        approveBtn = findViewById(R.id.adminProductDetailsApproveBtn);
        denyBtn = findViewById(R.id.adminProductDetailsDenyBtn);

        //--------------get the seller picture form database------------
        DatabaseReference sellerRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
        sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassSellerProfile profile = snapshot.getValue(ClassSellerProfile.class);
                assert profile != null;
                sellerPicture = profile.getPicture();
                sellerUserId = profile.getUserId();
                sellerName = profile.getName();

                SName.setText(sellerName);
                Picasso.get().load(sellerPicture).into(SPicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminApproveProductsDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //---------------set the value to the desired field--------------
        Picasso.get().load(productPicture).into(PPicture);
//        Picasso.get().load(sellerPicture).into(SPicture);

        name.setText(productName);
        price.setText(getResources().getString(R.string.tk_sign) + String.valueOf(productPrice));
        desc.setText(description);
//        SName.setText(sellerName);

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