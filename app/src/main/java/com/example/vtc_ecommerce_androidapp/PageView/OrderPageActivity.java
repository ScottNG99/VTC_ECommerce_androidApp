package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.CollectAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.OrderAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Order;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderPageActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    SwipeRefreshLayout swipeRefreshLayout;
    private List<Order> orderList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OrderAdapter adapter;

    private ImageView btnimageView;

    TextView processingtxt,receivedtxt;
    View view1,view3;


    Order order;
    int userid;

    String getmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        processingtxt = findViewById(R.id.txtprocessing);

        receivedtxt = findViewById(R.id.txtreceived);
        view1 = findViewById(R.id.viewline1);

        view3 = findViewById(R.id.viewline3);

        processingtxt.setOnClickListener(this);

        receivedtxt.setOnClickListener(this);


        swipeRefreshLayout = findViewById(R.id.swipemyorder);
        recyclerView =  findViewById(R.id.recViewOrder);

        btnimageView = findViewById(R.id.tobackuserprofile);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orderList = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("message"));


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getOrders("2");
            }

        });

        btnimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderPageActivity.this,MyProfileActivity.class);
                startActivity(intent);
            }
        });




    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String getmsg = intent.getStringExtra("msg");

            if (getmsg == null){

            }else {
                orderList.clear();
                getOrders(getmsg);
            }


        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //利用LocalBroadcastManager的接口，进行反注册
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
    }

/*
    public void getOrders() {


        swipeRefreshLayout.setRefreshing(true);
        userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        System.out.println("show user id " + Config.GET_ORDER_LIST + userid);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_ORDER_LIST+userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String pName = object.getString("pro_name");
                                int pPrice = object.getInt("pro_price");

                                String pImage = object.getString("pro_image1");

                                String price = String.valueOf(pPrice);

                                String pro_num = object.getString("pro_num");

                                String pro_status = object.getString("order_status");

                                String orderlineID = object.getString("orderlineID");
                                String orderID = object.getString("orderID");



                                order = new Order(pName,price,pImage,pro_num,pro_status,orderlineID,orderID);
                                orderList.add(order);

                            }


                            adapter = new OrderAdapter(OrderPageActivity.this,orderList);
                            recyclerView.setAdapter(adapter);

                        }catch (Exception e){

                        }

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(OrderPageActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        Volley.newRequestQueue(OrderPageActivity.this).add(stringRequest);


    }*/

    public void getOrders(String status){

        swipeRefreshLayout.setRefreshing(true);
        userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();

        String link = "userID="+userid + "&order_status="+status;
        String url = Config.GET_ORDER_LIST + link;
        System.out.println("show user id " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String pName = object.getString("pro_name");
                                int pPrice = object.getInt("pro_price");

                                String pImage = object.getString("pro_image1");

                                String price = String.valueOf(pPrice);

                                String pro_num = object.getString("pro_num");

                                String pro_status = object.getString("order_status");

                                String orderlineID = object.getString("orderlineID");
                                String orderID = object.getString("orderID");



                                order = new Order(pName,price,pImage,pro_num,pro_status,orderlineID,orderID);
                                orderList.add(order);

                            }


                            adapter = new OrderAdapter(OrderPageActivity.this,orderList);
                            recyclerView.setAdapter(adapter);

                        }catch (Exception e){

                        }

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(OrderPageActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        Volley.newRequestQueue(OrderPageActivity.this).add(stringRequest);

    }


    @Override
    public void onRefresh() {

        orderList.clear();
        getOrders("2");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.txtprocessing:
                processingtxt.setTextColor(Color.parseColor("#F36D31"));
                view1.setBackgroundColor(Color.parseColor("#F36D31"));



                receivedtxt.setTextColor(Color.parseColor("#8E96A5"));
                view3.setBackgroundColor(Color.parseColor("#dfdfdf"));

                orderList.clear();
                getOrders("2");

                break;


            case R.id.txtreceived:
                receivedtxt.setTextColor(Color.parseColor("#F36D31"));
                view3.setBackgroundColor(Color.parseColor("#F36D31"));


                processingtxt.setTextColor(Color.parseColor("#8E96A5"));
                view1.setBackgroundColor(Color.parseColor("#dfdfdf"));

                orderList.clear();
                getOrders("4");


        }

    }
}