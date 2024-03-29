package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.OrderProductAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.OrderProduct;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    private AutoCompleteTextView AddressAutoTV;
    private RecyclerView recyOrderListView;
    private TextView txtAmount,txtPromom,txtTotalAmount;
    private Button btnToPayment;
    private ImageView imgBacktoDetail;
    private String campusAddrss, userAddress;

    private OrderProductAdapter Orderadapter;
    private List<OrderProduct> orderProductList;
    private RecyclerView.LayoutManager layoutManager;

    private int sendAproductAamount = 0;
    private int sendproductsAamount = 0;

    private int apiAmount = 0;
    private int apiGoodsAmount = 0 ;
    private ArrayList<String> productIDList;
    private ArrayList<String> intentPaymentQty;
    private ArrayList<String> intentPaymentProductTotalAmount;

    String getOrderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        AddressAutoTV = findViewById(R.id.shippingAddressTxtView);
        recyOrderListView = findViewById(R.id.recListOrder);
        txtAmount = findViewById(R.id.orderAmount);
        txtPromom = findViewById(R.id.orderPromo);
        txtTotalAmount = findViewById(R.id.orderTotalAmount);
        btnToPayment = findViewById(R.id.topayment);
        imgBacktoDetail = findViewById(R.id.backCartlist);

        recyOrderListView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyOrderListView.setLayoutManager(layoutManager);

        productIDList = new ArrayList<String>();
        intentPaymentQty = new ArrayList<String>();
        intentPaymentProductTotalAmount = new ArrayList<String>();


        ArrayList<String> AddressList = getAddressList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CheckOutActivity.this, android.R.layout.simple_spinner_item, AddressList);

        //Set adapter
        AddressAutoTV.setAdapter(adapter);

        Intent intent = getIntent();
//        String pImg = intent.getStringExtra("productImg");
//        String pName = intent.getStringExtra("productName");
//        String pPrice = intent.getStringExtra("productPrice");
//        String pQty = intent.getStringExtra("productQty");
        int getCheck = intent.getIntExtra("checkIntent",0);

        //System.out.println("get check page " + getCheck);

        orderProductList = new ArrayList<>();


//        OrderProduct orderProduct = new OrderProduct(pName,pPrice,pImg,pQty);
//        orderProductList.add(orderProduct);


