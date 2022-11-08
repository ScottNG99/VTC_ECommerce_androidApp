package com.example.vtc_ecommerce_androidapp.LoginAndRegisterPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.Student;
import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.Service.RequestHandler;
import com.example.vtc_ecommerce_androidapp.Service.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login_page extends AppCompatActivity {

    private EditText edtstudentNum,edtpassword;
    private Button tologin;
    private TextView toregister;
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
        toregister = findViewById(R.id.toregister);

        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this,Regiter_pageActivity.class);
                startActivity(intent);
            }
        });


        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(login_page.this,HomePage_Activity.class);
//                startActivity(intent);

                userLogin();

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

    private void userLogin() {

        final String cna = edtstudentNum.getText().toString().trim();
        final String password = edtpassword.getText().toString().trim();


        if (TextUtils.isEmpty(cna)) {
            edtstudentNum.setError("Please enter your Student ID");
            edtstudentNum.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtpassword.setError("Please enter your password");
            edtpassword.requestFocus();
            return;
        }


        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        Student user = new Student(
                                userJson.getInt("userID"),
                                userJson.getString("studentID"),
                                userJson.getString("student_name"),
                                userJson.getString("user_nick_name"),
                                userJson.getString("user_mobile"),
                                userJson.getString("colleges"),
                                userJson.getString("user_address"),
                                userJson.getString("student_course")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("studentID", cna);
                params.put("userpassword", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }
}