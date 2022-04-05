package com.team12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityHome extends AppCompatActivity {

    TextView emptyText, userName;
    Button logInBtn;
    Toolbar userToolbar, nonUserToolbar;
    ImageView notification, userPicture, searchForUser, searchForNonUser;
    FloatingActionButton postProduct;
    RecyclerView productView;

    ArrayList<ClassAddProduct> arrayList;

    AdapterHomeProduct adapter;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InitializeAll();
        ShowData();
        OnClick();

    }

    //-------For fetch value form firebase--------
    private void ShowData() {
        //-------------get product data form firebase------------
        DatabaseReference productRef = database.getReference("Product");
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    ClassAddProduct product = snap.getValue(ClassAddProduct.class);
                    arrayList.add(product);
                }
                adapter.notifyDataSetChanged();
                if(adapter.getItemCount()>0){
                    emptyText.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityHome.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new AdapterHomeProduct(ActivityHome.this, arrayList);
        productView.setAdapter(adapter);

    }

    //---------For all Click-----------
    private void OnClick() {

        postProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityPostProduct.class));

            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityNotification.class);
                startActivity(intent);
            }
        });

        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityMyProfile.class);
                startActivity(intent);
            }
        });
    }

    //------For initialize all things-------------
    private void InitializeAll() {

        //---------firebase initialization
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //------------Initialize id----------
        userToolbar = findViewById(R.id.homeToolbarUser);
        nonUserToolbar = findViewById(R.id.homeToolbarNonUser);
        searchForUser = findViewById(R.id.homeSearchUser);
        searchForNonUser = findViewById(R.id.homeSearchNonUser);

        userPicture = findViewById(R.id.homeProfilePicture);
        notification = findViewById(R.id.homeNotification);

        productView = findViewById(R.id.homeRecyclerView);
        postProduct = findViewById(R.id.homeAddProductBtn);
        logInBtn = findViewById(R.id.homeLogInBtn);


        emptyText = findViewById(R.id.homeEmptyText);
        userName = findViewById(R.id.homeUserName);

        setSupportActionBar(userToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userToolbar.setTitle("");

        //---------check user is logged in or not-----------
        mAuthListener = firebaseAuth -> {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                nonUserToolbar.setVisibility(View.VISIBLE);
                userToolbar.setVisibility(View.GONE);
            }else{
                nonUserToolbar.setVisibility(View.GONE);
                userToolbar.setVisibility(View.VISIBLE);

                String userId = mAuth.getUid();
                assert userId != null;
                DatabaseReference userRef = database.getReference("User").child(userId).child("Profile");
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ClassUserProfile user = snapshot.getValue(ClassUserProfile.class);
                        assert user != null;
                        userName.setText(user.getName());
                        if (user.getPicture() != null) {
                            Picasso.get().load(user.getPicture()).into(userPicture);
                        } else {
                            userPicture.setImageResource(R.drawable.ic_demo_profile_picture_24);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ActivityHome.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };


        arrayList = new ArrayList<ClassAddProduct>();

        //-----------for recycler view------------
        layoutManager = new GridLayoutManager(ActivityHome.this, 2, RecyclerView.VERTICAL, false);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(layoutManager);
    }

    //---------for show the dotted menu in toolbar-------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    //----for selected menu item---------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.homeMenuLogOut){
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            mAuth.addAuthStateListener(mAuthListener);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
        ShowData();
    }
}