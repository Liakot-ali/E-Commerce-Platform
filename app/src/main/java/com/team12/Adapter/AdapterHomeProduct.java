package com.team12.Adapter;

import android.annotation.SuppressLint;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.Class.ClassSellerProfile;
import com.team12.User.ActivityProductDetails;
import com.team12.Class.ClassAddProduct;
import com.team12.R;

import java.util.ArrayList;

public class AdapterHomeProduct extends RecyclerView.Adapter<AdapterHomeProduct.ViewHolder> {

    ArrayList<ClassAddProduct> arrayList;
    Context activityContext;

    public AdapterHomeProduct(Context context, ArrayList<ClassAddProduct> list) {

        activityContext = context;
        arrayList = list;
    }

    //------To hold every list item view------
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, sellerName;
        ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.homeProductName);
            price = itemView.findViewById(R.id.homeProductPrice);
            sellerName = itemView.findViewById(R.id.homeSellerName);
            picture = itemView.findViewById(R.id.homeProductPicture);

        }
    }

    @NonNull
    @Override
    public AdapterHomeProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.item_home_product, viewGroup, false);

        return new AdapterHomeProduct.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomeProduct.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemView.setTag(arrayList.get(position));

        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(activityContext.getResources().getString(R.string.tk_sign) + arrayList.get(position).getPrice());
        holder.sellerName.setText(arrayList.get(position).getSellerName());
        Picasso.get().load(arrayList.get(position).getImage()).into(holder.picture);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activityContext, ActivityProductDetails.class);
            intent.putExtra("productId", arrayList.get(position).getProductId());
            intent.putExtra("productPicture", arrayList.get(position).getImage());
            intent.putExtra("productName", arrayList.get(position).getName());
            intent.putExtra("productPrice", arrayList.get(position).getPrice());
            intent.putExtra("productDescription", arrayList.get(position).getDescription());

            intent.putExtra("sellerName", arrayList.get(position).getSellerName());
            intent.putExtra("sellerId", arrayList.get(position).getSellerId());
            activityContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
