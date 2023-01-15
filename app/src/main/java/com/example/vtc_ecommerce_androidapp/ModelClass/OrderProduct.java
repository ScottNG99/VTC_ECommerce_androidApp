package com.example.vtc_ecommerce_androidapp.ModelClass;

import java.io.Serializable;

public class OrderProduct implements Serializable {

    private String pro_name;
    private String pro_price;
    private String pro_image1;
    private String goods_count;
    private String productID;

    public OrderProduct(String pro_name, String pro_price, String pro_image1, String goods_count, String productID) {
        this.pro_name = pro_name;
        this.pro_price = pro_price;
        this.pro_image1 = pro_image1;
        this.goods_count = goods_count;
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getPro_image1() {
        return pro_image1;
    }

    public void setPro_image1(String pro_image1) {
        this.pro_image1 = pro_image1;
    }

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }
}
