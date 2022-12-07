package com.example.vtc_ecommerce_androidapp.ModelClass;

public class AllProducts {

    private String pro_name;
    private String pro_price;
    private String pro_score;
    private String pro_image1;

    public AllProducts(String pro_name, String pro_price, String pro_score, String pro_image1) {
        this.pro_name = pro_name;
        this.pro_price = pro_price;
        this.pro_score = pro_score;
        this.pro_image1 = pro_image1;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_score() {
        return pro_score;
    }

    public void setPro_score(String pro_score) {
        this.pro_score = pro_score;
    }

    public String getPro_image1() {
        return pro_image1;
    }

    public void setPro_image1(String pro_image1) {
        this.pro_image1 = pro_image1;
    }
}
