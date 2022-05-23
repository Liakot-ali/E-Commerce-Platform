package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassAddProduct;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassSellingNotification;
import com.team12.R;

import java.util.UUID;

public class ActivityPostProduct extends AppCompatActivity {

    final static int PICK_IMAGE = 10;
    Uri productPictureUri = null;

    Toolbar toolbar;
    ImageView productPicture;
    Button addProductPicture, postProduct;
    TextInputEditText productName, productPrice, productDescription;
    TextInputLayout productNameLay, productPriceLay, productDescriptionLay;

    long sellerId = 0;
    String sellerUserId;
    String sellerPicture = null;
    String sellerName = null;
    String productId = null;
    String type, pictureSt = null;
    String passCode = null;

    FirebaseDatabase database;
    FirebaseStorage storage;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        InitializeAll();
        OnClick();
    }

    private void OnClick() {

        addProductPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        postProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                String name, price, description;
                name = productName.getText().toString();
                price = productPrice.getText().toString();
                description = productDescription.getText().toString();

                if (productPictureUri == null) {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.postProductActivityParentLayout), "Add product picture", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Add Image", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PICK_IMAGE);
                        }
                    });
                    snackbar.show();
                } else if (name.equals("")) {
                    progressDialog.dismiss();
                    productNameLay.setError("Product must have a name");
                    productPriceLay.setErrorEnabled(false);
                    productDescriptionLay.setErrorEnabled(false);
                } else if (price.equals("")) {
                    progressDialog.dismiss();
                    productNameLay.setErrorEnabled(false);
                    productPriceLay.setError("Invalid price");
                    productDescriptionLay.setErrorEnabled(false);
                } else if (description.equals("")) {
                    progressDialog.dismiss();
                    productNameLay.setErrorEnabled(false);
                    productPriceLay.setErrorEnabled(false);
                    productDescriptionLay.setError("Write product details");
                } else {
                    productNameLay.setErrorEnabled(false);
                    productPriceLay.setErrorEnabled(false);
                    productDescriptionLay.setErrorEnabled(false);

                    //TODO----add the information to the database
                    // 1. Admin product review list

                    // TODO-----add image in the storage
                    // 1. Product picture
                    if (productId == null) {
                        productId = UUID.randomUUID().toString();

                        StorageReference productPicRef = storage.getReference("ProductPicture").child(productId);
                        productPicRef.putFile(productPictureUri).
                                addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        productPicRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                            String imageUri;
                                            imageUri = uri.toString();
                                            DatabaseReference productRef = database.getReference("Admin").child("ProductApprove").child(productId);
                                            DatabaseReference sellerNotiRef = database.getReference("User").child(sellerUserId).child("Notification").child("SellingNotification").child(String.valueOf(System.currentTimeMillis()));

                                            ClassSellingNotification notification = new ClassSellingNotification(productId, name, price, pictureSt, description, String.valueOf(sellerId), "PostProduct");
                                            ClassAddProduct newProduct = new ClassAddProduct(productId, name, description, imageUri, Integer.parseInt(price), sellerId, "New");
                                            productRef.setValue(newProduct).addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    sellerNotiRef.setValue(notification).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                progressDialog.dismiss();
                                                                Snackbar.make(findViewById(R.id.postProductActivityParentLayout), "Your product upload request is submitted for review", Snackbar.LENGTH_SHORT).show();
                                                                productName.setText("");
                                                                productPrice.setText("");
                                                                productDescription.setText("");
                                                                productPicture.setImageResource(R.drawable.ic_product_demo_photo_24);
                                                            }else{
                                                                progressDialog.dismiss();
                                                                Toast.makeText(ActivityPostProduct.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(ActivityPostProduct.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(e -> {
                                                progressDialog.dismiss();
                                                Toast.makeText(ActivityPostProduct.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            });

                                        });
                                    }
                                }).addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(ActivityPostProduct.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    }else{
                        DatabaseReference productRef = database.getReference("Admin").child("ProductApprove").child(productId);
                        DatabaseReference myProductRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MyProduct").child(productId);
                        DatabaseReference sellerNotiRef = database.getReference("User").child(sellerUserId).child("Notification").child("SellingNotification").child(String.valueOf(System.currentTimeMillis()));

                        ClassSellingNotification notification = new ClassSellingNotification(productId, name, price, pictureSt, description, String.valueOf(sellerId), "PostProduct");
                        ClassAddProduct newProduct = new ClassAddProduct(productId, name, description, pictureSt, Integer.parseInt(price), sellerId, "Edit");
                        productRef.setValue(newProduct).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                sellerNotiRef.setValue(notification).addOnCompleteListener(task -> {
                                    if(passCode.equals("Edit")){
                                        //-------if the seller edit the product which is already shown in home-------------
                                        DatabaseReference deleteProductRef = database.getReference("Product").child(productId);
                                        deleteProductRef.removeValue().addOnCompleteListener(task2 -> {
                                            progressDialog.dismiss();
                                            myProductRef.removeValue();
                                            Snackbar.make(findViewById(R.id.postProductActivityParentLayout), "Your product upload request is submitted for review", Snackbar.LENGTH_SHORT).show();
                                            productName.setText("");
                                            productPrice.setText("");
                                            productDescription.setText("");
                                            productPicture.setImageResource(R.drawable.ic_product_demo_photo_24);
                                        });
                                    }else{
                                        progressDialog.dismiss();
                                        Snackbar.make(findViewById(R.id.postProductActivityParentLayout), "Your product upload request is submitted for review", Snackbar.LENGTH_SHORT).show();
                                        productName.setText("");
                                        productPrice.setText("");
                                        productDescription.setText("");
                                        productPicture.setImageResource(R.drawable.ic_product_demo_photo_24);
                                    }
                                });
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(ActivityPostProduct.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityPostProduct.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                }
            }
        });

    }

    private void InitializeAll() {

        sellerId = getIntent().getLongExtra("SellerId", 0);
        productId = getIntent().getStringExtra("ProductId");
        passCode = getIntent().getStringExtra("PassCode");


        //--------Firebase initialization-------
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //--------show back icon in toolbar----------
        toolbar = findViewById(R.id.postProductToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //--------Image------
        productPicture = findViewById(R.id.postProductPicture);

        //------Button---------
        addProductPicture = findViewById(R.id.postProductAddPicture);
        postProduct = findViewById(R.id.postProductPostBtn);

        //-----------TextInputEditText----------
        productName = findViewById(R.id.postProductName);
        productPrice = findViewById(R.id.postProductPrice);
        productDescription = findViewById(R.id.postProductDescription);

        //------TextInputEditTextLayout------
        productNameLay = findViewById(R.id.postProductNameLayout);
        productPriceLay = findViewById(R.id.postProductPriceLayout);
        productDescriptionLay = findViewById(R.id.postProductDescriptionLayout);

        //-----get the details info if the product has to be edit----------
        if(productId == null){
            type = "new";
        }else{
            DatabaseReference editProductRef;
            if(passCode.equals("Admin")) {
                 editProductRef = database.getReference("Admin").child("ProductApprove").child(productId);
            }else{
                editProductRef = database.getReference("Product").child(productId);
            }
            type ="Edit";

            editProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ClassAddProduct product = snapshot.getValue(ClassAddProduct.class);
                    assert product != null;
                    productName.setText(product.getName());
                    productPrice.setText(String.valueOf(product.getPrice()));
                    productDescription.setText(product.getDescription());
                    Picasso.get().load(product.getImage()).into(productPicture);

                    pictureSt = product.getImage();
                    productPictureUri = Uri.parse(pictureSt);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ActivityPostProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //------------get the seller info------------
        DatabaseReference sellerRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
        sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassSellerProfile seller = snapshot.getValue(ClassSellerProfile.class);
                assert seller != null;
                sellerPicture = seller.getPicture();
                sellerName = seller.getName();
                sellerUserId = seller.getUserId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityPostProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog = new ProgressDialog(ActivityPostProduct.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("We are working on your product");
        progressDialog.setCancelable(false);

    }

    //--------For add the image in the imageView----------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data.getData() != null) {
            productPictureUri = data.getData();
            Picasso.get().load(productPictureUri).into(productPicture);
        } else {
            productPictureUri = null;
        }
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