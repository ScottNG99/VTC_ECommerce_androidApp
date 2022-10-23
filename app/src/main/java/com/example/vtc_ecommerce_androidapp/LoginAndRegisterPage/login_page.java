package com.example.vtc_ecommerce_androidapp.LoginAndRegisterPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.R;

public class login_page extends AppCompatActivity {

    private EditText edtstudentNum,edtpassword;
    private Button tologin;
    private ImageView hiddenandshowpwd;

    int cliknum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        edtstudentNum = findViewById(R.id.edtlogin);
        edtpassword = findViewById(R.id.edtpassword);
        tologin = findViewById(R.id.btnlogin);
        hiddenandshowpwd = findViewById(R.id.hiddenpwd);


        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this,HomePage_Activity.class);
                startActivity(intent);

            }
        });



        hiddenandshowpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cliknum++;

                if (cliknum %2 == 0){

                    edtpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    edtpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });






    }
}