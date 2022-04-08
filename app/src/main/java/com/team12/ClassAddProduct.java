package com.team12;

public class ClassAddProduct {
    String name;
    String description;
    String image;
    String productId;
    long price, sellerId;

    public ClassAddProduct(){

    }

    public ClassAddProduct(String productId, String name, String description, String image, long price, long sellerId) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.sellerId = sellerId;
    }

    public ClassAddProduct(String name, String description, String image, long price, long sellerId) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }
}