//        Orderadapter = new OrderProductAdapter(CheckOutActivity.this,orderProductList);
//        recyOrderListView.setAdapter(Orderadapter);



        btnToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getrec=AddressAutoTV.getText().toString();


                if (getrec.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please select your shipping address!",Toast.LENGTH_SHORT).show();
                }else {

                    if (sendAproductAamount == 0){
                        apiAmount = sendproductsAamount;
                        CreateOrder(apiAmount,getrec);



                    }else {
                        apiAmount = sendAproductAamount;
                        CreateOrder(apiAmount,getrec);



                    }


//                    System.out.println("get order id fre " + getOrderID);
//
//                    Intent intent3 = new Intent(CheckOutActivity.this,PaymentPageActivity.class);
//                    intent3.putExtra("sendAproductAamount" , sendAproductAamount);
//                    intent3.putExtra("sendproductsAamount",sendproductsAamount);
//                    intent3.putStringArrayListExtra("productIDList",productIDList);
//                    intent3.putStringArrayListExtra("productTotalPrice",intentPaymentProductTotalAmount);
//                    intent3.putStringArrayListExtra("productQTY",intentPaymentQty);
//                    intent3.putExtra("sendMsgToBuy","paided");
//                    intent3.putExtra("sendOrderID",getOrderID);
//                    startActivityForResult(intent3,0);



                }

            }
        });




        imgBacktoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2;

                if (getCheck == 1){

                    intent2 = new Intent(CheckOutActivity.this,ProductDetailActivity.class);
                    setResult(RESULT_OK,intent2);
                    finish();

                }else if (getCheck == 2){

                    intent2 = new Intent(CheckOutActivity.this,MyCartActivity.class);
                    startActivity(intent2);
                }

            }
        });

        if (getCheck == 1){

            String pImg = intent.getStringExtra("productImg");
            String pName = intent.getStringExtra("productName");
            String pPrice = intent.getStringExtra("productPrice");
            String pQty = intent.getStringExtra("productQty");
            String pid = intent.getStringExtra("productID");



            OrderProduct orderProduct = new OrderProduct(pName,pPrice,pImg,pQty,pid);
            orderProductList.add(orderProduct);

            productIDList.add(pid);
            intentPaymentQty.add(pQty);
            apiGoodsAmount = Integer.parseInt(pQty);

            Orderadapter = new OrderProductAdapter(CheckOutActivity.this,orderProductList);
            recyOrderListView.setAdapter(Orderadapter);

            int amount = Integer.parseInt(pPrice) * Integer.parseInt(pQty);
            intentPaymentProductTotalAmount.add(String.valueOf(amount));

            txtAmount.setText("$"+String.valueOf(amount));
            txtTotalAmount.setText("$"+String.valueOf(amount));

            sendAproductAamount = amount;




        }else if (getCheck == 2){

//            OrderProduct orderProducts = (OrderProduct) getIntent().getSerializableExtra("OrderAllDetails");
//            orderProductList.add(orderProducts);

            orderProductList =(ArrayList<OrderProduct>) getIntent().getSerializableExtra("orderList");

            for (int i = 0; i<orderProductList.size(); i++){
                productIDList.add(orderProductList.get(i).getProductID());
                intentPaymentQty.add(orderProductList.get(i).getGoods_count());
                String caltotalAmount = String.valueOf(Integer.valueOf(orderProductList.get(i).getPro_price()) * Integer.valueOf(orderProductList.get(i).getGoods_count()));
                intentPaymentProductTotalAmount.add(caltotalAmount);
                System.out.println("get list " + orderProductList.get(i).getProductID());
            }

            Orderadapter = new OrderProductAdapter(CheckOutActivity.this,orderProductList);
            recyOrderListView.setAdapter(Orderadapter);

            int getAmount = intent.getIntExtra("OrderAmount",0);
            int getgoodsAmount = intent.getIntExtra("OrderGoodsAmount",0);

            apiGoodsAmount = getgoodsAmount;

            txtAmount.setText("$"+String.valueOf(getAmount));
            txtTotalAmount.setText("$"+String.valueOf(getAmount));

            sendproductsAamount = getAmount;


        }



    }


    private void CreateOrder(int totalAmount, String deliveryAdreess) {

        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        String orderStatus = "1"; //unpaid
        String TotalQty = String.valueOf(apiGoodsAmount);



        String link = "userID="+userid + "&total_price="+totalAmount + "&order_status="+orderStatus + "&product_delivery="+deliveryAdreess + "&quantity="+TotalQty;
        System.out.println("this step 1");


        String url = Config.ADD_ORDER + link;
        new CollectManager().execute(url);

        getApiOrderID();




    }


    private void getApiOrderID() {

        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        String path = "userID=" + String.valueOf(userid);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_ORDERID+path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String orderid = object.getString("orderID");

                                getOrderID = orderid;
                                System.out.println("this step 2 " + getOrderID);


                            }


                            createOrderLine(getOrderID);





                        }catch (Exception e){

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(CheckOutActivity.this, error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        Volley.newRequestQueue(CheckOutActivity.this).add(stringRequest);






    }


    private void createOrderLine(String orderID) {

        for (int n=0;n<productIDList.size();n++){

            String link = "productID="+productIDList.get(n) + "&pro_total_price="+intentPaymentProductTotalAmount.get(n) + "&pro_num="+intentPaymentQty.get(n) + "&orderID="+orderID;



            String url = Config.ADD_ORDER_LINE + link;
            new CollectManager().execute(url);

            System.out.println("get order id fre " + orderID);

            Intent intent3 = new Intent(CheckOutActivity.this,PaymentPageActivity.class);
            intent3.putExtra("sendAproductAamount" , sendAproductAamount);
            intent3.putExtra("sendproductsAamount",sendproductsAamount);
            intent3.putStringArrayListExtra("productIDList",productIDList);
            intent3.putStringArrayListExtra("productTotalPrice",intentPaymentProductTotalAmount);
            intent3.putStringArrayListExtra("productQTY",intentPaymentQty);
            intent3.putExtra("sendMsgToBuy","paided");
            intent3.putExtra("sendOrderID",orderID);
            startActivityForResult(intent3,0);



        }


    }




    private ArrayList<String> getAddressList() {
        ArrayList<String> compus = new ArrayList<>();


        campusAddrss = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getStudentID();
        //userAddress = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getStudent_name();
        userAddress = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getStudent_course();

        compus.add(campusAddrss);
        compus.add(userAddress);

        return compus;
    }


}