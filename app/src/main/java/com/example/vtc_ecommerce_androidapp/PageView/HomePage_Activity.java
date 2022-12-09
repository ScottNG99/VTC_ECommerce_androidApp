package com.example.vtc_ecommerce_androidapp.PageView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.ProductAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.SliderAdapter;
import com.example.vtc_ecommerce_androidapp.MainActivity;
import com.example.vtc_ecommerce_androidapp.ModelClass.product;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class HomePage_Activity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {

    SliderView sliderView;
    SearchView searchView;

    int[] images = {R.drawable.vtc_notebook_banner,
            R.drawable.newstwo,
            R.drawable.newthree
    };

    private List<product> productList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  ProductAdapter adapter;

    RequestQueue requestQueue;
    private int requestCount = 1;

    private BottomNavigationView buttomNavbar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our product list
        productList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Calling method to get data to fetch data
        getData();

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new ProductAdapter(productList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);



        searchView = findViewById(R.id.searchView);


        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        /* 設置圓點動畫效果*/
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        /* 設置滾動動畫效果*/
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();


        searchView.setQueryHint("Search Product");
        searchView.onActionViewExpanded();
        searchView.clearFocus();

        buttomNavbar = findViewById(R.id.ButtomnavView);

        buttomNavbar.setSelectedItemId(R.id.home);


        buttomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.all:
                        startActivity(new Intent(HomePage_Activity.this,ListAllProductActivity.class));

                        return true;
                    case R.id.home:
                        return true;

                    case R.id.user:
                        startActivity(new Intent(HomePage_Activity.this,MyProfileActivity.class));
                        return true;
                }
                return false;
            }
        });



    }



    //This method will get data from the web api
    private JsonArrayRequest getDataFromServer(int requestCount) {




        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Config.DATA_URL + String.valueOf(requestCount),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //If an error occurs that means end of the list has reached
                        Toast.makeText(HomePage_Activity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });



        //Returning the request
        return jsonArrayRequest;
    }











    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }





    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            product product = new product();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                product.setPro_image1(json.getString(Config.TAG_IMAGE_URL));
                product.setPro_name(json.getString(Config.TAG_PRODUCTNAME));
                product.setPro_price(json.getString(Config.TAG_PRICE));
                product.setPro_score(json.getString(Config.TAG_SCORE));

                System.out.println("hget img  " +  json.getString(Config.TAG_IMAGE_URL));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            //productList.clear();
            productList.add(product);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();


    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getData();
        }
    }





}