package com.team12;

public class ClassSellerProfile {
    long sellerId;
    String name, picture, phone, email, userId;
    String address, description, type;

    public ClassSellerProfile() {

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
