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
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.ProductAdapter;
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
public class ListAllProductActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener{

    private List<product> productList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;

    private BottomNavigationView buttomNavbar;

    RequestQueue requestQueue;
    private int requestCount = 1;

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
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Calling method to get data to fetch data
        getDatatwo();

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new ProductAdapter(productList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


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

                }
                return false;
            }
        });

    }

    private JsonArrayRequest getDataFromServer(int requestCount) {




        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Config.DATA_URL + String.valueOf(requestCount),null,
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
                        Toast.makeText(ListAllProductActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });



        //Returning the request
        return jsonArrayRequest;
    }











    private void getDatatwo() {
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
            productList.clear();
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
            getDatatwo();
        }
    }
}