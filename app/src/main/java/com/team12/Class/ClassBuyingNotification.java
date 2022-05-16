package com.team12.Class;

public class ClassBuyingNotification {
    String productName, productPrice, productPicture, sellerName, sellerPhone, sellerEmail;
    String sellerPicture, address, description, sellerId, userId, type;
    String tag;

    String notiInfo, notiTag;


    public ClassBuyingNotification() {
    }

    //-----------for show notification text----
    public ClassBuyingNotification(String notiInfo, String notiTag){
        this.notiInfo = notiInfo;
        this.notiTag = notiTag;
    }

    //--------for 'ConfirmOrder' and 'SellerResponse' notification------------
    public ClassBuyingNotification(String productName, String productPrice, String productPicture, String sellerName, String sellerPhone, String sellerEmail, String tag) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPicture = productPicture;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.sellerEmail = sellerEmail;
        this.tag = tag;
    }

    //-------------for 'ApplySeller', 'ApproveSeller' and 'DenySeller' notification------------
    public ClassBuyingNotification(String sellerName, String sellerPhone, String sellerEmail, String sellerPicture, String address, String description, String sellerId, String userId, String type, String tag) {
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.sellerEmail = sellerEmail;
        this.sellerPicture = sellerPicture;
        this.address = address;
        this.description = description;
        this.sellerId = sellerId;
        this.userId = userId;
        this.type = type;
        this.tag = tag;
    }

    @Override
    public String toString() {
        String text = "";
        switch (notiTag) {
            case "ConfirmOrder":
                text = "You're ordering a product " + notiInfo;
                break;
            case "SellerResponse":
                text = "Seller is response in your product " + notiInfo;
                break;
            case "ApplySeller":
                text = "Your seller request is processing. Edit your info here";
                break;
            case "ApproveSeller":
                text = "Your seller request is approved. Your seller ID is " + notiInfo;
                break;
            case "DenySeller":
                text = "Your seller request is denyed. Edit your info here";
                break;
        }
        return text;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerPicture() {
        return sellerPicture;
    }

    public void setSellerPicture(String sellerPicture) {
        this.sellerPicture = sellerPicture;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public String getNotiInfo() {
        return notiInfo;
    }

    public void setNotiInfo(String notiInfo) {
        this.notiInfo = notiInfo;
    }

    public String getNotiTag() {
        return notiTag;
    }

    public void setNotiTag(String notiTag) {
        this.notiTag = notiTag;
    }
}
