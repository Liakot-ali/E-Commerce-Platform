package com.team12.Adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Intent;
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
import com.team12.Seller.ActivityCustomerDetails;
import com.team12.Seller.ActivityPostProduct;
import com.team12.User.ActivityCustomerAddress;

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
        String  tag = arrayList.get(holder.getAdapterPosition()).getTag();
        String text = "Null";
        switch (tag) {
            case "PostProduct":
            case "ApproveProduct":
            case "DenyProduct":
                text = new ClassSellingNotification(arrayList.get(holder.getAdapterPosition()).getProductName(), "", tag).toString();
                break;
            case "ConfirmOrder":
                text = new ClassSellingNotification(arrayList.get(holder.getAdapterPosition()).getProductName(), arrayList.get(holder.getAdapterPosition()).getCustomerName(), tag).toString();
                break;
        }

        //--------set the notification text depend on the type------
        holder.text.setText(text);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tag) {
                    case "PostProduct":
                    case "DenyProduct":
                        Intent intent = new Intent(v.getContext(), ActivityPostProduct.class);
                        intent.putExtra("PassCode", "Admin");
                        intent.putExtra("SellerId", Long.parseLong(arrayList.get(holder.getAdapterPosition()).getSellerId()));
                        intent.putExtra("ProductId", arrayList.get(holder.getAdapterPosition()).getProductId());
                        v.getContext().startActivity(intent);
                        break;
                    case "ConfirmOrder":
                        Intent intent1 = new Intent(v.getContext(), ActivityCustomerDetails.class);
                        intent1.putExtra("ProductName", arrayList.get(holder.getAdapterPosition()).getProductName());
                        intent1.putExtra("ProductPrice", arrayList.get(holder.getAdapterPosition()).getProductPrice());
                        intent1.putExtra("ProductPicture", arrayList.get(holder.getAdapterPosition()).getProductPicture());
                        intent1.putExtra("ProductId", arrayList.get(holder.getAdapterPosition()).getProductId());

                        intent1.putExtra("CustomerName", arrayList.get(holder.getAdapterPosition()).getCustomerName());
                        intent1.putExtra("CustomerPhone", arrayList.get(holder.getAdapterPosition()).getCustomerPhone());
                        intent1.putExtra("CustomerEmail", arrayList.get(holder.getAdapterPosition()).getCustomerEmail());
                        intent1.putExtra("CustomerAddress", arrayList.get(holder.getAdapterPosition()).getCustomerAddress());
                        intent1.putExtra("CustomerNote", arrayList.get(holder.getAdapterPosition()).getCustomerNote());
                        v.getContext().startActivity(intent1);
                        break;
                    case "ApproveProduct":
                        Toast.makeText(v.getContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
