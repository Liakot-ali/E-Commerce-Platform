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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassBuyingNotification;
import com.team12.Class.ClassSellerProfile;
import com.team12.R;
import com.team12.User.ActivityHome;

import java.util.UUID;

public class ActivitySellerApplicationForm extends AppCompatActivity {

    private static final int IMAGE_PIC = 1;
    String EMAIL_VALIDITY_CHECK = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Toolbar toolbar;
    EditText SellerName, SellerAddress, SellerPhone, SellerEmail, Description;
    CheckBox checkBox;
    ImageView SellerPicture;
    Button Submit, addPicture;
    ProgressDialog dialog;

    String name, phone, email, address, picture, description, userId, type;
    long sellerId;
    Uri imageUri = null;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;

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
                //TODO---store the image in the storage and update database---------
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
                } else if (phone.isEmpty()) {
                    SellerPhone.setError("Phone is empty");
                    SellerPhone.requestFocus();
                } else if (phone.length() < 10 || phone.length() > 11 || number > 1999999999 || number < 999999999) {
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

                    //TODO----check if any seller ID is already register by the email or phone---------
                    //------generate random sellerId between (1-1000000)--------
                    if (sellerId == 0) {
                        sellerId = (long) (Math.random() * 1000000 + 1);
                    }
                    if (imageUri != null) {
                        dialog.show();
                        String id = UUID.randomUUID().toString();
                        StorageReference sRef = storage.getReference("Admin").child("SellerApproval").child(id);
                        sRef.putFile(imageUri).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                sRef.getDownloadUrl().addOnSuccessListener(uri -> {

                                    //-------store the application form data to the admin section and change the sellerId in user Section-------
                                    DatabaseReference adminRef = database.getReference("Admin").child("SellerApproval").child(String.valueOf(sellerId));
                                    ClassSellerProfile sellerReq = new ClassSellerProfile();
                                    ClassBuyingNotification notification ;
                                    if (type == null) {
                                        sellerReq = new ClassSellerProfile(sellerId, name, uri.toString(), phone, email, userId, address, description, "new");
                                        notification = new ClassBuyingNotification(name, phone, email, picture, address, description, String.valueOf(sellerId), userId, "new", "ApplySeller");
                                    } else {
                                        sellerReq = new ClassSellerProfile(sellerId, name, uri.toString(), phone, email, userId, address, description, "edit");
                                        notification = new ClassBuyingNotification(name, phone, email, picture, address, description, String.valueOf(sellerId), userId, "edit", "ApplySeller");
                                    }
                                    adminRef.setValue(sellerReq).addOnCompleteListener(task1 -> {
                                        DatabaseReference userRef = database.getReference("User").child(userId).child("Profile").child("sellerId");
                                        DatabaseReference notiRef = database.getReference("User").child(userId).child("Notification").child("BuyingNotification").child(String.valueOf(System.currentTimeMillis()));
                                        userRef.setValue(sellerId).addOnCompleteListener(task11 -> {
                                            if (task11.isSuccessful()) {
                                                dialog.dismiss();
                                                notiRef.setValue(notification);
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
                                                dialog.dismiss();
                                                Toast.makeText(ActivitySellerApplicationForm.this, task11.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    });
                                });
                            }
                        });
                    } else {
                        dialog.show();

                        //-------store the application form data to the admin section and change the sellerId in user Section-------
                        DatabaseReference adminRef = database.getReference("Admin").child("SellerApproval").child(String.valueOf(sellerId));
                        ClassSellerProfile sellerReq = new ClassSellerProfile();
                        ClassBuyingNotification notification;
                        if (type == null) {
                            notification = new ClassBuyingNotification(name, phone, email, String.valueOf(getResources().getDrawable(R.drawable.ic_demo_profile_picture_24)), address, description, String.valueOf(sellerId), userId, "new", "ApplySeller");
                            sellerReq = new ClassSellerProfile(sellerId, name, null, phone, email, userId, address, description, "new");
                        } else {
                            notification = new ClassBuyingNotification(name, phone, email, null, address, description, String.valueOf(sellerId), userId, "edit", "ApplySeller");
                            sellerReq = new ClassSellerProfile(sellerId, name, String.valueOf(getResources().getDrawable(R.drawable.ic_demo_profile_picture_24)), phone, email, userId, address, description, "edit");
                        }
                        adminRef.setValue(sellerReq).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference userRef = database.getReference("User").child(userId).child("Profile").child("sellerId");
                                DatabaseReference notiRef = database.getReference("User").child(userId).child("Notification").child("BuyingNotification").child(String.valueOf(System.currentTimeMillis()));
                                userRef.setValue(sellerId).addOnCompleteListener(task12 -> {
                                    if (task12.isSuccessful()) {
                                        dialog.dismiss();
                                        notiRef.setValue(notification);
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
                                        dialog.dismiss();
                                        Toast.makeText(ActivitySellerApplicationForm.this, task12.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    private void InitializeAll() {

        dialog = new ProgressDialog(ActivitySellerApplicationForm.this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Your request is now in processing");
        dialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

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