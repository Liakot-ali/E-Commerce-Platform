package com.team12.Class;

public class ClassSellingNotification {

    String productId, productName, productPrice, productPicture;
    String customerName, customerPhone, customerEmail, customerAddress, customerNote;
    String productDescription, reportMessage, sellerId, tag;

    String messageProduct, messageName;

    public ClassSellingNotification() {
    }

    public ClassSellingNotification(String message1, String message2, String tag) {
        this.messageProduct = message1;
        this.messageName = message2;
        this.tag = tag;
    }

    public ClassSellingNotification(String productId, String productName, String productPrice, String productPicture, String productDescription, String sellerId, String tag) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPicture = productPicture;
        this.productDescription = productDescription;
        this.sellerId = sellerId;
        this.tag = tag;
    }

    public ClassSellingNotification(String productId, String productName, String productPrice, String productPicture, String customerName, String customerPhone, String customerEmail, String customerAddress, String customerNote, String tag) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPicture = productPicture;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerNote = customerNote;
        this.tag = tag;
    }

    @Override
    public String toString() {
        String text = "";
        if (tag.equals("PostProduct")) {
            text = "Your product " + messageProduct + " is now in processing.\nEdit your product here.";
        } else if (tag.equals("ApproveProduct")) {
            text = "Your product " + messageProduct + " is approved by seller.";
        } else if (tag.equals("DenyProduct")) {
            text = "Your product " + messageProduct + " is denyed by seller.\nEdit your product here";
        } else if (tag.equals("ConfirmOrder")) {
            text = "A buyer " + messageName + " is order your product " + messageProduct + ". Click for details";
        }
        return text;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessageProduct() {
        return messageProduct;
    }

    public void setMessageProduct(String messageProduct) {
        this.messageProduct = messageProduct;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }


}
