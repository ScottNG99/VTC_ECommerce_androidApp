package com.example.vtc_ecommerce_androidapp.ModelClass;

public class Order {
    private String pro_name;
    private String pro_price;
    private String pro_image1;
    private String pro_num;
    private String order_status;
    private String orderlineID;
    private String orderID;
    private String productID;

    public Order(String pro_name, String pro_price, String pro_image1, String pro_num, String order_status, String orderlineID, String orderID, String productID) {
        this.pro_name = pro_name;
        this.pro_price = pro_price;
        this.pro_image1 = pro_image1;
        this.pro_num = pro_num;
        this.order_status = order_status;
        this.orderlineID = orderlineID;
        this.orderID = orderID;
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getOrderlineID() {
        return orderlineID;
    }

    public void setOrderlineID(String orderlineID) {
        this.orderlineID = orderlineID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
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

    public String getPro_num() {
        return pro_num;
    }

    public void setPro_num(String pro_num) {
        this.pro_num = pro_num;
    }
}
