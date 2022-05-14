package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.team12.Seller.ActivityPostProduct;
import com.team12.Seller.ActivitySellerApplicationForm;
import com.team12.Adapter.AdapterHomeProduct;
import com.team12.Authentication.ActivityLogin;
import com.team12.Class.ClassAddProduct;
import com.team12.Class.ClassSellerProfile;
import com.team12.Class.ClassUserProfile;
import com.team12.R;

import java.util.ArrayList;

public class ActivityHome extends AppCompatActivity {

    TextView emptyText, userName;
    Button logInBtn;
    Toolbar userToolbar, nonUserToolbar;
    ImageView notification, userPicture, searchForUser, searchForNonUser;
    FloatingActionButton postProduct;
    RecyclerView productView;
    ProgressBar progressBar;
    LinearLayout progressBarLay;

    ArrayList<ClassAddProduct> arrayList;
    boolean loggedIn = false;
    long sellerId = 0;
    String name, phone, email, address, picture;
    String userId;

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
//                progressBarLay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot snap : snapshot.getChildren()) {
                    ClassAddProduct product = snap.getValue(ClassAddProduct.class);
                    arrayList.add(product);
                }
//                progressBarLay.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (arrayList.size() == 0) {
                    emptyText.setVisibility(View.VISIBLE);
                } else {
                    emptyText.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
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
                mAuth.addAuthStateListener(mAuthListener);
                if (loggedIn) {
                    if (sellerId == 0) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityHome.this);
                        dialog.setTitle("You don't have an seller ID");
                        dialog.setMessage("For posting your product,\nfirst you have to apply for seller.");
                        dialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ActivityHome.this, ActivitySellerApplicationForm.class);
                                intent.putExtra("sellerId", sellerId);
                                intent.putExtra("userId", userId);
                                intent.putExtra("name", name);
                                intent.putExtra("phone", phone);
                                intent.putExtra("email", email);
                                intent.putExtra("address", address);
                                intent.putExtra("picture", picture);
                                intent.putExtra("description", "");
                                startActivity(intent);
                            }
                        });
                        dialog.setNegativeButton("Cancel", null);
                        dialog.setCancelable(true);
                        dialog.show();
                    } else if (sellerId > 0 && sellerId <= 1000000) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityHome.this);
                        dialog.setTitle("Your are not seller yet");
                        dialog.setMessage("Check your notification,\nor edit your seller application form");
                        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference editRef = database.getReference("Admin").child("SellerApproval").child(String.valueOf(sellerId));
                                editRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        ClassSellerProfile editSeller = snapshot.getValue(ClassSellerProfile.class);
                                        Intent intent = new Intent(ActivityHome.this, ActivitySellerApplicationForm.class);
                                        assert editSeller != null;
                                        intent.putExtra("sellerId", sellerId);
                                        intent.putExtra("userId", editSeller.getUserId());
                                        intent.putExtra("name", editSeller.getName());
                                        intent.putExtra("phone", editSeller.getPhone());
                                        intent.putExtra("email", editSeller.getEmail());
                                        intent.putExtra("address", editSeller.getAddress());
                                        intent.putExtra("picture", editSeller.getPicture());
                                        intent.putExtra("description", editSeller.getDescription());
                                        intent.putExtra("type", editSeller.getType());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(ActivityHome.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        dialog.setNegativeButton("Cancel", null);
                        dialog.setCancelable(true);
                        dialog.show();
                    } else if (sellerId > 1000000) {
                        Intent intent = new Intent(ActivityHome.this, ActivityPostProduct.class);
                        intent.putExtra("SellerId", sellerId);
                        startActivity(intent);
                    }
                } else {
                    //------show an alertdialog to login------
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityHome.this);
                    dialog.setTitle("You're not log in yet");
                    dialog.setMessage("Please log in to your account");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("Cancel", null);
                    dialog.show();
                }

            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
                startActivity(intent);
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
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivityMyProfile.class);
                startActivity(intent);
            }
        });
        searchForNonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivitySearch.class);
                startActivity(intent);
            }
        });
        searchForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHome.this, ActivitySearch.class);
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

        progressBar = findViewById(R.id.homeProgressBar);
        progressBarLay = findViewById(R.id.homeProgressBarLay);

        emptyText = findViewById(R.id.homeEmptyText);
        userName = findViewById(R.id.homeUserName);

        setSupportActionBar(userToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        progressBarLay.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);

        //---------check user is logged in or not-----------
        mAuthListener = firebaseAuth -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                nonUserToolbar.setVisibility(View.VISIBLE);
                userToolbar.setVisibility(View.GONE);
                loggedIn = false;
            } else {
                nonUserToolbar.setVisibility(View.GONE);
                userToolbar.setVisibility(View.VISIBLE);
                loggedIn = true;
            }

            if(loggedIn){
                userId = mAuth.getUid();
                assert userId != null;
                DatabaseReference userRef = database.getReference("User").child(userId).child("Profile");
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ClassUserProfile user = snapshot.getValue(ClassUserProfile.class);
                        assert user != null;
                        name = user.getName();
                        phone = user.getPhone();
                        email = user.getEmail();
                        address = user.getAddress();
                        sellerId = user.getSellerId();
                        picture = user.getPicture();

                        userName.setText(name);
                        if (user.getPicture() != null) {
                            Picasso.get().load(picture).into(userPicture);
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
        if (item.getItemId() == R.id.homeMenuLogOut) {
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            mAuth.addAuthStateListener(mAuthListener);
        }
        else if(item.getItemId() == R.id.homeMenuAboutUs){
            Toast.makeText(this, "About us clicked", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.homeMenuFeedback){
            Toast.makeText(this, "Feedback clicked", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.homeMenuHelp){
            Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
        ShowData();
    }

    //-------for pressing double back to exit--------
    long currentTime = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - currentTime < 1000){
            super.onBackPressed();
        }else{
            currentTime = System.currentTimeMillis();
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
    }
}