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
import com.team12.Class.ClassSellingNotification;
import com.team12.R;
import com.team12.Seller.ActivityCustomerDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterMySelling extends RecyclerView.Adapter<AdapterMySelling.ViewHolder> {

    ArrayList<ClassSellingNotification> arrayList;
    Context context;

    public AdapterMySelling(Context context, ArrayList<ClassSellingNotification> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView productName, cusName, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.mySellingProductPicture);
            productName = itemView.findViewById(R.id.mySellingProductName);
            cusName = itemView.findViewById(R.id.mySellingCustomerName);
            price = itemView.findViewById(R.id.mySellingProductPrice);
        }
    }

    @NonNull
    @Override
    public AdapterMySelling.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_selling, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMySelling.ViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));
        holder.productName.setText(arrayList.get(holder.getAdapterPosition()).getProductName());
        holder.cusName.setText(arrayList.get(holder.getAdapterPosition()).getCustomerName());
        holder.price.setText(context.getResources().getString(R.string.tk_sign) + arrayList.get(holder.getAdapterPosition()).getProductPrice());
        Picasso.get().load(arrayList.get(holder.getAdapterPosition()).getProductPicture()).into(holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                intent1.putExtra("SellingId", arrayList.get(holder.getAdapterPosition()).getSellingId());
                intent1.putExtra("PassCode", "MySelling");
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
