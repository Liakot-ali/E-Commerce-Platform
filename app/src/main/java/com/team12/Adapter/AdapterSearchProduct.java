package com.team12.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team12.Class.ClassAddProduct;
import com.team12.R;
import com.team12.User.ActivityProductDetails;

import java.util.ArrayList;

public class AdapterSearchProduct extends RecyclerView.Adapter<AdapterSearchProduct.ViewHolder> {

    ArrayList<ClassAddProduct> arrayList;
    android.content.Context context;
    public AdapterSearchProduct(Context context, ArrayList<ClassAddProduct> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.searchProductName);
            productPrice = itemView.findViewById(R.id.searchProductPrice);
        }
    }

    @NonNull
    @Override
    public AdapterSearchProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchProduct.ViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        holder.productName.setText(arrayList.get(holder.getAdapterPosition()).getName());
        holder.productPrice.setText(context.getResources().getString(R.string.tk_sign) + arrayList.get(holder.getAdapterPosition()).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityProductDetails.class);
                intent.putExtra("productId", arrayList.get(holder.getAdapterPosition()).getProductId());
                intent.putExtra("productPicture", arrayList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("productName", arrayList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("productPrice", arrayList.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("productDescription", arrayList.get(holder.getAdapterPosition()).getDescription());

                intent.putExtra("sellerName", arrayList.get(holder.getAdapterPosition()).getSellerName());
                intent.putExtra("sellerId", arrayList.get(holder.getAdapterPosition()).getSellerId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void searchList(ArrayList<ClassAddProduct> searchList){
        this.arrayList = searchList;
        notifyDataSetChanged();
    }
}
