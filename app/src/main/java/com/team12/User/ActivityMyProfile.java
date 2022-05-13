package com.team12.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.FileObserver;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.team12.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMyProfile extends AppCompatActivity {


    Toolbar MyProfileToolber;
    TextView myProfile;
    TextInputEditText Name ,Phone,Email,Address;
    TextInputLayout NameLayout,PhoneLayout,EmailLayout,AddressLayout;
    CircleImageView MyPicture,AddPicture;
    Button Update,MyOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initialiozationall();

    }


    //for initialize everything

    private void initialiozationall() {

        MyProfileToolber = findViewById(R.id.myProfileToolbar);
        myProfile =findViewById(R.id.MyProfile);
        Name=findViewById(R.id.myProfileName);
        NameLayout= findViewById(R.id.myProfileNameLayout);
        Phone=findViewById(R.id.myProfilePhoneNo);
        PhoneLayout= findViewById(R.id.myProfilePhoneNoLayout);
        Email=findViewById(R.id.myProfileEmail);
        EmailLayout= findViewById(R.id.myProfileEmailLayout);
        Address=findViewById(R.id.myProfileAddress);
        AddressLayout= findViewById(R.id.myProfileAddressLayout);
        MyPicture =findViewById(R.id.myProfilePicture);
        AddPicture=findViewById(R.id.myProfilePictureAdd);
        Update=findViewById(R.id.myProfileUpdateBtn);
        MyOrder=findViewById(R.id.myProfileOrderBtn);
    }
}