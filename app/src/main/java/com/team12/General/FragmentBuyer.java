package com.team12.General;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.team12.Adapter.AdapterBuyerNotification;
import com.team12.Class.ClassBuyingNotification;
import com.team12.R;
import com.team12.User.ActivityNotification;

import java.util.ArrayList;


public class FragmentBuyer extends Fragment {

    RecyclerView recyclerView;
    TextView emptyText;
    ProgressBar progressBar;

    AdapterBuyerNotification adapter;
    ArrayList<ClassBuyingNotification> arrayList;
    RecyclerView.LayoutManager manager;

    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String userId = auth.getUid();
        arrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.buyerNotificationList);
        emptyText = view.findViewById(R.id.buyerNotificationEmptyText);
        progressBar = view.findViewById(R.id.buyerNotificationProgressBar);

        manager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        adapter = new AdapterBuyerNotification(arrayList);
        recyclerView.setAdapter(adapter);

        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        assert userId != null;
        DatabaseReference notiRef = database.getReference("User").child(userId).child("Notification").child("BuyingNotification");
        notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    ClassBuyingNotification newNoti = snap.getValue(ClassBuyingNotification.class);
                    arrayList.add(newNoti);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if(arrayList.size() != 0){
                    emptyText.setVisibility(View.GONE);
                }else{
                    emptyText.setVisibility(View.VISIBLE);
                }
                Log.e("ArraySize", "onDataChange: " + "size " + arrayList.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}