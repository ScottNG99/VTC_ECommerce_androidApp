package com.example.vtc_ecommerce_androidapp.api;

public class Config {

    private static String IP_ADDRESS = "192.168.1.218";

    //Data URL
    // Need to update your ip
//    public static final String DATA_URL = "http://192.168.1.218/ecommerce/getproduct.php?page=";
//    public  static final  String ALL_PRODUCT_URL = "http://192.168.1.218/ecommerce/getAllProducts.php?";
//    public static final String COLLECT_URL = "http://192.168.1.218/ecommerce/getcollect.php?userID=";
//    public static final String DELETE_COLLECT_URL = "http://192.168.1.218/ecommerce/DeleteCollectRecord.php?collectID=";
//    public static final String ADD_COLLECT_URL = "http://192.168.1.218/ecommerce/AddCollectRecord.php?";
//    public static final String DELETE_COLLECT_URL_DETAILS = "http://192.168.1.218/ecommerce/DeleteCollectRecordForDetails.php?";
//    public static final String CHECK_COLLECT_URL_DETAILS = "http://192.168.1.218/ecommerce/CheckCollectStatus.php?";
//    public static final String UPDATE_USER_PROFILE = "http://192.168.1.218/ecommerce/UpdateUserProfile.php?";
//    public static final String GET_USER_PROFILE = "http://192.168.1.218/ecommerce/getStudent.php?";
//    public static final String GET_CART = "http://192.168.1.218/ecommerce/getCart.php?userID=";
//    public static final String ADD_CART = "http://192.168.1.218/ecommerce/addCart.php?";
//    public static final String DELETE_CART = "http://192.168.1.218/ecommerce/deleteCart.php?";
//    public static final String GET_POPULAR_PRODUCT = "http://192.168.1.218/ecommerce/GetPopularProduct.php?";
//    public static final String UPDATE_CART_QTY = "http://192.168.1.218/ecommerce/updateProductQtyForCart.php?";



    public static final String DATA_URL = "http://" + IP_ADDRESS + "/ecommerce/getproduct.php?page=";
    public  static final  String ALL_PRODUCT_URL = "http://" + IP_ADDRESS + "/ecommerce/getAllProducts.php?";
    public static final String COLLECT_URL = "http://" + IP_ADDRESS + "/ecommerce/getcollect.php?userID=";
    public static final String DELETE_COLLECT_URL = "http://" + IP_ADDRESS + "/ecommerce/DeleteCollectRecord.php?collectID=";
    public static final String ADD_COLLECT_URL = "http://" +IP_ADDRESS + "/ecommerce/AddCollectRecord.php?";
    public static final String DELETE_COLLECT_URL_DETAILS = "http://" + IP_ADDRESS + "/ecommerce/DeleteCollectRecordForDetails.php?";
    public static final String CHECK_COLLECT_URL_DETAILS = "http://" +IP_ADDRESS + "/ecommerce/CheckCollectStatus.php?";
    public static final String UPDATE_USER_PROFILE = "http://" + IP_ADDRESS+ "/ecommerce/UpdateUserProfile.php?";
    public static final String GET_USER_PROFILE = "http://" + IP_ADDRESS+ "/ecommerce/getStudent.php?";
    public static final String GET_CART = "http://" + IP_ADDRESS+ "/ecommerce/getCart.php?userID=";
    public static final String ADD_CART = "http://" + IP_ADDRESS+ "/ecommerce/addCart.php?";
    public static final String DELETE_CART = "http://" + IP_ADDRESS+ "/ecommerce/deleteCart.php?";
    public static final String GET_POPULAR_PRODUCT = "http://" + IP_ADDRESS+ "/ecommerce/GetPopularProduct.php?";
    public static final String UPDATE_CART_QTY = "http://" + IP_ADDRESS+ "/ecommerce/updateProductQtyForCart.php?";
    public static final String API_STRIPE = "http://" + IP_ADDRESS+ "/ecommerce/stripe-php-master/serversCard.php?";
    public static final String GET_ORDER_LIST = "http://" + IP_ADDRESS+ "/ecommerce/getOrderList.php?userID=";
    public static final String ADD_ORDER = "http://" + IP_ADDRESS+ "/ecommerce/addOrder.php?";
    public static final String ADD_ORDER_LINE = "http://" + IP_ADDRESS+ "/ecommerce/createOrderProduct.php?";
    public static final String GET_ORDERID = "http://" + IP_ADDRESS+ "/ecommerce/getOrderID.php?";
    public static final String UPDATE_AND_CANCEL_ORDER = "http://" + IP_ADDRESS+ "/ecommerce/cancelOrderAndUpdateOrder.php?";
    public static final String UPDATE_ORDER_STATUS = "http://" + IP_ADDRESS+ "/ecommerce/updateOrderStatus.php?";
    public static final String ADD_RATING = "http://" + IP_ADDRESS+ "/ecommerce/addRating.php?";
    public static final String GET_RATING = "http://" + IP_ADDRESS+ "/ecommerce/getProducctReviews.php?";
    public static final String GET_RECOMMENDATIONAPI = "http://" + IP_ADDRESS+ "/ecommerce/recommendationSystem/getRecommendation.php?userID=";
    public static final String GET_CATEGORY = "http://" + IP_ADDRESS+ "/ecommerce/getCategory.php?";
    public static final String TEST_GET_ORDER_LIST = "http://" + IP_ADDRESS+ "/ecommerce/testGetOrder.php?";



