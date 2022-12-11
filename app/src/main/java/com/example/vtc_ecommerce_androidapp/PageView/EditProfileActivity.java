package com.example.vtc_ecommerce_androidapp.PageView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.Service.RequestHandler;
import com.example.vtc_ecommerce_androidapp.Service.URLs;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputEditText txtUserName,txtUserPwd,txtUserMP,txtUserAddress;
    AutoCompleteTextView CampusAutoTV;
    private ImageView imgback,imgcheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        CampusAutoTV = findViewById(R.id.CampusTextView);

        txtUserPwd = findViewById(R.id.edtUserpasswords);
        txtUserMP = findViewById(R.id.edtmobilephone);
        txtUserAddress =findViewById(R.id.edtuserAddress);
        imgback = findViewById(R.id.imgbackmyprofile);
        imgcheck =findViewById(R.id.confirmBtn);
        txtUserName =findViewById(R.id.edtUsername);



        ArrayList<String> customerList = getCampusList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditProfileActivity.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        CampusAutoTV.setAdapter(adapter);


        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this,MyProfileActivity.class);
                startActivity(intent);
            }
        });



        //submit button click event registration
        imgcheck.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                editProfile();

                Toast.makeText(getApplicationContext(), "Profile has been updated", Toast.LENGTH_LONG).show();

            }});
    }



    private ArrayList<String> getCampusList() {
        ArrayList<String> compus = new ArrayList<>();
        compus.add("IVE(CW)");
        compus.add("IVE(HW)");
        compus.add("IVE(KT)");
        compus.add("IVE(KC)");
        compus.add("IVE(LWL)");
        compus.add("IVE(MH)");
        compus.add("IVE(ST)");
        compus.add("IVE(TY)");
        compus.add("IVE(TM)");
        return compus;

    }

    private void editProfile(){
        final String name = txtUserName.getText().toString();
        final String pwd = txtUserPwd.getText().toString();
        final String address = txtUserAddress.getText().toString();
        final String phomeNo = txtUserMP.getText().toString();
        final String compus = CampusAutoTV.getText().toString();
        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        final String txtUserID = String.valueOf(userid);

        class EditProfile extends AsyncTask<Void, Void, String>{

            @Override
            protected String doInBackground(Void... voids) {

                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("userID", txtUserID);
                params.put("user_nick_name", name);
                params.put("userpassword", pwd);
                params.put("user_mobile", phomeNo);
                params.put("user_address", address);
                params.put("colleges", compus);

                //returing the response
                return requestHandler.sendPostRequest(Config.UPDATE_USER_PROFILE, params);

            }
        }
        EditProfile ul = new EditProfile();
        ul.execute();
    }


}