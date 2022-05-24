package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.team12.R;

public class ActivityMyProductDetails extends AppCompatActivity {

    Toolbar toolbar;
    TextView productName, productPrice, productDescription;
    Button editBtn, deleteBtn;
    ImageView productPicture;

    String name, description, picture, productId;
    long price, sellerId;


    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product_details);
        InitializeAll();
        OnClick();
    }

    private void OnClick() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ActivityMyProductDetails.this, "Under Construction", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityMyProductDetails.this, ActivityPostProduct.class);
                intent.putExtra("SellerId", sellerId);
                intent.putExtra("ProductId", productId);
                intent.putExtra("PassCode", "Edit");
                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityMyProductDetails.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("You can't undo your product");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference myProductRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MyProduct").child(productId);

                        DatabaseReference productRef = database.getReference("Product").child(productId);
                        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    if(myProductRef.removeValue().isSuccessful()) {
                                        Toast.makeText(ActivityMyProductDetails.this, "Your product is  successfully deleted", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ActivityMyProductDetails.this, ActivityMyProduct.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(ActivityMyProductDetails.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("No", null);
                dialog.setCancelable(true);
                dialog.show();

//                Toast.makeText(ActivityMyProductDetails.this, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InitializeAll() {
        name = getIntent().getStringExtra("ProductName");
        price = getIntent().getLongExtra("ProductPrice",0);
        description = getIntent().getStringExtra("Description");
        picture = getIntent().getStringExtra("ProductPicture");
        productId = getIntent().getStringExtra("ProductId");
        sellerId = getIntent().getLongExtra("SellerId", 0);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.myProductDetailsToolbar);
        //------show back icon in toolbar--------
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        productName = findViewById(R.id.myProductDetailsProductName);
        productPrice = findViewById(R.id.myProductDetailsPrice);
        productDescription = findViewById(R.id.myProductDetailsDescription);
        productPicture = findViewById(R.id.myProductDetailsPicture);

        editBtn = findViewById(R.id.myProductDetailsEditBtn);
        deleteBtn = findViewById(R.id.myProductDetailsDeleteBtn);

        productName.setText(name);
        productPrice.setText(getResources().getString(R.string.tk_sign) + String.valueOf(price));
        productDescription.setText(description);

        Picasso.get().load(picture).into(productPicture);
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