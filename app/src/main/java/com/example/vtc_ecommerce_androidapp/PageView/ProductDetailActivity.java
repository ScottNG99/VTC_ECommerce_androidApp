package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.R;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imgProduct,back,imgIntroduction,imgCollect;
    private TextView txtProductNamr,txtScore,txtPrice,txtdescrition,txttotalprice,txttotalQuantity;
    ImageView addQuantity,minusQuantity;

    //Default quantity is 1;
    int quantity = 1;

    // Cannot buy more than 10
    // Set quantity limits later based on inventory
    int upperLimit = 10;
    int totalPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.productImg);
        back = findViewById(R.id.backalllist);
        imgIntroduction = findViewById(R.id.imgtwo);
        imgCollect = findViewById(R.id.collect);
        txtProductNamr = findViewById(R.id.productName);
        txtScore = findViewById(R.id.txtscore);
        txtPrice = findViewById(R.id.productPrices);
        txtdescrition= findViewById(R.id.descProduct);
        txttotalprice = findViewById(R.id.totalPrice);
        txttotalQuantity = findViewById(R.id.showQuantity);
        addQuantity = findViewById(R.id.add);
        minusQuantity = findViewById(R.id.minus);

        Intent intent = getIntent();
        String pImg = intent.getStringExtra("pImage");
        String pItro = intent.getStringExtra("pIntroduction");
        String pName = intent.getStringExtra("pName");
        String pScore = intent.getStringExtra("pScore");
        String pDesce = intent.getStringExtra("pdesc");
        String pPrice = intent.getStringExtra("pPrice");



        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity == upperLimit) {

                    Toast.makeText(ProductDetailActivity.this, "Cant Order More than 10", Toast.LENGTH_SHORT).show();
                    txttotalQuantity.setText(String.valueOf(quantity));


                }else {
                    quantity++;
                    txttotalQuantity.setText(String.valueOf(quantity));
                    totalPrice = quantity * Integer.parseInt(pPrice);
                    txttotalprice.setText(String.valueOf(totalPrice));
                }



            }
        });

        minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity == 1) {

                    Toast.makeText(ProductDetailActivity.this, "A minimum of one", Toast.LENGTH_SHORT).show();
                    txttotalQuantity.setText(String.valueOf(quantity));
                    quantity = 1;


                } else {

                    quantity--;
                    txttotalQuantity.setText(String.valueOf(quantity));
                    totalPrice = totalPrice - Integer.parseInt(pPrice);
                    txttotalprice.setText(String.valueOf(totalPrice));

                }

            }
        });


        if (intent !=null){

            Glide.with(ProductDetailActivity.this).load(pImg).into(imgProduct);
            Glide.with(ProductDetailActivity.this).load(pItro).into(imgIntroduction);

            txtProductNamr.setText(pName);
            txtScore.setText(pScore);
            txtPrice.setText("$"+pPrice);
            txtdescrition.setText(pDesce);
            txttotalprice.setText("$"+pPrice);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this,ListAllProductActivity.class);
                startActivity(intent);
            }
        });


    }
}