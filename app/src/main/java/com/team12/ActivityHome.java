package com.team12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ActivityHome extends AppCompatActivity {

    TextView emptyText;

    RecyclerView productView;
    FloatingActionButton postProduct;
    FirebaseDatabase database;
    ArrayList<ClassAddProduct> arrayList;
    AdapterHomeProduct adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InitializeAll();
        OnClick();
        ShowData();

        adapter = new AdapterHomeProduct(ActivityHome.this, arrayList);
        productView.setAdapter(adapter);


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
    }

    //---------For all Click-----------
    private void OnClick() {

        postProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityPostProduct.class));

            }
        });
    }

    //------For initialize all things-------------
    private void InitializeAll() {

        //---------firebase initialization
        database = FirebaseDatabase.getInstance();

        //------------Initialize id----------
        productView = findViewById(R.id.homeRecyclerView);
        postProduct = findViewById(R.id.homeAddProductBtn);

        emptyText = findViewById(R.id.homeEmptyText);

        arrayList = new ArrayList<ClassAddProduct>();

        //-----------for recycler view------------
        layoutManager = new GridLayoutManager(ActivityHome.this, 2, RecyclerView.VERTICAL, false);
        productView.setHasFixedSize(true);
        productView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowData();
    }
}