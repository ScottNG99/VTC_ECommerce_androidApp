package com.example.vtc_ecommerce_androidapp.ModelClass;

public class Review {

    private String productID;
    private String rating;
    private String comment;
    private String creat_time;
    private String user_nick_name;

    public Review(String productID, String rating, String comment, String creat_time, String user_nick_name) {
        this.productID = productID;
        this.rating = rating;
        this.comment = comment;
        this.creat_time = creat_time;
        this.user_nick_name = user_nick_name;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }
}
