package com.team12;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ActivitySellerApplicationForm extends AppCompatActivity {

    private static final int IMAGE_PIC = 1;
    String EMAIL_VALIDITY_CHECK = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Toolbar toolbar;
    EditText SellerName, SellerAddress, SellerPhone, SellerEmail, Description;
    CheckBox checkBox;
    ImageView SellerPicture;
    Button Submit, addPicture;

    String name, phone, email, address, picture, description, userId, type;
    long sellerId;
    Uri imageUri = null;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_application_form);

        InitializeAll();
        OnClick();

    }

    private void OnClick() {

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit.setEnabled(checkBox.isChecked());
            }
        });
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PIC);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO---check the validity and store the image in the storage and update database---------
                name = SellerName.getText().toString();
                phone = SellerPhone.getText().toString();
                address = SellerAddress.getText().toString();
                email = SellerEmail.getText().toString();
                description = Description.getText().toString();

                long number = 2000000000;
                if (!phone.isEmpty() && !phone.contains("%") && !phone.contains(":") && !phone.contains(" ") && !phone.contains(".")) {
                    number = Long.parseLong(phone);
                }

                if (name.isEmpty()) {
                    SellerName.setError("Name is empty");
                    SellerName.requestFocus();
                } else if(phone.isEmpty()){
                    SellerPhone.setError("Phone is empty");
                    SellerPhone.requestFocus();
                }
                else if (phone.length() < 10 || phone.length() > 11 || number > 1999999999 || number < 999999999) {
                    SellerPhone.setError("Invalid phone number");
                    SellerPhone.requestFocus();
                } else if (address.isEmpty()) {
                    SellerAddress.setError("Invalid address");
                    SellerAddress.requestFocus();
                } else if (email.isEmpty()) {
                    SellerEmail.setError("Email is empty");
                    SellerEmail.requestFocus();
                } else if (!email.matches(EMAIL_VALIDITY_CHECK)) {
                    SellerEmail.setError("Invalid email");
                    SellerEmail.requestFocus();
                } else if (description.isEmpty()) {
                    Description.setError("Description is empty");
                    Description.requestFocus();
                } else {
                    //------generate random sellerId between (1-1000000)--------
                    if (sellerId == 0) {
                        sellerId = (long) (Math.random() * 1000000 + 1);
                    }

                    //-------store the application form data to the admin section and change the sellerId in user Section-------
                    DatabaseReference adminRef = database.getReference("Admin").child("SellerApproval").child(String.valueOf(sellerId));
                    ClassSellerProfile sellerReq = new ClassSellerProfile();
                    if (type == null) {
                        sellerReq = new ClassSellerProfile(sellerId, name, null, phone, email, userId, address, description, "new");
                    } else {
                        sellerReq = new ClassSellerProfile(sellerId, name, null, phone, email, userId, address, description, "edit");
                    }
                    adminRef.setValue(sellerReq).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            DatabaseReference userRef = database.getReference("User").child(userId).child("Profile").child("sellerId");
                            userRef.setValue(sellerId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ActivitySellerApplicationForm.this, "Your seller request has been successfully submitted to admin", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ActivitySellerApplicationForm.this, ActivityHome.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        finish();
                                        SellerName.setText("");
                                        SellerEmail.setText("");
                                        SellerPhone.setText("");
                                        SellerAddress.setText("");
                                        Description.setText("");
                                        SellerPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);

                                    } else {
                                        Toast.makeText(ActivitySellerApplicationForm.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void InitializeAll() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        toolbar = findViewById(R.id.formToolbar);
        SellerPicture = findViewById(R.id.sellerPicture);
        SellerName = findViewById(R.id.SellerName);
        SellerAddress = findViewById(R.id.SellerAddress);
        SellerPhone = findViewById(R.id.SellerPhone);
        SellerEmail = findViewById(R.id.SellerEmail);
        Description = findViewById(R.id.Description);
        checkBox = findViewById(R.id.SellerCheckBox);
        Submit = findViewById(R.id.Submit);
        addPicture = findViewById(R.id.SellerAddPicturebBtn);

        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        address = getIntent().getStringExtra("address");
        picture = getIntent().getStringExtra("picture");
        description = getIntent().getStringExtra("description");
        type = getIntent().getStringExtra("type");
        userId = getIntent().getStringExtra("userId");
        sellerId = getIntent().getLongExtra("sellerId", 0);

        SellerName.setText(name);
        SellerPhone.setText(phone);
        SellerEmail.setText(email);
        SellerAddress.setText(address);
        Description.setText(description);
        if (picture != null) {
            Picasso.get().load(picture).into(SellerPicture);
        } else {
            SellerPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);
        }
        Submit.setEnabled(checkBox.isChecked());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PIC && resultCode == Activity.RESULT_OK && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(SellerPicture);
        } else {
            imageUri = null;
        }
    }
}