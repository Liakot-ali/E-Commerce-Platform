package com.team12.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.Context;
import com.team12.Class.ClassBuyingNotification;
import com.team12.Class.ClassSellingNotification;

import java.util.ArrayList;

public class AdapterSellerNotification extends RecyclerView.Adapter<AdapterSellerNotification.ViewHolder> {

    ArrayList<ClassSellingNotification> arrayList;
    Context context;

    public AdapterSellerNotification(Context context, ArrayList<ClassSellingNotification> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public AdapterSellerNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSellerNotification.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
