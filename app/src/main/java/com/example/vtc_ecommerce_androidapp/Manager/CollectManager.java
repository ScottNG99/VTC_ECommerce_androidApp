package com.example.vtc_ecommerce_androidapp.Manager;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.AllProductsAdapter;
import com.example.vtc_ecommerce_androidapp.Adater.CollectAdapter;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.TestRespon;
import com.example.vtc_ecommerce_androidapp.PageView.CollectActivity;
import com.example.vtc_ecommerce_androidapp.PageView.ListAllProductActivity;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CollectManager extends AsyncTask<String, String, String>  {

    public static final String CHECK_COLLECT_URL_DETAILS = "http://192.168.1.218/ecommerce/CheckCollectStatus.php?";


    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = null;


        try {
            URL url;
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
                InputStream is = conn.getInputStream();

            }else{
                InputStream err = conn.getErrorStream();
            }
            return "Done";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }


    public void getCollectStatus(String userid, String productid){



        StringRequest stringRequest = new StringRequest(Request.Method.GET, CHECK_COLLECT_URL_DETAILS+"userID="+userid+"productID="+productid,
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
                                String responses = object.getString("response");





                            }

                        }catch (Exception e){

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });






    }




}
