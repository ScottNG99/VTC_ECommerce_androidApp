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

import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.Student;
import com.example.vtc_ecommerce_androidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        studentNikeName = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUser_mobile();
        studentNumber = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getColleges();

        txtstudentNO.setText(studentNumber);
        txtNikeName.setText(studentNikeName);



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