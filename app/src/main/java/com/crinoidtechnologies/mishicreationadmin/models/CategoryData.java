package com.crinoidtechnologies.mishicreationadmin.models;

public class CategoryData {
    private  String title;
    private  int imageResult;

    public CategoryData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResult() {
        return imageResult;
    }

    public void setImageResult(int imageResult) {
        this.imageResult = imageResult;
    }

    public CategoryData(String title, int imageResult) {
        this.title = title;
        this.imageResult = imageResult;
    }
}
