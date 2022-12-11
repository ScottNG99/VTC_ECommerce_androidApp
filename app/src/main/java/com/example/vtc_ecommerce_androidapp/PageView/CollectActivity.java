package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.AllProductsAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.CollectAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    SwipeRefreshLayout swipeRefreshLayout;
    private List<AllProducts> productList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public CollectAdapter adapter;

    AllProducts product;
    int userid;
    //private static  final String BASE_URL = "http://000/ecommerce/getcollect.php?userID=";




    private ImageView imgMyprofile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        imgMyprofile = findViewById(R.id.backmyprofile);
        swipeRefreshLayout =findViewById(R.id.swipe);

        recyclerView = findViewById(R.id.recViewCollect);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(CollectActivity.this,2);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our product list
        productList = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);




        //Calling method to get data to fetch data





        imgMyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectActivity.this,MyProfileActivity.class);
                startActivity(intent);
            }
        });


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getCollect();
            }

        });
    }
    @Override
    public void onRefresh() {
        productList.clear();
        getCollect();
    }

    private void getCollect() {
        swipeRefreshLayout.setRefreshing(true);
        userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.COLLECT_URL+userid,
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
                                String pdesc = object.getString("pro_desc");
                                String price = String.valueOf(pPrice);

                                String collectID = object.getString("collectID");



                                product = new AllProducts(pName,price,pScore,pImage,pImageTwo,null,pdesc,collectID,null);
                                productList.add(product);

                            }


                            adapter = new CollectAdapter(CollectActivity.this,productList);
                            recyclerView.setAdapter(adapter);

                        }catch (Exception e){

                        }

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(CollectActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        Volley.newRequestQueue(CollectActivity.this).add(stringRequest);

    }


}