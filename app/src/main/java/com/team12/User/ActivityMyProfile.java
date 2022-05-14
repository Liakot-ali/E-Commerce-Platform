package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
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
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassUserProfile;
import com.team12.R;
import com.team12.Seller.ActivitySellerProfile;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMyProfile extends AppCompatActivity {

    //done by done
    /**
     * Tag to use to {@link Log} messages
     */
    private static final String TAG = "My Profile";

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String nameUs,emailUs;

    //done by  done


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

        //done by jannat
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();



        DatabaseReference MyProfileRef = database.getReference("User").child(String.valueOf(uid)).child("Profile");
        MyProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassUserProfile UserProfile = snapshot.getValue(ClassUserProfile.class);
                assert UserProfile != null;
                nameUs = UserProfile.getName();
                emailUs = UserProfile.getEmail();

                Name.setText(nameUs);
              //  SellerPhone.setText(phoneSt);
                Email.setText(emailUs);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityMyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        //done by jannat


    }


    //for initialize everything

    private void initialiozationall() {


        //done by jannat


        //--------show back icon in toolbar----------
        MyProfileToolber = findViewById(R.id.myProfileToolbar);
        setSupportActionBar(MyProfileToolber);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //done by jannat


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
   //----for back previous activity--------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}