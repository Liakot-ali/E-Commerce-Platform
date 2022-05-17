package com.team12.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterSearchProduct;
import com.team12.Class.ClassAddProduct;
import com.team12.R;

import java.util.ArrayList;
import java.util.Locale;

public class ActivitySearch extends AppCompatActivity {


    EditText Search;
    RecyclerView List;
    TextView emptyText;

    ArrayList<ClassAddProduct> arrayList;
    ArrayList<ClassAddProduct> tempList;
    AdapterSearchProduct adapter;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        InitializeAll();

        tempList = arrayList;
        adapter = new AdapterSearchProduct(ActivitySearch.this, tempList);
        List.setAdapter(adapter);

        Log.e("Activity Search", "Templist size " + tempList.size());

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<ClassAddProduct> filterList;
                filterList = new ArrayList<>();
                for(ClassAddProduct product : arrayList){
                    if(product.getName().toLowerCase().contains(s.toString().toLowerCase())){
                        filterList.add(product);
                    }
                }
                if(filterList.size() != 0){
                    adapter.searchList(filterList);
                    List.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.GONE);
                }else{
                    tempList = null;
                    List.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    private void InitializeAll() {
        Search = findViewById(R.id.searchText);
        List = findViewById(R.id.searchRecycleView);
        emptyText = findViewById(R.id.searchEmptyText);
        emptyText.setVisibility(View.GONE);

        List.setHasFixedSize(true);
        List.setLayoutManager(new LinearLayoutManager(ActivitySearch.this));

        ProgressDialog dialog = new ProgressDialog(ActivitySearch.this);
        dialog.setTitle("Please wait");
        dialog.setMessage("We're working..");
        dialog.setCancelable(false);
        dialog.show();

        arrayList = new ArrayList<>();
        tempList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        DatabaseReference productRef = database.getReference("Product");
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    ClassAddProduct product = snap.getValue(ClassAddProduct.class);
                    arrayList.add(product);
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(ActivitySearch.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("Activity Search", "Arraylist size " + arrayList.size());

    }
}