package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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
import com.team12.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMyProfile extends AppCompatActivity {

    //done by done
    /**
     * Tag to use to {@link Log} messages
     */
    private static final String TAG = "My Profile";

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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

        //done by done
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name = "" + dataSnapshot1.child("name").getValue();
                    String emaill = "" + dataSnapshot1.child("email").getValue();
                   // String image = "" + dataSnapshot1.child("image").getValue();
                    // setting data to our text view
                    Name.setText(name);
                    Email.setText(emaill);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    //done by done


    }


    //for initialize everything

    private void initialiozationall() {


        //done by done
        firebaseAuth = FirebaseAuth.getInstance();

        // getting current user data
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        //done by done

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