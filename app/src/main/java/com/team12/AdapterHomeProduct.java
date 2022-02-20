package com.team12;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHomeProduct extends RecyclerView.Adapter<AdapterHomeProduct.ViewHolder> {

    ArrayList<ClassAddProduct> arrayList;
    Context activityContext;

    public AdapterHomeProduct (Context context, ArrayList<ClassAddProduct> list) {

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
        holder.price.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.sellerName.setText("Seller Name");
        Picasso.get().load(arrayList.get(position).getImage()).into(holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference sellerProfileRef = FirebaseDatabase.getInstance().getReference("Seller").child(String.valueOf(arrayList.get(position).getSellerId()));
                //TODO----get the seller information from firebase--------
                sellerProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(activityContext, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
