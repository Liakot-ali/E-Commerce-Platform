package com.team12;

public class ClassAddProduct {
    String name, description, image, sellerName;
    int price, sellerId;

    public ClassAddProduct(){

    }
    public ClassAddProduct(String name, String description, String image, int price, int sellerId) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.sellerId = sellerId;
    }
    public ClassAddProduct(String name, String description, String image, int price, int sellerId, String sellerName) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
