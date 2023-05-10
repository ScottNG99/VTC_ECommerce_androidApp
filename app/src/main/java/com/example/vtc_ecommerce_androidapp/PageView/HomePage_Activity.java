package com.example.vtc_ecommerce_androidapp.PageView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.CollectAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.PopularAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.ProductAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.SliderAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.categoryAdapter;
import com.example.vtc_ecommerce_androidapp.MainActivity;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.category;
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
public class HomePage_Activity extends AppCompatActivity  {

    SliderView sliderView;
    SearchView searchView;

    int[] images = {R.drawable.vtc_notebook_banner,
            R.drawable.newstwo,
            R.drawable.newthree
    };

    private List<AllProducts> allProductsList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  PopularAdapter adapter;

    //recommendation view adapter
    private  RecyclerView recommendationRec;
    private List<AllProducts> allRcommendationList;
    private RecyclerView.LayoutManager reclayoutManager;

    //category view adapter
    private  RecyclerView catergoryRec;
    private List<category> allcatergoryList;
    private RecyclerView.LayoutManager catelayoutManager;
    private categoryAdapter categoryAdapter;

//    RequestQueue requestQueue;
//    private int requestCount = 1;

    private BottomNavigationView buttomNavbar;

    AllProducts product;
    category category;

    private int userid;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        recommendationRec = findViewById(R.id.RecommendationrecyclerView);
        recommendationRec.setHasFixedSize(true);
        reclayoutManager = new GridLayoutManager(this,2);
        recommendationRec.setLayoutManager(reclayoutManager);


        catergoryRec = findViewById(R.id.categoryrecyclerView);
        catelayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        catergoryRec.setLayoutManager(catelayoutManager);





        //Initializing our product list
        allProductsList = new ArrayList<>();
        allRcommendationList = new ArrayList<>();
        allcatergoryList = new ArrayList<>();
        //requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Calling method to get data to fetch data
        //getData();

        getPopularData();
        getRecommendationData();

        getcategory();

        //Adding an scroll change listener to recyclerview
        //recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter


        //Adding adapter to recyclerview
        //recyclerView.setAdapter(adapter);



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

                    case R.id.cart:
                        startActivity(new Intent(HomePage_Activity.this,MyCartActivity.class));
                        return true;
                }
                return false;
            }
        });



    }

    private void getcategory() {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String category_name = object.getString("category_name");

                                String categoryID = object.getString("categoryID");
                                String categorycol_image = object.getString("categorycol_image");








                                category = new category(category_name,categorycol_image,categoryID);
                                allcatergoryList.add(category);

                            }


                                categoryAdapter = new categoryAdapter(HomePage_Activity.this,allcatergoryList);
                                catergoryRec.setAdapter(categoryAdapter);

                        }catch (Exception e){

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(HomePage_Activity.this, error.toString(),Toast.LENGTH_LONG).show();


            }
        });



        Volley.newRequestQueue(HomePage_Activity.this).add(stringRequest);








//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/1.png","Windows"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/2.png","Mac"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/3.png","Earphone"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/4.png","Stationery"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/5.png","Mouse and Keyboard"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/6.png","Tablet"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/7.png","Rucksack"));
//        allcatergoryList.add(new category("http://fypvtcecommerce.000webhostapp.com/image/category/8.png","Monitor"));
//
//        categoryAdapter = new categoryAdapter(HomePage_Activity.this,allcatergoryList);
//        catergoryRec.setAdapter(categoryAdapter);







    }

    private void getRecommendationData() {
        userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_RECOMMENDATIONAPI+userid,
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
                                String pID = object.getString("productID");
                                String pImageThree = object.getString("pro_image3");
                                String price = String.valueOf(pPrice);

                                System.out.println("show recommendation id " + pName);



                                product = new AllProducts(pName,price,pScore,pImage,pImageTwo,pImageThree,pdesc,null,pID);
                                allRcommendationList.add(product);

                            }


                            adapter = new PopularAdapter(HomePage_Activity.this,allRcommendationList);
                            recommendationRec.setAdapter(adapter);

                        }catch (Exception e){

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(HomePage_Activity.this, error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        Volley.newRequestQueue(HomePage_Activity.this).add(stringRequest);




    }

    private void getPopularData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_POPULAR_PRODUCT,
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
                                String pID = object.getString("productID");
                                String pImageThree = object.getString("pro_image3");
                                String price = String.valueOf(pPrice);







                                product = new AllProducts(pName,price,pScore,pImage,pImageTwo,pImageThree,pdesc,null,pID);
                                allProductsList.add(product);

                            }


                            adapter = new PopularAdapter(HomePage_Activity.this,allProductsList);
                            recyclerView.setAdapter(adapter);

                        }catch (Exception e){

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(HomePage_Activity.this, error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        Volley.newRequestQueue(HomePage_Activity.this).add(stringRequest);
    }


    //This method will get data from the web api
//    private JsonArrayRequest getDataFromServer(int requestCount) {
//
//
//
//
//        //JsonArrayRequest of volley
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,Config.DATA_URL + String.valueOf(requestCount),null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        //Calling method parseData to parse the json response
//                        parseData(response);
//                        //Hiding the progressbar
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        //If an error occurs that means end of the list has reached
//                        Toast.makeText(HomePage_Activity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//
//        //Returning the request
//        return jsonArrayRequest;
//    }











//    private void getData() {
//        //Adding the method to the queue by calling the method getDataFromServer
//        requestQueue.add(getDataFromServer(requestCount));
//        //Incrementing the request counter
//        requestCount++;
//    }





//    private void parseData(JSONArray array) {
//        for (int i = 0; i < array.length(); i++) {
//            //Creating the superhero object
//            product product = new product();
//            JSONObject json = null;
//            try {
//                //Getting json
//                json = array.getJSONObject(i);
//
//                //Adding data to the superhero object
//                product.setPro_image1(json.getString(Config.TAG_IMAGE_URL));
//                product.setPro_name(json.getString(Config.TAG_PRODUCTNAME));
//                product.setPro_price(json.getString(Config.TAG_PRICE));
//                product.setPro_score(json.getString(Config.TAG_SCORE));
//
//                System.out.println("hget img  " +  json.getString(Config.TAG_IMAGE_URL));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //Adding the superhero object to the list
//            //productList.clear();
//            productList.add(product);
//        }
//
//        //Notifying the adapter that data has been added or changed
//        adapter.notifyDataSetChanged();
//
//
//    }

//    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
//        if (recyclerView.getAdapter().getItemCount() != 0) {
//            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
//                return true;
//        }
//        return false;
//    }
//
//    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//        //Ifscrolled at last then
//        if (isLastItemDisplaying(recyclerView)) {
//            //Calling the method getdata again
//            getData();
//        }
//    }





}