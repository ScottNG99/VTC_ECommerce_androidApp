package com.example.vtc_ecommerce_androidapp.ModelClass;

public class category {

    private String category_name;
    private String categorycol_image;
    private String categoryID;

    public category(String category_name, String categorycol_image, String categoryID) {
        this.category_name = category_name;
        this.categorycol_image = categorycol_image;
        this.categoryID = categoryID;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategorycol_image() {
        return categorycol_image;
    }

    public void setCategorycol_image(String categorycol_image) {
        this.categorycol_image = categorycol_image;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
