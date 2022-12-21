package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vtc_ecommerce_androidapp.Adater.CollectAdapter;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Student;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyProfileActivity extends AppCompatActivity {

    private TextView txtNikeName,txtstudentNO;
    private BottomNavigationView buttomNavbar;
    private ImageView btnEdit,btnlogot,btnMyCollection;

    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        txtNikeName = findViewById(R.id.userNikeName);
        txtstudentNO = findViewById(R.id.userCND);
        btnlogot = findViewById(R.id.tologout);
        btnEdit = findViewById(R.id.toedit);
        btnMyCollection = findViewById(R.id.tomycollection);


        buttomNavbar = findViewById(R.id.ButtomnavView);
        buttomNavbar.setSelectedItemId(R.id.user);

        String studentNikeName,studentNumber;

        showProfile();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });


        btnMyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this,CollectActivity.class);
                startActivity(intent);
            }
        });


        buttomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.user:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(MyProfileActivity.this,HomePage_Activity.class));
                        return true;

                    case R.id.all:
                        startActivity(new Intent(MyProfileActivity.this,ListAllProductActivity.class));
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(MyProfileActivity.this,MyCartActivity.class));
                        return true;

                }
                return false;
            }
        });


        btnlogot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNormalDialog();
            }
        });




        //studentNikeName = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUser_mobile();
        studentNumber = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getColleges();

        txtstudentNO.setText(studentNumber);






    }

    private void showProfile() {

        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        String path = "userID=" + String.valueOf(userid);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_USER_PROFILE+path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String UserName = object.getString("user_nick_name");
                                System.out.println("get uName " + UserName);

                                txtNikeName.setText(UserName);



                            }


                        }catch (Exception e){

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(MyProfileActivity.this, error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        Volley.newRequestQueue(MyProfileActivity.this).add(stringRequest);

    }

    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MyProfileActivity.this);
        normalDialog.setIcon(R.drawable.logout);
        normalDialog.setTitle("Sign Out");
        normalDialog.setMessage("Are you sure you want to log out?");
        normalDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        dialog.dismiss();
                    }
                });
        normalDialog.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();

    }
}