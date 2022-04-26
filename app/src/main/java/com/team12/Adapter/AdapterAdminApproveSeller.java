package com.team12.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team12.Class.ClassSellerProfile;
import com.team12.R;

import java.util.ArrayList;

public class AdapterAdminApproveSeller extends RecyclerView.Adapter<AdapterAdminApproveSeller.ViewHolder> {

    ArrayList<ClassSellerProfile> list;
    Context context;

    public AdapterAdminApproveSeller(Context context, ArrayList<ClassSellerProfile> arrayList){
        this.context = context;
        this.list = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, phone, type;
        Button approveBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.approveSellerName);
            phone = view.findViewById(R.id.approveSellerPhone);
            type = view.findViewById(R.id.approveSellerType);
            approveBtn = view.findViewById(R.id.approveSellerApproveBtn);
        }
    }
    @NonNull
    @Override
    public AdapterAdminApproveSeller.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_approve_seller, viewGroup, false);
        return new AdapterAdminApproveSeller.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminApproveSeller.ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(holder.getAdapterPosition()));

        holder.name.setText(list.get(holder.getAdapterPosition()).getName());
        holder.phone.setText(list.get(holder.getAdapterPosition()).getPhone());
        holder.type.setText(list.get(holder.getAdapterPosition()).getType());

        holder.itemView.setOnClickListener(v -> Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show());
        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sellerId = 1234567;
                sellerId = (long) (Math.random() * 9999999 + 1000001); //--Generate sellerId (1000001 - 9999999)---------
                Toast.makeText(context, "Approve Clicked", Toast.LENGTH_SHORT).show();
                DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("Admin").child("SellerApproval").child(String.valueOf(list.get(holder.getAdapterPosition()).getSellerId()));
                DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("Seller").child(String.valueOf(sellerId)).child("SellerInfo");
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(list.get(holder.getAdapterPosition()).getUserId()).child("Profile").child("sellerId");


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
