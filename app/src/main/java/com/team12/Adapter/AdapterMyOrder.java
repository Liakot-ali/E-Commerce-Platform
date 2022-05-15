package com.team12.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team12.Class.ClassMyOrder;
import com.team12.R;

import java.util.ArrayList;

public class AdapterMyOrder extends RecyclerView.Adapter<AdapterMyOrder.ViewHolder> {

    Context context;
    ArrayList<ClassMyOrder> arrayList;

    public AdapterMyOrder(Context context, ArrayList<ClassMyOrder> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView productName, sellerName, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.myOrderProductPicture);
            productName = itemView.findViewById(R.id.myOrderProductName);
            sellerName = itemView.findViewById(R.id.myOrderSellerName);
            price = itemView.findViewById(R.id.myOrderProductPrice);
        }
    }

    @NonNull
    @Override
    public AdapterMyOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyOrder.ViewHolder holder, int position) {

        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        holder.productName.setText(arrayList.get(holder.getAdapterPosition()).getProductName());
        holder.sellerName.setText(arrayList.get(holder.getAdapterPosition()).getSellerName());
        holder.price.setText(arrayList.get(holder.getAdapterPosition()).getProductPrice());
        Picasso.get().load(arrayList.get(holder.getAdapterPosition()).getProductPicture()).into(holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "My Order clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
