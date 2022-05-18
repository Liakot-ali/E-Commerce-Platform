package com.team12.General;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team12.Adapter.AdapterSellerNotification;
import com.team12.Class.ClassSellingNotification;
import com.team12.R;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentSeller extends Fragment {

    RecyclerView recyclerView;
    TextView emptyText;
    ProgressBar progressBar;

    RecyclerView.LayoutManager manager;
    AdapterSellerNotification adapter;

    ArrayList<ClassSellingNotification> arrayList;

    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String userId = auth.getUid();

        recyclerView = view.findViewById(R.id.sellerNotificationList);
        emptyText = view.findViewById(R.id.sellerNotificationEmptyText);
        progressBar = view.findViewById(R.id.sellerNotificationProgressBar);

        manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        arrayList = new ArrayList<>();

        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        assert userId != null;
        DatabaseReference sellerNotiRef = database.getReference("User").child(userId).child("Notification").child("SellingNotification");

        sellerNotiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    ClassSellingNotification newNoti = snap.getValue(ClassSellingNotification.class);
                    assert newNoti != null;
                    ClassSellingNotification upNoti = new ClassSellingNotification(newNoti, snap.getKey());
                    arrayList.add(upNoti);
                }
                Collections.reverse(arrayList);
                progressBar.setVisibility(View.GONE);
                if(arrayList.size() != 0){
                    emptyText.setVisibility(View.GONE);
                }else{
                    emptyText.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new AdapterSellerNotification(arrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}