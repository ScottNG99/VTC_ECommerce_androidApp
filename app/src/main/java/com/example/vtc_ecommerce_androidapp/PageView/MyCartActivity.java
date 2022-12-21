package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.CartAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.CollectAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Cart;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    private List<Cart> cartList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public CartAdapter adapter;

    private Button checkout;
    private TextView txtTotlalPrice;
    private BottomNavigationView buttomNavbar;



    int userid;

    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        swipeRefreshLayout =findViewById(R.id.swipetwo);
        checkout = findViewById(R.id.toCheckOut);
        txtTotlalPrice = findViewById(R.id.showCarttotalPrice);

        recyclerView = findViewById(R.id.recViewCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyCartActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        cartList = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);

        buttomNavbar = findViewById(R.id.ButtomnavView);

        buttomNavbar.setSelectedItemId(R.id.cart);

        buttomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.cart:

                        return true;
                    case R.id.home:
                        startActivity(new Intent(MyCartActivity.this,HomePage_Activity.class));
                        return true;

                    case R.id.user:
                        startActivity(new Intent(MyCartActivity.this,MyProfileActivity.class));
                        return true;

                    case R.id.all:
                        startActivity(new Intent(MyCartActivity.this,ListAllProductActivity.class));
                        return true;
                }
                return false;
            }
        });






        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getCart();
            }

        });
    }



    @Override
    public void onRefresh() {
        cartList.clear();
        getCart();
    }

    private void getCart() {

        swipeRefreshLayout.setRefreshing(true);
        userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_CART+userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        int showTotalAmount = 0;


                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String pName = object.getString("pro_name");
                                int pPrice = object.getInt("pro_price");
                                String pImage = object.getString("pro_image1");
                                String price = String.valueOf(pPrice);
                                String cartID = object.getString("cartID");
                                String goods_count = object.getString("goods_count");

                                int counts = Integer.valueOf(goods_count);

                                int totalprice = pPrice * counts;
                                showTotalAmount += totalprice;



                                cart = new Cart(pName,price,pImage,cartID,goods_count);
                                cartList.add(cart);

                            }





                            adapter = new CartAdapter(MyCartActivity.this,cartList);
                            recyclerView.setAdapter(adapter);



                            System.out.println("can show price " + showTotalAmount);
                            txtTotlalPrice.setText("$" + String.valueOf(showTotalAmount));



                        }catch (Exception e){

                        }

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(MyCartActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        Volley.newRequestQueue(MyCartActivity.this).add(stringRequest);


    }


}