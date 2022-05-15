package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterMyOrder;
import com.team12.Class.ClassMyOrder;
import com.team12.R;

import java.util.ArrayList;

public class ActivityMyOrder extends AppCompatActivity {
    Toolbar toolbar;

    ProgressBar progressBar;
    TextView orderEmptyText;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager manager;
    AdapterMyOrder adapter;

    ArrayList<ClassMyOrder> arrayList;
    FirebaseDatabase database;
    FirebaseAuth auth;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        InitializeAll();
    }
    private void InitializeAll(){
        userId = getIntent().getStringExtra("UserId");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //-----------show back button in the toolbar----------
        toolbar = findViewById(R.id.myOrderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recyclerView = findViewById(R.id.myOrderRecyclerView);
        progressBar = findViewById(R.id.myOrderProgressBar);
        orderEmptyText = findViewById(R.id.myOrderTextView);

        manager = new LinearLayoutManager(ActivityMyOrder.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        arrayList = new ArrayList<>();
        orderEmptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference orderRef = database.getReference("User").child(userId).child("MyOrders");
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    ClassMyOrder order = snap.getValue(ClassMyOrder.class);
                    arrayList.add(order);
                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                if(arrayList.size() == 0){
                    orderEmptyText.setVisibility(View.VISIBLE);
                }else{
                    orderEmptyText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityMyOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        adapter = new AdapterMyOrder(ActivityMyOrder.this, arrayList);
        recyclerView.setAdapter(adapter);
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