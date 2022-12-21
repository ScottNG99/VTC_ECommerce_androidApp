package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.product;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ListAllProductActivity extends AppCompatActivity{

    private List<AllProducts> productList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AllProductsAdapter adapter;

    private BottomNavigationView buttomNavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_product);

        recyclerView = findViewById(R.id.recViewAll);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our product list
        productList = new ArrayList<>();


        //Calling method to get data to fetch data
        getproduct();



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

    }

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



    }



}