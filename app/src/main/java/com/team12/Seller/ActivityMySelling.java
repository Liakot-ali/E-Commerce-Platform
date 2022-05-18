package com.team12.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterMySelling;
import com.team12.Class.ClassSellingNotification;
import com.team12.R;

import java.util.ArrayList;

public class ActivityMySelling extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarText, Emptytext;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    ArrayList<ClassSellingNotification> arrayList;
    AdapterMySelling adapter;

    FirebaseDatabase database;

    long sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_selling);
        InitializeAll();

    }
    private void  InitializeAll(){

        database = FirebaseDatabase.getInstance();

        //-------show back button in toolbar--------
        toolbar = findViewById(R.id.mySellingToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbarText = findViewById(R.id.mySellingToolbarText);
        Emptytext = findViewById(R.id.MySellingEmptyTextView);
        recyclerView = findViewById(R.id.mySellingRecyclerView);
        progressBar = findViewById(R.id.mySellingProgressBar);

        sellerId = getIntent().getLongExtra("SellerId", 0);

        arrayList = new ArrayList<>();
        Emptytext.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMySelling.this));

        adapter = new AdapterMySelling(ActivityMySelling.this, arrayList);
        recyclerView.setAdapter(adapter);


        DatabaseReference sellingRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MySelling");
        sellingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    ClassSellingNotification sell = snap.getValue(ClassSellingNotification.class);
                    arrayList.add(sell);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if(arrayList.size() != 0){
                    Emptytext.setVisibility(View.GONE);
                }else{
                    Emptytext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityMySelling.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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