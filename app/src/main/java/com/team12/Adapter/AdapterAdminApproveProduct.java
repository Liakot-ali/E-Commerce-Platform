package com.team12.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team12.Admin.AdminApproveProductList;
import com.team12.Admin.AdminApproveProductsDetails;
import com.team12.Class.ClassAddProduct;
import com.team12.Class.ClassSellerProfile;
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
        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        holder.name.setText(arrayList.get(holder.getAdapterPosition()).getName());
        holder.type.setText(arrayList.get(holder.getAdapterPosition()).getType());
        holder.price.setText(context.getResources().getString(R.string.tk_sign) + arrayList.get(holder.getAdapterPosition()).getPrice());
        if(arrayList.get(holder.getAdapterPosition()).getImage() != null){
            Picasso.get().load(arrayList.get(position).getImage()).into(holder.picture);
        }else{
            holder.picture.setImageResource(R.drawable.ic_product_demo_photo_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminApproveProductsDetails.class);
                intent.putExtra("ProductId", arrayList.get(holder.getAdapterPosition()).getProductId());
                intent.putExtra("ProductName", arrayList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("ProductPrice", arrayList.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("Description", arrayList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("ProductPicture", arrayList.get(holder.getAdapterPosition()).getImage());

                intent.putExtra("SellerId", arrayList.get(holder.getAdapterPosition()).getSellerId());
                context.startActivity(intent);
            }
        });

        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                String productName, productPicture, description,  productId;
                long productPrice, sellerId;
                productName = arrayList.get(holder.getAdapterPosition()).getName();
                productPicture = arrayList.get(holder.getAdapterPosition()).getImage();
                description = arrayList.get(holder.getAdapterPosition()).getDescription();
                productId = arrayList.get(holder.getAdapterPosition()).getProductId();
                productPrice = arrayList.get(holder.getAdapterPosition()).getPrice();

                sellerId = arrayList.get(holder.getAdapterPosition()).getSellerId();

                DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
                sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ClassSellerProfile profile = snapshot.getValue(ClassSellerProfile.class);
                        assert profile != null;
                        ClassAddProduct newProduct = new ClassAddProduct(productId, productName, description, productPicture, productPrice, profile.getName(), sellerId);

                        //TODO------send a notification to the seller------
                        DatabaseReference adminRef = database.getReference("Admin").child("ProductApprove").child(productId);
                        DatabaseReference sellerRef = database.getReference("Seller").child(String.valueOf(sellerId)).child("MyProduct").child(productId);
                        DatabaseReference productRef = database.getReference("Product").child(productId);
                        DatabaseReference notiRef = database.getReference("User").child(profile.getUserId()).child("Notification").child("SellingNotification");

                        productRef.setValue(newProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    arrayList.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    adminRef.removeValue();
                                    sellerRef.setValue(newProduct);
                                    Toast.makeText(context, "Product approved", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
