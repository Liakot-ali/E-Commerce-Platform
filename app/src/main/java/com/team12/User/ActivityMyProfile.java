package com.team12.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassUserProfile;
import com.team12.R;
import com.team12.Seller.ActivityMyProduct;
import com.team12.Seller.ActivityMySelling;
import com.team12.Seller.ActivitySellerProfile;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMyProfile extends AppCompatActivity {


    final static int PICK_IMAGE = 10;
    Uri userPictureUri = null;

    FirebaseAuth mAuth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    String preName, prePhone, preAddress, prePicture, emailUs;
    long sellerId;
    String uid;
    boolean changePicture = false;


    Toolbar MyProfileToolber;
    TextView myProfile;
    TextInputEditText Name, Phone, Email, Address;
    TextInputLayout NameLayout, PhoneLayout, EmailLayout, AddressLayout;
    CircleImageView MyPicture, AddPicture;
    Button Update, MyOrder;
    LinearLayout sellerLayout;
    Button mySellingBtn, myProductBtn;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initializationAll();
        OnClick();

    }

    private void OnClick() {

        //------------for clicked-----------
        AddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameus, phoneus, addressus;
                nameus = Name.getText().toString();
                phoneus = Phone.getText().toString();
                addressus = Address.getText().toString();
                if(nameus.equals(preName) && addressus.equals(preAddress) && phoneus.equals(prePhone) && !changePicture){
                    Toast.makeText(ActivityMyProfile.this, "Changes nothing", Toast.LENGTH_SHORT).show();
                }else {


                    //-----TODO---after checking the validity--------
                    dialog.show();
                    DatabaseReference profileRef = database.getReference("User").child(uid).child("Profile");

                    if (userPictureUri != null) {
                        StorageReference sRef = FirebaseStorage.getInstance().getReference("UserPicture").child(uid);

                        sRef.putFile(userPictureUri).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                sRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    ClassUserProfile upProfile = new ClassUserProfile(uid, nameus, uri.toString(), phoneus, emailUs, addressus, (int) sellerId);
                                    profileRef.setValue(upProfile).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(ActivityMyProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                            Name.clearFocus();
                                            Phone.clearFocus();
                                            Address.clearFocus();
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(ActivityMyProfile.this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                });
                            } else {
                                dialog.dismiss();
                                Toast.makeText(ActivityMyProfile.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        ClassUserProfile upProfile = new ClassUserProfile(uid, nameus, prePicture, phoneus, emailUs, addressus, (int) sellerId);
                        profileRef.setValue(upProfile).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(ActivityMyProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                Name.clearFocus();
                                Phone.clearFocus();
                                Address.clearFocus();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(ActivityMyProfile.this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

        MyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyProfile.this, ActivityMyOrder.class);
                intent.putExtra("UserId", uid);
                startActivity(intent);
            }
        });

        myProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyProfile.this, ActivityMyProduct.class);
                intent.putExtra("SellerId", sellerId);
                startActivity(intent);
            }
        });

        mySellingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyProfile.this, ActivityMySelling.class);
                intent.putExtra("SellerId", sellerId);
                startActivity(intent);
            }
        });
    }

    private void initializationAll() {

        //----------done by jannat---------
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        sellerId = getIntent().getLongExtra("SellerId", 0);

        //--------show back icon in toolbar----------
        MyProfileToolber = findViewById(R.id.myProfileToolbar);
        setSupportActionBar(MyProfileToolber);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myProfile = findViewById(R.id.MyProfile);
        Name = findViewById(R.id.myProfileName);
        NameLayout = findViewById(R.id.myProfileNameLayout);
        Phone = findViewById(R.id.myProfilePhoneNo);
        PhoneLayout = findViewById(R.id.myProfilePhoneNoLayout);
        Email = findViewById(R.id.myProfileEmail);
        EmailLayout = findViewById(R.id.myProfileEmailLayout);
        Address = findViewById(R.id.myProfileAddress);
        AddressLayout = findViewById(R.id.myProfileAddressLayout);
        MyPicture = findViewById(R.id.myProfilePicture);
        AddPicture = findViewById(R.id.myProfilePictureAdd);
        Update = findViewById(R.id.myProfileUpdateBtn);
        MyOrder = findViewById(R.id.myProfileOrderBtn);

        sellerLayout = findViewById(R.id.myProfileSellerLayout);
        myProductBtn = findViewById(R.id.myProfileMyProductBtn);
        mySellingBtn = findViewById(R.id.myProfileMySellingBtn);

        if (sellerId >= 10000) {
            sellerLayout.setVisibility(View.VISIBLE);
        } else {
            sellerLayout.setVisibility(View.GONE);
        }

        //-----------one by jannat for retrieve data from firebase-------
        uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference MyProfileRef = database.getReference("User").child(String.valueOf(uid)).child("Profile");
        MyProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassUserProfile UserProfile = snapshot.getValue(ClassUserProfile.class);
                assert UserProfile != null;
                preName = UserProfile.getName();
                emailUs = UserProfile.getEmail();
                prePhone = UserProfile.getPhone();
                preAddress = UserProfile.getAddress();
                prePicture = UserProfile.getPicture();

                Name.setText(preName);
                Phone.setText(prePhone);
                Email.setText(emailUs);
                Address.setText(preAddress);
                if(prePicture != null){
                    Picasso.get().load(prePicture).into(MyPicture);
                }else{
                    MyPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityMyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog = new ProgressDialog(ActivityMyProfile.this);
        dialog.setTitle("Please wait..");
        dialog.setMessage("Your profile is updating..");
        dialog.setCancelable(false);

    }

    //--------For add the image in the imageView----------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data.getData() != null) {
            userPictureUri = data.getData();
            Picasso.get().load(userPictureUri).into(MyPicture);
            changePicture = true;
        } else {
            userPictureUri = null;
            changePicture = false;
        }
    }

    //----for back previous activity--------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}