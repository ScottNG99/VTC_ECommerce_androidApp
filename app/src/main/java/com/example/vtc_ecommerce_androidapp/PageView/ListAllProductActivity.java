package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.AllProductsAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.ProductAdapter;
import com.example.vtc_ecommerce_androidapp.DialogFragment.FilterDialogFragment;
import com.example.vtc_ecommerce_androidapp.DialogFragment.SortDialogFragment;
import com.example.vtc_ecommerce_androidapp.DialogFragment.SuccessfulAlertDialogFragment;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.product;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ListAllProductActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<AllProducts> productList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AllProductsAdapter adapter;

    private BottomNavigationView buttomNavbar;

    SwipeRefreshLayout swipeRefreshLayout;

    MaterialButton btnFilter,btnSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_product);

        btnFilter = findViewById(R.id.btnfiltersort);
        btnSort = findViewById(R.id.btnsort);

        swipeRefreshLayout = findViewById(R.id.swipemyallproduct);

        recyclerView = findViewById(R.id.recViewAll);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our product list
        productList = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);


        //Calling method to get data to fetch data


        Intent intent = getIntent();
        String categoryID = intent.getStringExtra("categoryID");

//        if (categoryID != null){
//
//            getproduct(categoryID,"0","0");
//
//        }





        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogFragment newFragment = new FilterDialogFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                newFragment.show(ft, "dialog");
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("Filtermessage"));



        buttomNavbar = findViewById(R.id.ButtomnavView);

        buttomNavbar.setSelectedItemId(R.id.all);


        buttomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.all:


                        return true;
                    case R.id.home:
                        startActivity(new Intent(ListAllProductActivity.this,HomePage_Activity.class));
                        return true;

                    case R.id.user:
                        startActivity(new Intent(ListAllProductActivity.this,MyProfileActivity.class));
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(ListAllProductActivity.this,MyCartActivity.class));
                        return true;

                }
                return false;
            }
        });


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                if (categoryID != null){

                    getproduct(categoryID,null,null);

                }else {
                    getproduct("0",null,null);

                }


            }

        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                Collections.sort(productList,AllProducts.ProductPrice);
//
//                System.out.println("show listaa " + productList);
//
//
//                adapter = new AllProductsAdapter(ListAllProductActivity.this,productList);
//                recyclerView.setAdapter(adapter);


                SortDialogFragment newFragment = new SortDialogFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                newFragment.show(ft, "dialog");

            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(SortMessageReceiver,
                new IntentFilter("Sortmessage"));



    }



    public BroadcastReceiver SortMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String getsortMsg = intent.getStringExtra("msg");

            if (getsortMsg.equals("lowest")){
                Collections.sort(productList,AllProducts.ProductPrice);

                adapter = new AllProductsAdapter(ListAllProductActivity.this,productList);
                recyclerView.setAdapter(adapter);

            }else if (getsortMsg.equals("highestScore")){

                Collections.sort(productList,AllProducts.ProductScore);

                adapter = new AllProductsAdapter(ListAllProductActivity.this,productList);
                recyclerView.setAdapter(adapter);

            }else if (getsortMsg.equals("highest")){

                Collections.sort(productList,AllProducts.ProductHighestPrice);

                adapter = new AllProductsAdapter(ListAllProductActivity.this,productList);
                recyclerView.setAdapter(adapter);

            }


        }
    };




    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String getcategoryID = intent.getStringExtra("categoryID");
            String getlowestPrice = intent.getStringExtra("lowestPrice");
            String gethighestPrice = intent.getStringExtra("highestPrice");


            productList.clear();
            getproduct(getcategoryID,getlowestPrice,gethighestPrice);




        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(SortMessageReceiver);
    }


/*
    private void getproduct() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.ALL_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String pName = object.getString("pro_name");
                                int pPrice = object.getInt("pro_price");
                                String pScore = object.getString("pro_score");
                                String pImage = object.getString("pro_image1");
                                String pImageTwo = object.getString("pro_image2");
                                String pImageThree = object.getString("pro_image3");
                                String pdesc = object.getString("pro_desc");
                                String price = String.valueOf(pPrice);
                                String pID = object.getString("productID");

                                System.out.println("show image s " + pImage );





                                AllProducts product = new AllProducts(pName,price,pScore,pImage,pImageTwo,pImageThree,pdesc,null,pID);
                                productList.add(product);
                            }

                        }catch (Exception e){

                        }

                        adapter = new AllProductsAdapter(ListAllProductActivity.this,productList);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(ListAllProductActivity.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(ListAllProductActivity.this).add(stringRequest);



    }*/

    public void getproduct(String categoryID, String lowest, String highest) {
        swipeRefreshLayout.setRefreshing(true);
        String link = "categoryID="+categoryID + "&lowest_pro_price="+lowest + "&highest_pro_price="+highest;
        String url = Config.ALL_PRODUCT_URL + link;

        System.out.println("show path " + url);

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
                                String pScore = object.getString("pro_score");
                                String pImage = object.getString("pro_image1");
                                String pImageTwo = object.getString("pro_image2");
                                String pImageThree = object.getString("pro_image3");
                                String pdesc = object.getString("pro_desc");
                                String price = String.valueOf(pPrice);
                                String pID = object.getString("productID");


                                AllProducts product = new AllProducts(pName,price,pScore,pImage,pImageTwo,pImageThree,pdesc,null,pID);
                                productList.add(product);
                            }

                        }catch (Exception e){

                        }

                        adapter = new AllProductsAdapter(ListAllProductActivity.this,productList);
                        recyclerView.setAdapter(adapter);



                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(ListAllProductActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        Volley.newRequestQueue(ListAllProductActivity.this).add(stringRequest);



    }


    @Override
    public void onRefresh() {
        productList.clear();
        getproduct("0",null,null);

    }




}