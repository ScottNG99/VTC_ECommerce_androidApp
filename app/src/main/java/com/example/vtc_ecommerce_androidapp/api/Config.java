package com.example.vtc_ecommerce_androidapp.api;

public class Config {

    //Data URL
    // Need to update your ip
    public static final String DATA_URL = "http://192.168.1.218/ecommerce/getproduct.php?page=";
    public  static final  String ALL_PRODUCT_URL = "http://192.168.1.218/ecommerce/getAllProducts.php?";
    public static final String COLLECT_URL = "http://192.168.1.218/ecommerce/getcollect.php?userID=";
    public static final String DELETE_COLLECT_URL = "http://192.168.1.218/ecommerce/DeleteCollectRecord.php?collectID=";
    public static final String ADD_COLLECT_URL = "http://192.168.1.218/ecommerce/AddCollectRecord.php?";
    public static final String DELETE_COLLECT_URL_DETAILS = "http://192.168.1.218/ecommerce/DeleteCollectRecordForDetails.php?";
    public static final String CHECK_COLLECT_URL_DETAILS = "http://192.168.1.218/ecommerce/CheckCollectStatus.php?";
    public static final String UPDATE_USER_PROFILE = "http://192.168.1.218/ecommerce/UpdateUserProfile.php?";
    public static final String GET_USER_PROFILE = "http://192.168.1.218/ecommerce/getStudent.php?";

    //JSON TAGS
    public static final String TAG_IMAGE_URL = "pro_image1";
    public static final String TAG_PRODUCTNAME = "pro_name";
    public static final String TAG_PRICE = "pro_price";
    public static final String TAG_SCORE = "pro_score";


    //public  static final  String ALL_PRODUCT_URL = "http://fypvtcecommerce.000webhostapp.com/getAllProducts.php?";
    //public static final String DATA_URL = "http://fypvtcecommerce.000webhostapp.com/getproduct.php?page=";
    //public static final String COLLECT_URL = "http://fypvtcecommerce.000webhostapp.com/getcollect.php?userID=";
    //public static final String DELETE_COLLECT_URL = "http://fypvtcecommerce.000webhostapp.com/DeleteCollectRecord.php?collectID=";
    //public static final String ADD_COLLECT_URL = "http://fypvtcecommerce.000webhostapp.com/AddCollectRecord.php?";
    //public static final String DELETE_COLLECT_URL_DETAILS = "http://fypvtcecommerce.000webhostapp.com/DeleteCollectRecordForDetails.php?";
    //public static final String CHECK_COLLECT_URL_DETAILS = "http://fypvtcecommerce.000webhostapp.com/CheckCollectStatus.php?";
    //public static final String UPDATE_USER_PROFILE = "http://fypvtcecommerce.000webhostapp.com/UpdateUserProfile.php?";
    //public static final String GET_USER_PROFILE = "http://fypvtcecommerce.000webhostapp.com/getStudent.php?";
}
