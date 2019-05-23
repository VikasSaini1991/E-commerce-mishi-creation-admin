package com.crinoidtechnologies.mishicreationadmin.modelsVikas;

public class ProductData {
    private  String totalPrice;
    private  int imageResult;

    public ProductData() {
    }

    public ProductData(String totalPrice, int imageResult) {
        this.totalPrice = totalPrice;
        this.imageResult = imageResult;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getImageResult() {
        return imageResult;
    }

    public void setImageResult(int imageResult) {
        this.imageResult = imageResult;
    }
}
