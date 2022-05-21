package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterMyOrder;
import com.team12.Adapter.AdapterMyProduct;
import com.team12.Class.ClassAddProduct;
import com.team12.R;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityMyProduct extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText, emptyText;
    ImageView search;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    AdapterMyProduct adapter;
    ArrayList<ClassAddProduct> arrayList;
    long sellerId;


    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);
        InitializeAll();

    }
    private void InitializeAll(){

        sellerId = getIntent().getLongExtra("SellerId", 0);
        toolbar = findViewById(R.id.myProductToolbar);

        //-------show back button in toolbar--------
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbarText = findViewById(R.id.myProductToolbarText);
        search = findViewById(R.id.myProductSearch);
        emptyText = findViewById(R.id.myProductEmptyText);
        recyclerView = findViewById(R.id.myProductRecyclerView);
        progressBar = findViewById(R.id.myProductProgressBar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMyProduct.this));

        arrayList = new ArrayList<>();
        adapter = new AdapterMyProduct(ActivityMyProduct.this, arrayList);
        recyclerView.setAdapter(adapter);

        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference myProductRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MyProduct");
        myProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    DatabaseReference productRef = database.getReference("Product").child(Objects.requireNonNull(snap.getKey()));
                    productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ClassAddProduct product = snapshot.getValue(ClassAddProduct.class);
                            arrayList.add(product);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ActivityMyProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if(arrayList.size() != 0){
                    emptyText.setVisibility(View.GONE);
                }else{
                    emptyText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityMyProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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