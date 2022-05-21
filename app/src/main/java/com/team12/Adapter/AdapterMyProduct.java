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
import com.team12.Class.ClassAddProduct;
import com.team12.R;

import java.util.ArrayList;

public class AdapterMyProduct extends RecyclerView.Adapter<AdapterMyProduct.ViewHolder> {

    Context context;
    ArrayList<ClassAddProduct> arrayList;
    public AdapterMyProduct(Context context, ArrayList<ClassAddProduct> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;

        ImageView productPicture, dropDown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.myProductName);
            price = itemView.findViewById(R.id.myProductPrice);
            description = itemView.findViewById(R.id.myProductDescription);
            productPicture = itemView.findViewById(R.id.myProductPicture);
            dropDown = itemView.findViewById(R.id.myProductDropDown);

        }
    }

    @NonNull
    @Override
    public AdapterMyProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_selling, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyProduct.ViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        holder.name.setText(arrayList.get(holder.getAdapterPosition()).getName());
        holder.price.setText((int) arrayList.get(holder.getAdapterPosition()).getPrice());
        holder.description.setText(arrayList.get(holder.getAdapterPosition()).getDescription());

        Picasso.get().load(arrayList.get(holder.getAdapterPosition()).getImage()).into(holder.productPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Drop down clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
