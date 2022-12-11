package com.example.vtc_ecommerce_androidapp.ModelClass;

public class AllProducts {

    private String productID;
    private String pro_name;
    private String pro_price;
    private String pro_score;
    private String pro_image1;
    private String pro_image2;
    private String pro_image3;
    private String pro_desc;
    private String collectID;

//    public AllProducts(String pro_name, String pro_price, String pro_score, String pro_image1) {
//        this.pro_name = pro_name;
//        this.pro_price = pro_price;
//        this.pro_score = pro_score;
//        this.pro_image1 = pro_image1;
//    }


    public AllProducts(String pro_name, String pro_price, String pro_score, String pro_image1, String pro_image2, String pro_image3, String pro_desc,String collectID, String productID) {
        this.pro_name = pro_name;
        this.pro_price = pro_price;
        this.pro_score = pro_score;
        this.pro_image1 = pro_image1;
        this.pro_image2 = pro_image2;
        this.pro_image3 = pro_image3;
        this.pro_desc = pro_desc;
        this.collectID =collectID;
        this.productID =productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCollectID() {
        return collectID;
    }

    public void setCollectID(String collectID) {
        this.collectID = collectID;
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

    public String getPro_image2() {
        return pro_image2;
    }

    public void setPro_image2(String pro_image2) {
        this.pro_image2 = pro_image2;
    }

    public String getPro_image3() {
        return pro_image3;
    }

    public void setPro_image3(String pro_image3) {
        this.pro_image3 = pro_image3;
    }

    public String getPro_desc() {
        return pro_desc;
    }

    public void setPro_desc(String pro_desc) {
        this.pro_desc = pro_desc;
    }
}
