package com.team12;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class ActivityPostProduct extends AppCompatActivity {

    final static int PICK_IMAGE = 10;
    Uri productPictureUri = null;

    ImageView productPicture;
    Button addProductPicture, postProduct;
    TextInputEditText productName, productPrice, productDescription;
    TextInputLayout productNameLay, productPriceLay, productDescriptionLay;


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

                if(productPictureUri == null) {
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
                }
                else if(name.equals("")){
                    progressDialog.dismiss();
                    productNameLay.setError("Product must have a name");
                    productPriceLay.setErrorEnabled(false);
                    productDescriptionLay.setErrorEnabled(false);
                }else if(price.equals("")){
                    progressDialog.dismiss();
                    productNameLay.setErrorEnabled(false);
                    productPriceLay.setError("Invalid price");
                    productDescriptionLay.setErrorEnabled(false);
                }else if(description.equals("")){
                    progressDialog.dismiss();
                    productNameLay.setErrorEnabled(false);
                    productPriceLay.setErrorEnabled(false);
                    productDescriptionLay.setError("Write product details");
                }else{
                    productNameLay.setErrorEnabled(false);
                    productPriceLay.setErrorEnabled(false);
                    productDescriptionLay.setErrorEnabled(false);

                    //TODO----add the information to the database
                    // 1. Admin product review list

                    // TODO-----add image in the storage
                    // 1. Product picture
                    String productId;
                    int sellerId = 1234567;
                    productId = UUID.randomUUID().toString();

                    StorageReference productPicRef = storage.getReference("ProductPicture").child(productId);
                    productPicRef.putFile(productPictureUri).
                            addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    productPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUri;
                                            imageUri = uri.toString();
                                            DatabaseReference productRef = database.getReference("Product").child(productId);
                                            ClassAddProduct newProduct = new ClassAddProduct(productId, name, description, imageUri, Integer.parseInt(price), sellerId);
                                            productRef.setValue(newProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    Snackbar.make(findViewById(R.id.postProductActivityParentLayout), "Product added successfully", Snackbar.LENGTH_SHORT).show();
                                                    productName.setText("");
                                                    productPrice.setText("");
                                                    productDescription.setText("");
                                                    productPicture.setImageResource(R.drawable.ic_product_demo_photo_24);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(ActivityPostProduct.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }
                                    });
                                }
                            }).addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(ActivityPostProduct.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                }
            }
        });

    }

    private void InitializeAll() {
        //--------Firebase initialization-------
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

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
        }
        else{
            productPictureUri = null;
        }
    }
}