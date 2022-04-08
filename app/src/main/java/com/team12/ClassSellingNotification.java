package com.team12;

public class ClassSellingNotification {
    String sellingId, productId, productName, cusName, cusPhone, cusEmail, cusAddress, cusNote;

    public ClassSellingNotification() {
    }

    public ClassSellingNotification(String sellingId, String productId, String productName, String cusName, String cusPhone, String cusEmail, String cusAddress, String cusNote) {
        this.sellingId = sellingId;
        this.productId = productId;
        this.productName = productName;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusEmail = cusEmail;
        this.cusAddress = cusAddress;
        this.cusNote = cusNote;
    }

    public String getSellingId() {
        return sellingId;
    }

    public void setSellingId(String sellingId) {
        this.sellingId = sellingId;
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

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusNote() {
        return cusNote;
    }

    public void setCusNote(String cusNote) {
        this.cusNote = cusNote;
    }
}
