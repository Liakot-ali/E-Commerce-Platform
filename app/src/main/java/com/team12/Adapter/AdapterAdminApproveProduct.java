package com.team12.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team12.Class.ClassAddProduct;
import com.team12.R;

import java.util.ArrayList;

public class AdapterAdminApproveProduct extends RecyclerView.Adapter<AdapterAdminApproveProduct.ViewHolder> {

    ArrayList<ClassAddProduct> arrayList;
    Context context;

    public AdapterAdminApproveProduct(Context context, ArrayList<ClassAddProduct> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, price, type;
        ImageView picture;
        Button approveBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.approveProductName);
            price = view.findViewById(R.id.approveProductPrice);
            type = view.findViewById(R.id.approveProductType);
            picture = view.findViewById(R.id.approveProductPicture);
            approveBtn = view.findViewById(R.id.approveProductBtn);
        }
    }
    @NonNull
    @Override
    public AdapterAdminApproveProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_approve_product, viewGroup, false);
        return new AdapterAdminApproveProduct.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminApproveProduct.ViewHolder holder, int position) {

        //--TODO---add type(new or edited) for every product-----------
        holder.itemView.setTag(arrayList.get(position));
        holder.name.setText(arrayList.get(position).getName());
        holder.type.setText(arrayList.get(position).getType());
        holder.price.setText(context.getResources().getString(R.string.tk_sign) + arrayList.get(position).getPrice());
        if(arrayList.get(position).getImage() != null){
            Picasso.get().load(arrayList.get(position).getImage()).into(holder.picture);
        }else{
            holder.picture.setImageResource(R.drawable.ic_product_demo_photo_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Approve btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
