package com.team12.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterAdminApproveSeller;
import com.team12.Class.ClassSellerProfile;
import com.team12.R;

import java.util.ArrayList;

public class AdminApproveSellerList extends AppCompatActivity {

    TextView emptyText;
    RecyclerView recyclerView;
    AdapterAdminApproveSeller adapter;
    RecyclerView.LayoutManager manager;

    ArrayList<ClassSellerProfile> arrayList;

    FirebaseDatabase database;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approve_seller_list);
        InitializeAll();
    }

    private void InitializeAll() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.approveSellerRecyclerView);
        emptyText = findViewById(R.id.approveSellerEmptyText);

        manager = new LinearLayoutManager(AdminApproveSellerList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        arrayList = new ArrayList<>();

        emptyText.setVisibility(View.GONE);

        DatabaseReference adminRef = database.getReference("Admin").child("SellerApproval");

        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    ClassSellerProfile seller = snap.getValue(ClassSellerProfile.class);
                    arrayList.add(seller);
                }
                if(arrayList.size() != 0){
                    emptyText.setVisibility(View.GONE);
                }else{
                    emptyText.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminApproveSellerList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new AdapterAdminApproveSeller(AdminApproveSellerList.this, arrayList);
        recyclerView.setAdapter(adapter);

    }
}