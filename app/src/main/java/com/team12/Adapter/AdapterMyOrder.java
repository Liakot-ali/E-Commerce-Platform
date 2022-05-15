package com.team12.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.team12.User.ActivityMyOrderDetails;

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
        holder.sellerName.setText("Seller " + arrayList.get(holder.getAdapterPosition()).getSellerName());
        holder.price.setText(arrayList.get(holder.getAdapterPosition()).getProductPrice());
        Picasso.get().load(arrayList.get(holder.getAdapterPosition()).getProductPicture()).into(holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityMyOrderDetails.class);
                intent.putExtra("ProductPicture", arrayList.get(holder.getAdapterPosition()).getProductPicture());
                intent.putExtra("ProductName", arrayList.get(holder.getAdapterPosition()).getProductName());
                intent.putExtra("ProductPrice", arrayList.get(holder.getAdapterPosition()).getProductPrice());
                intent.putExtra("SellerName", arrayList.get(holder.getAdapterPosition()).getSellerName());
                intent.putExtra("SellerPhone", arrayList.get(holder.getAdapterPosition()).getSellerPhone());
                intent.putExtra("SellerEmail", arrayList.get(holder.getAdapterPosition()).getSellerEmail());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
