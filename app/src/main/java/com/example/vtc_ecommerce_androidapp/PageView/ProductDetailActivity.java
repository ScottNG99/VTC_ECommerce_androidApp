package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.Adater.OrderAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.ReviewAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Order;
import com.example.vtc_ecommerce_androidapp.ModelClass.OrderProduct;
import com.example.vtc_ecommerce_androidapp.ModelClass.Review;
import com.example.vtc_ecommerce_androidapp.ModelClass.TestRespon;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imgProduct,back,imgIntroduction,imgCollect,imgIntroductionTwo,imgonlinechat;
    private TextView txtProductNamr,txtScore,txtPrice,txtdescrition,txttotalprice,txttotalQuantity;
    ImageView addQuantity,minusQuantity;

    private Button btnaddToCart,btntoCheckOut;

    private LinearLayout detatilsbtn,reviewbtn;
    //private LinearLayout showdetails,showreviws;
    private LinearLayout showdetails,nodate;
    private RelativeLayout showreviws;
    private TextView detatilstxt,reviewstxt;
    private View detailsview,reviewView;

    private List<Review> reviewList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewAdapter adapter;
    Review review;

    //Default quantity is 1;
    int quantity = 1;

    // Cannot buy more than 10
    // Set quantity limits later based on inventory
    int upperLimit = 10;
    int totalPrice;

    //check Check whether a number is even or odd
    int num = 0;



    public  String getreponses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.productImg);
        back = findViewById(R.id.backalllist);
        imgIntroduction = findViewById(R.id.imgtwo);
        imgCollect = findViewById(R.id.collect);
        txtProductNamr = findViewById(R.id.productName);
        txtScore = findViewById(R.id.txtscore);
        txtPrice = findViewById(R.id.productPrices);
        txtdescrition= findViewById(R.id.descProduct);
        txttotalprice = findViewById(R.id.totalPrice);
        txttotalQuantity = findViewById(R.id.showQuantity);
        addQuantity = findViewById(R.id.add);
        minusQuantity = findViewById(R.id.minus);
        btnaddToCart = findViewById(R.id.addCart);
        imgIntroductionTwo = findViewById(R.id.imgintrotwo);
        btntoCheckOut = findViewById(R.id.tobuy);
        imgonlinechat = findViewById(R.id.onlineChat);

        detatilsbtn = findViewById(R.id.detailsLinea);
        reviewbtn = findViewById(R.id.recivewsLinear);
        showdetails = findViewById(R.id.showDetailsInfo);
        showreviws = findViewById(R.id.showComments);

        detatilstxt = findViewById(R.id.detailstext);
        reviewstxt = findViewById(R.id.reviewtxt);
        detailsview = findViewById(R.id.detailsview);
        reviewView = findViewById(R.id.reviewline0);
        nodate = findViewById(R.id.Linearnodatareview);

        recyclerView =  findViewById(R.id.recViewreviews);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        reviewList = new ArrayList<>();






        Intent intent = getIntent();
        String pImg = intent.getStringExtra("pImage");
        String pItro = intent.getStringExtra("pIntroduction");
        String pName = intent.getStringExtra("pName");
        String pScore = intent.getStringExtra("pScore");
        String pDesce = intent.getStringExtra("pdesc");
        String pPrice = intent.getStringExtra("pPrice");
        String pid = intent.getStringExtra("PID");
        String pItroTwo = intent.getStringExtra("pIntroductiontwo");

        String getAcitityPage = intent.getStringExtra("sendActivity");


        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showreviws.setVisibility(View.VISIBLE);
                showdetails.setVisibility(View.GONE);

                reviewstxt.setTextColor(Color.parseColor("#000000"));
                reviewView.setBackgroundColor(Color.parseColor("#000000"));

                detatilstxt.setTextColor(Color.parseColor("#8E96A5"));
                detailsview.setBackgroundColor(Color.parseColor("#dfdfdf"));

                getReviews(pid);




            }
        });

        detatilsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showreviws.setVisibility(View.GONE);
                showdetails.setVisibility(View.VISIBLE);

                reviewstxt.setTextColor(Color.parseColor("#8E96A5"));
                reviewView.setBackgroundColor(Color.parseColor("#dfdfdf"));

                detatilstxt.setTextColor(Color.parseColor("#000000"));
                detailsview.setBackgroundColor(Color.parseColor("#000000"));

                nodate.setVisibility(View.GONE);

                reviewList.clear();
            }
        });

        imgonlinechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProductDetailActivity.this,OnlineChatActivity.class);
//                intent1.putExtra("productName",pName);
//                intent1.putExtra("productPrice",pPrice);
//                intent1.putExtra("productImg",pImg);
//                intent1.putExtra("productintro1",pItro);
//                intent1.putExtra("productintro2",pItroTwo);
//                intent1.putExtra("productscore",pScore);
//                intent1.putExtra("productintrodation",pDesce);
//                intent1.putExtra("productID",pid);
                startActivity(intent1);
            }
        });

        //To Check Out a product
        btntoCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                List<OrderProduct> orderProductList;
