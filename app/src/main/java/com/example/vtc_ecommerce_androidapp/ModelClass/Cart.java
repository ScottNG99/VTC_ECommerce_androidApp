package com.example.vtc_ecommerce_androidapp.ModelClass;

public class Cart {

    private String pro_name;
    private String pro_price;
    private String pro_image1;
    private String cartID;
    private String goods_count;

    public Cart(String pro_name, String pro_price, String pro_image1, String cartID, String goods_count) {
        this.pro_name = pro_name;
        this.pro_price = pro_price;
        this.pro_image1 = pro_image1;
        this.cartID = cartID;
        this.goods_count = goods_count;
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

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }
}
