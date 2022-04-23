package com.team12.Class;

public class ClassBuyingNotification {
    String buyingId, sellerId, sellerName, productId, productName;

    public ClassBuyingNotification() {
    }

    public ClassBuyingNotification(String buyingId, String sellerId, String sellerName, String productId, String productName) {
        this.buyingId = buyingId;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.productId = productId;
        this.productName = productName;
    }

    public String getBuyingId() {
        return buyingId;
    }

    public void setBuyingId(String buyingId) {
        this.buyingId = buyingId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
