package com.crinoidtechnologies.mishicreationadmin.modelsVikas;

public class OrderData {
    private  String userName;
    private String userAddress;
    private String userSProduct;
    private String userPhoneNo;
    private  String userTotalPrice;

    public OrderData() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserSProduct() {
        return userSProduct;
    }

    public void setUserSProduct(String userSProduct) {
        this.userSProduct = userSProduct;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserTotalPrice() {
        return userTotalPrice;
    }

    public void setUserTotalPrice(String userTotalPrice) {
        this.userTotalPrice = userTotalPrice;
    }

    public OrderData(String userName, String userAddress, String userSProduct, String userPhoneNo, String userTotalPrice) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userSProduct = userSProduct;
        this.userPhoneNo = userPhoneNo;
        this.userTotalPrice = userTotalPrice;
    }
}
