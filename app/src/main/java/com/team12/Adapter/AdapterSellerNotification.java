package com.team12.Adapter;

import static android.os.Build.VERSION_CODES.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.Context;
import com.team12.Class.ClassBuyingNotification;
import com.team12.Class.ClassSellingNotification;
import com.team12.R;

import java.util.ArrayList;

public class AdapterSellerNotification extends RecyclerView.Adapter<AdapterSellerNotification.ViewHolder> {

    ArrayList<ClassSellingNotification> arrayList;

    public AdapterSellerNotification(ArrayList<ClassSellingNotification> arrayList){
        this.arrayList = arrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(com.team12.R.id.notificationItemText);
        }
    }

    @NonNull
    @Override
    public AdapterSellerNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(com.team12.R.layout.item_buyer_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSellerNotification.ViewHolder holder, int position) {

        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        String  type = arrayList.get(position).getType();

        //-TODO-------set the notification text depend on the type------
        String textSt = new ClassSellingNotification(arrayList.get(position).getSellingId(), arrayList.get(position).getProductId()).toString();
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