//                orderProductList = new ArrayList<>();
//
//                OrderProduct orderProduct = new OrderProduct(pName,pPrice,pImg,String.valueOf(quantity));
//                orderProductList.add(orderProduct);

                Intent intentCheckOut = new Intent(ProductDetailActivity.this,CheckOutActivity.class);
                intentCheckOut.putExtra("checkIntent",1);
                intentCheckOut.putExtra("productName",pName);
                intentCheckOut.putExtra("productPrice",pPrice);
                intentCheckOut.putExtra("productImg",pImg);
                intentCheckOut.putExtra("productQty",String.valueOf(quantity));
                intentCheckOut.putExtra("productID",pid);

                startActivityForResult(intentCheckOut,0);

            }
        });




        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity == upperLimit) {

                    Toast.makeText(ProductDetailActivity.this, "Cant Order More than 10", Toast.LENGTH_SHORT).show();
                    txttotalQuantity.setText(String.valueOf(quantity));


                }else {
                    quantity++;
                    txttotalQuantity.setText(String.valueOf(quantity));
                    totalPrice = quantity * Integer.parseInt(pPrice);
                    txttotalprice.setText(String.valueOf("$"+totalPrice));


                }



            }
        });

        minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity == 1) {

                    Toast.makeText(ProductDetailActivity.this, "A minimum of one", Toast.LENGTH_SHORT).show();
                    txttotalQuantity.setText(String.valueOf(quantity));
                    quantity = 1;


                } else {

                    quantity--;
                    txttotalQuantity.setText(String.valueOf(quantity));
                    totalPrice = totalPrice - Integer.parseInt(pPrice);
                    txttotalprice.setText(String.valueOf("$"+totalPrice));

                }

            }
        });


        if (intent !=null){

            Glide.with(ProductDetailActivity.this).load(pImg).into(imgProduct);
            Glide.with(ProductDetailActivity.this).load(pItro).into(imgIntroduction);
            Glide.with(ProductDetailActivity.this).load(pItroTwo).into(imgIntroductionTwo);

            txtProductNamr.setText(pName);
            txtScore.setText(pScore);
            txtPrice.setText("$"+pPrice);
            txtdescrition.setText(pDesce);
            txttotalprice.setText("$"+pPrice);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getAcitityPage.equals("ListAll")){
                    Intent intent = new Intent(ProductDetailActivity.this,ListAllProductActivity.class);
                    startActivity(intent);
                }else if (getAcitityPage.equals("Collect")){
                    Intent intent = new Intent(ProductDetailActivity.this,CollectActivity.class);
                    startActivity(intent);
                }else if (getAcitityPage.equals("Popular")){
                    Intent intent = new Intent(ProductDetailActivity.this,HomePage_Activity.class);
                    startActivity(intent);
                }


            }
        });
        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        String productId = pid;
        String txtUserID = String.valueOf(userid);
        String targetURL = "userID="+txtUserID + "&productID=" +productId;
        getCollectStatus(targetURL);

        System.out.println("send url " + targetURL);




        imgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
                String productId = pid;
                String txtUserID = String.valueOf(userid);

                String targetURL = "userID="+txtUserID + "&productID=" +productId;

                if (imgCollect.getBackground().getConstantState() == getResources().getDrawable(R.drawable.noheart).getConstantState()){


                        imgCollect.setBackgroundResource(R.drawable.heart);

                        String linkURL = Config.ADD_COLLECT_URL+targetURL;

                        new CollectManager().execute(linkURL);
                    }else {
                        imgCollect.setBackgroundResource(R.drawable.noheart);
                        String deletelinkURL = Config.DELETE_COLLECT_URL_DETAILS+targetURL;
                        new CollectManager().execute(deletelinkURL);
                    }




            }
        });


        btnaddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart(pid);
            }
        });


    }

    private void getReviews(String pid) {




        String link = "productID="+pid;
        String url = Config.GET_RATING + link;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String productID = object.getString("productID");
                                String rating = object.getString("rating");
                                String comment = object.getString("comment");
                                String creattime = object.getString("creat_time");
                                String nickname = object.getString("user_nick_name");



                                review = new Review(productID,rating,comment,creattime,nickname);
                                reviewList.add(review);



                            }

                            if (reviewList == null || reviewList.isEmpty()){
                                nodate.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else {
                                nodate.setVisibility(View.GONE);

                            }


                            adapter = new ReviewAdapter(ProductDetailActivity.this,reviewList);
                            recyclerView.setAdapter(adapter);

                        }catch (Exception e){

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(ProductDetailActivity.this, error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        Volley.newRequestQueue(ProductDetailActivity.this).add(stringRequest);

    }

    private void addCart(String pid) {

        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        String productId = pid;
        String txtUserID = String.valueOf(userid);

        String targetURL = "userID="+txtUserID + "&productID=" +productId + "&goods_count=" + quantity;

        String linkURL = Config.ADD_CART+targetURL;

        new CollectManager().execute(linkURL);

//        Toast.makeText(getApplicationContext(),"Add to Cart successful",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ProductDetailActivity.this,MyCartActivity.class);
        startActivity(intent);


    }


    public void getCollectStatus(String url){



        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.CHECK_COLLECT_URL_DETAILS+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String collectID = object.getString("collectID");
                                String userID = object.getString("userID");
                                String productID = object.getString("productID");
                                String collect_time = object.getString("collect_time");
                                String responseone = object.getString("response");

                                if (responseone.equals("yes")){
                                    imgCollect.setBackgroundResource(R.drawable.heart);

                                }else {
                                    imgCollect.setBackgroundResource(R.drawable.noheart);
                                }








                            }

                        }catch (Exception e){

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("this 3 ");

            }
        });


        Volley.newRequestQueue(ProductDetailActivity.this).add(stringRequest);






    }




}