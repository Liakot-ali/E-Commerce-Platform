package com.team12;

public class ClassSellerProfile {
    long sellerId;
    String name, picture, phone, email, userId;

    public ClassSellerProfile() {
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
}
