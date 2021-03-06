package com.team12.Class;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassSellerProfile {
    long sellerId;
    String name, picture, phone, email, userId;
    String address, description, type;

    public ClassSellerProfile() {

    }

    public ClassSellerProfile(long id, String t, Context context) {
        if (t.equals("Admin")) {
            DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("Admin").child("SellerApproval").child(String.valueOf(id));
            adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ClassSellerProfile seller = snapshot.getValue(ClassSellerProfile.class);
                    assert seller != null;
                    sellerId = seller.getSellerId();
                    name = seller.getName();
                    picture = seller.getPicture();
                    phone = seller.getPhone();
                    email = seller.getEmail();
                    userId = seller.getUserId();
                    address = seller.getAddress();
                    description = seller.getDescription();
                    type = seller.getType();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("Seller").child(String.valueOf(id)).child("SellerInfo");
            sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ClassSellerProfile seller = snapshot.getValue(ClassSellerProfile.class);
                    assert seller != null;
                    sellerId = seller.getSellerId();
                    name = seller.getName();
                    picture = seller.getPicture();
                    phone = seller.getPhone();
                    email = seller.getEmail();
                    userId = seller.getUserId();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    //---for add and retrieve value from admin->sellerApproval-----
    public ClassSellerProfile(long sellerId, String name, String picture, String phone, String email, String userId, String address, String description, String type) {
        this.sellerId = sellerId;
        this.name = name;
        this.picture = picture;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
        this.address = address;
        this.description = description;
        this.type = type;
    }

    //---for add and retrieve value from admin->sellerApproval-----
    public ClassSellerProfile(long sellerId, String name, String picture, String phone, String email, String userId, String address, String description) {
        this.sellerId = sellerId;
        this.name = name;
        this.picture = picture;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
        this.address = address;
        this.description = description;
    }

    public ClassSellerProfile(long sellerId, String name, String picture, String phone, String email, String userId) {
        this.sellerId = sellerId;
        this.name = name;
        this.picture = picture;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
    }


    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
