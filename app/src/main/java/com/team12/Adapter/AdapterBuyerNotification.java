package com.team12.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.Context;
import com.team12.Class.ClassBuyingNotification;
import com.team12.R;
import com.team12.Seller.ActivitySellerApplicationForm;
import com.team12.User.ActivityMyOrderDetails;

import java.util.ArrayList;

public class AdapterBuyerNotification extends RecyclerView.Adapter<AdapterBuyerNotification.ViewHolder> {

    ArrayList<ClassBuyingNotification> arrayList;

    public AdapterBuyerNotification(ArrayList<ClassBuyingNotification> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.notificationItemText);
        }
    }

    @NonNull
    @Override
    public AdapterBuyerNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.item_buyer_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBuyerNotification.ViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(holder.getAdapterPosition()));

        String notiTag = arrayList.get(holder.getAdapterPosition()).getTag();
        String notiInfo ;
        switch (notiTag) {
            case "ConfirmOrder":
            case "SellerResponse":
                notiInfo = arrayList.get(holder.getAdapterPosition()).getProductName();
                break;
            case "ApplySeller":
            case "DenySeller":
                notiInfo = arrayList.get(holder.getAdapterPosition()).getUserId();
                break;
            case "ApproveSeller":
                notiInfo = arrayList.get(holder.getAdapterPosition()).getSellerId();
                break;
            default:
                notiInfo = "Nothing";
                break;
        }
        String textSt = new ClassBuyingNotification(notiTag, notiInfo).toString();
        holder.text.setText(textSt);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.text.setTextColor(v.getContext().getResources().getColor(R.color.black_gray));
                switch (notiTag) {
                    case "ConfirmOrder":
                    case "SellerResponse":
                        Intent intent = new Intent(v.getContext(), ActivityMyOrderDetails.class);
                        intent.putExtra("ProductPicture", arrayList.get(holder.getAdapterPosition()).getProductPicture());
                        intent.putExtra("ProductName", arrayList.get(holder.getAdapterPosition()).getProductName());
                        intent.putExtra("ProductPrice", arrayList.get(holder.getAdapterPosition()).getProductPrice());
                        intent.putExtra("SellerName", arrayList.get(holder.getAdapterPosition()).getSellerName());
                        intent.putExtra("SellerPhone", arrayList.get(holder.getAdapterPosition()).getSellerPhone());
                        intent.putExtra("SellerEmail", arrayList.get(holder.getAdapterPosition()).getSellerEmail());
                        v.getContext().startActivity(intent);
                        break;
                    case "ApplySeller":
                    case "DenySeller":
                        Intent intent1 = new Intent(v.getContext(), ActivitySellerApplicationForm.class);
                        intent1.putExtra("sellerId", arrayList.get(holder.getAdapterPosition()).getSellerId());
                        intent1.putExtra("userId", arrayList.get(holder.getAdapterPosition()).getUserId());
                        intent1.putExtra("name", arrayList.get(holder.getAdapterPosition()).getSellerName());
                        intent1.putExtra("phone", arrayList.get(holder.getAdapterPosition()).getSellerPhone());
                        intent1.putExtra("email", arrayList.get(holder.getAdapterPosition()).getSellerEmail());
                        intent1.putExtra("address", arrayList.get(holder.getAdapterPosition()).getAddress());
                        intent1.putExtra("picture", arrayList.get(holder.getAdapterPosition()).getSellerPicture());
                        intent1.putExtra("description", arrayList.get(holder.getAdapterPosition()).getDescription());
                        intent1.putExtra("type", arrayList.get(holder.getAdapterPosition()).getType());
                        v.getContext().startActivity(intent1);
                        break;
                    case "ApproveSeller":
                        Toast.makeText(v.getContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
