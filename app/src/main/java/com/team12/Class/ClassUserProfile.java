package com.team12.Class;

public class ClassUserProfile {
    String name, picture, phone, email, address, userId;
    int sellerId;

    public ClassUserProfile(){

    }

    public ClassUserProfile(String name, String picture, String phone, String email, String address, int sellerId) {
        this.name = name;
        this.picture = picture;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.sellerId = sellerId;
    }
    public ClassUserProfile(String userId, String name, String picture, String phone, String email, String address, int sellerId) {
        this.userId = userId;
        this.name = name;
        this.picture = picture;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.sellerId = sellerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }


}
