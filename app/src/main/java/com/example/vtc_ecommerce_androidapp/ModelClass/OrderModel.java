package com.example.vtc_ecommerce_androidapp.ModelClass;

public class OrderModel {

    private String orderID,userID,total_price,order_status,product_delivery,creat_time,quantity;

    public OrderModel(String orderID, String userID, String total_price, String order_status, String product_delivery, String creat_time, String quantity) {
        this.orderID = orderID;
        this.userID = userID;
        this.total_price = total_price;
        this.order_status = order_status;
        this.product_delivery = product_delivery;
        this.creat_time = creat_time;
        this.quantity = quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProduct_delivery() {
        return product_delivery;
    }

    public void setProduct_delivery(String product_delivery) {
        this.product_delivery = product_delivery;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