    //JSON TAGS
    public static final String TAG_IMAGE_URL = "pro_image1";
    public static final String TAG_PRODUCTNAME = "pro_name";
    public static final String TAG_PRICE = "pro_price";
    public static final String TAG_SCORE = "pro_score";


//    public  static final  String ALL_PRODUCT_URL = "http://fypvtcecommerce.000webhostapp.com/getAllProducts.php?";
//    public static final String DATA_URL = "http://fypvtcecommerce.000webhostapp.com/getproduct.php?page=";
//    public static final String COLLECT_URL = "http://fypvtcecommerce.000webhostapp.com/getcollect.php?userID=";
//    public static final String DELETE_COLLECT_URL = "http://fypvtcecommerce.000webhostapp.com/DeleteCollectRecord.php?collectID=";
//    public static final String ADD_COLLECT_URL = "http://fypvtcecommerce.000webhostapp.com/AddCollectRecord.php?";
//    public static final String DELETE_COLLECT_URL_DETAILS = "http://fypvtcecommerce.000webhostapp.com/DeleteCollectRecordForDetails.php?";
//    public static final String CHECK_COLLECT_URL_DETAILS = "http://fypvtcecommerce.000webhostapp.com/CheckCollectStatus.php?";
//    public static final String UPDATE_USER_PROFILE = "http://fypvtcecommerce.000webhostapp.com/UpdateUserProfile.php?";
//    public static final String GET_USER_PROFILE = "http://fypvtcecommerce.000webhostapp.com/getStudent.php?";
//    public static final String GET_CART = "http://fypvtcecommerce.000webhostapp.com/getCart.php?userID=";
//    public static final String ADD_CART = "http://fypvtcecommerce.000webhostapp.com/addCart.php?";
//    public static final String DELETE_CART = "http://fypvtcecommerce.000webhostapp.com/deleteCart.php?";
//    public static final String GET_POPULAR_PRODUCT = "http://fypvtcecommerce.000webhostapp.com/GetPopularProduct.php?";
//    public static final String UPDATE_CART_QTY = "http://fypvtcecommerce.000webhostapp.com/updateProductQtyForCart.php?";
//    public static final String API_STRIPE = "http://" + IP_ADDRESS+ "/ecommerce/stripe-php-master/serversCard.php?";
//    public static final String GET_ORDER_LIST = "http://fypvtcecommerce.000webhostapp.com/getOrderList.php?userID=";
//    public static final String ADD_ORDER = "http://fypvtcecommerce.000webhostapp.com/addOrder.php?";
//    public static final String ADD_ORDER_LINE = "http://fypvtcecommerce.000webhostapp.com/createOrderProduct.php?";
//    public static final String GET_ORDERID = "http://fypvtcecommerce.000webhostapp.com/getOrderID.php?";
//    public static final String UPDATE_AND_CANCEL_ORDER = "http://fypvtcecommerce.000webhostapp.com/cancelOrderAndUpdateOrder.php?";
//    public static final String UPDATE_ORDER_STATUS = "http://fypvtcecommerce.000webhostapp.com/updateOrderStatus.php?";
//    public static final String ADD_RATING = "http://fypvtcecommerce.000webhostapp.com/addRating.php?";
//    public static final String GET_RATING = "http://fypvtcecommerce.000webhostapp.com/getProducctReviews.php?";
//    public static final String GET_RECOMMENDATIONAPI = "http://fypvtcecommerce.000webhostapp.com/recommendationSystem/getRecommendation.php?userID=";
//    public static final String GET_CATEGORY = "http://fypvtcecommerce.000webhostapp.com/getCategory.php?";

}
