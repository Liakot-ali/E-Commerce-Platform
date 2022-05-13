package com.team12.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.Context;
import com.team12.Class.ClassBuyingNotification;
import com.team12.R;

import java.util.ArrayList;

public class AdapterBuyerNotification extends RecyclerView.Adapter<AdapterBuyerNotification.ViewHolder> {

    ArrayList<ClassBuyingNotification> arrayList;

    public AdapterBuyerNotification( ArrayList<ClassBuyingNotification> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.notificationItemText);
        }
    }

    @NonNull
    @Override
    public AdapterBuyerNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.item_buyer_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBuyerNotification.ViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        String textSt = new ClassBuyingNotification(arrayList.get(position).getSellerName(), arrayList.get(position).getProductName()).toString();
        holder.text.setText(textSt);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
