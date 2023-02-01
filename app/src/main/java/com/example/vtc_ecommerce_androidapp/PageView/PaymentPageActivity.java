package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.DialogFragment.SuccessfulAlertDialogFragment;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.OrderModel;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import okhttp3.*;


public class PaymentPageActivity extends AppCompatActivity {

    private static final String TAG = "PaymentPageActivity";
//    private static final String BACKEND_URL = "http://192.168.1.218/crdeitCardSheet/serversCard.php?";

    private CardView CrditCardPayment;
    ImageView tobackcheckout;

    private PaymentSheet paymentSheet;
    private String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
   

    int getProductPrice,getProductsPrice;
    int finalAmount = 0;

    String getOrderID;

    ArrayList<String> getProductID;
    ArrayList<String> getProductTotalPrice;
    ArrayList<String> getProductQty;

    String formUnpaidPage;
    String Msgpaided;



    OrderModel orderModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        fetchApi();
        CrditCardPayment = findViewById(R.id.cardPayment);

        tobackcheckout = findViewById(R.id.tobackCheckOut);

        //getApiOrderID();

        getProductID = new ArrayList<String>();
        getProductTotalPrice = new ArrayList<String>();
        getProductQty = new ArrayList<String>();







        Intent intent = getIntent();
        getProductPrice = intent.getIntExtra("sendAproductAamount",0);
        getProductsPrice = intent.getIntExtra("sendproductsAamount",0);
        getProductID = getIntent().getExtras().getStringArrayList("productIDList");
        getProductTotalPrice = getIntent().getExtras().getStringArrayList("productTotalPrice");
        getProductQty = getIntent().getExtras().getStringArrayList("productQTY");

//        for (int i=0; i<getProductID.size(); i++){
//            System.out.println("show id list all " + getProductID.get(i) + " and qty " + getProductQty.get(i) + " and amounmt " + getProductTotalPrice.get(i));
//
//        }
        formUnpaidPage = intent.getStringExtra("proPrice");
        Msgpaided = intent.getStringExtra("sendMsgToBuy");
        getOrderID = intent.getStringExtra("sendOrderID");







//        if (getProductPrice == 0){
//            finalAmount = getProductsPrice*100;
//        }else {
//            finalAmount = getProductPrice*100;
//        }

        if (getProductPrice ==0 && getProductsPrice ==0){
            finalAmount = Integer.parseInt(formUnpaidPage)*100;
        }else {
            if (getProductPrice == 0){
                finalAmount = getProductsPrice*100;
            }else {
                finalAmount = getProductPrice*100;
            }
        }





        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        CrditCardPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (paymentIntentClientSecret != null){
                   paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, new PaymentSheet.Configuration("Codes Easy",customerConfig));

               }
            }
        });

        //fetchPaymentIntent();


        tobackcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PaymentPageActivity.this,CheckOutActivity.class);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
        


        



    }

//    private void getApiOrderID() {
//
//        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
//        String path = "userID=" + String.valueOf(userid);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_ORDERID+path,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//
//                        try {
//
//                            JSONArray array = new JSONArray(response);
//                            for (int i = 0; i<array.length(); i++){
//
//                                JSONObject object = array.getJSONObject(i);
//
//                                String orderid = object.getString("orderID");
//
//                                getOrderID = orderid;
//
//
//
//                            }
//
//
//                        }catch (Exception e){
//
//                        }
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(PaymentPageActivity.this, error.toString(),Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//
//        Volley.newRequestQueue(PaymentPageActivity.this).add(stringRequest);
//
//
//
//
//
//
//    }


    private void fetchApi() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Config.API_STRIPE;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            customerConfig = new PaymentSheet.CustomerConfiguration(
                                    jsonObject.getString("customer"),
                                    jsonObject.getString("ephemeralKey")
                            );

                            paymentIntentClientSecret = jsonObject.getString("paymentIntent");
                            PaymentConfiguration.init(getApplicationContext(), jsonObject.getString("publishableKey"));
                            System.out.println("show paymentSecre "+ paymentIntentClientSecret);

                        } catch (JSONException e){
                            e.printStackTrace();;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                paramV.put("amount",String.valueOf(finalAmount));
                return paramV;
            }
        };
        queue.add(stringRequest);


    }



    private void showAlert(String title,  String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }



    private void onPaymentSheetResult(
            final PaymentSheetResult paymentSheetResult
    ) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            //showToast("Payment complete!");

            SuccessfulAlertDialogFragment  newFragment = new SuccessfulAlertDialogFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            newFragment.show(ft, "dialog");

            //createOrderLine();
            if (Msgpaided.equals("paided")){
                int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
                String link = "orderID="+getOrderID +  "&userID="+userid ;

                System.out.println("ti go this code " + getOrderID + " and " + userid);
                String url = Config.UPDATE_ORDER_STATUS + link;
                new CollectManager().execute(url);
            }


        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.i(TAG, "Payment canceled!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
            showAlert("Payment failed", error.getLocalizedMessage());
        }
    }

//    private void createOrderLine() {
//
//        for (int n=0;n<getProductID.size();n++){
//
//            String link = "productID="+getProductID.get(n) + "&pro_total_price="+getProductTotalPrice.get(n) + "&pro_num="+getProductQty.get(n) + "&orderID="+getOrderID;
//
//            System.out.println("show link shhh " + link);
//            String url = Config.ADD_ORDER_LINE + link;
//            new CollectManager().execute(url);
//
//
//
//        }
//
//
//    }

}