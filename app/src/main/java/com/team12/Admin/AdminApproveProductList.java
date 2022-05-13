package com.team12.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterAdminApproveProduct;
import com.team12.Class.ClassAddProduct;
import com.team12.R;

import java.util.ArrayList;

public class AdminApproveProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyText;
    ProgressBar progressBar;

    AdapterAdminApproveProduct adapter;
    ArrayList<ClassAddProduct> arrayList;
    RecyclerView.LayoutManager manager;

    FirebaseDatabase database;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approve_product_list);
        InitializeAll();
    }

    private void InitializeAll() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.approveProductRecyclerView);
        emptyText = findViewById(R.id.approveProductEmptyText);
        progressBar = findViewById(R.id.approveProductProgressBar);

        manager = new LinearLayoutManager(AdminApproveProductList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        progressBar.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);

        DatabaseReference approveProductRef = database.getReference("Admin").child("ProductApprove");

        approveProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    ClassAddProduct product = snap.getValue(ClassAddProduct.class);
                    arrayList.add(product);
                }
                progressBar.setVisibility(View.GONE);
                if(arrayList.size() != 0){
                    emptyText.setVisibility(View.GONE);
                }else {
                    emptyText.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminApproveProductList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new AdapterAdminApproveProduct(AdminApproveProductList.this, arrayList);
        recyclerView.setAdapter(adapter);

    }
}