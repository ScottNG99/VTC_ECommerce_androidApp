package com.example.vtc_ecommerce_androidapp.LoginAndRegisterPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.Student;
import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.Service.RequestHandler;
import com.example.vtc_ecommerce_androidapp.Service.URLs;
import com.example.vtc_ecommerce_androidapp.api.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class InputStudentDetailInfoActivity extends AppCompatActivity {
    private EditText edtname,edtphone,edtpassword,edtconfitmpwd,edtaddress;
    private Spinner spinnercampus;
    private Button btnregister;

    private String studentname,studentnum,stucourse;
    public String campus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_student_detail_info);



        edtname = findViewById(R.id.edtnikename);
        edtphone = findViewById(R.id.edtmobile);
        edtpassword = findViewById(R.id.edtpwd);
        edtconfitmpwd = findViewById(R.id.edtrepwd);
        edtaddress = findViewById(R.id.edtaddress);
        spinnercampus = findViewById(R.id.spinnercampus);
        btnregister =findViewById(R.id.btnconfirm);

        Intent intent = getIntent();
        studentname = intent.getStringExtra("studentName");
        studentnum = intent.getStringExtra("studentNum");
        stucourse = intent.getStringExtra("studentCourse");

        System.out.println("intent getstring  " + studentname +"  +  " + studentnum + "  +  "+ stucourse);



        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        spinnercampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                campus = spinnercampus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







    }



    private void registerUser() {

        final  String nickname = edtname.getText().toString().trim();
        final  String mobile = edtphone.getText().toString().trim();
        final  String address = edtaddress.getText().toString().trim();
        final  String colleges = campus;
        final  String password = edtpassword.getText().toString().trim();
        final  String confirmpwd = edtconfitmpwd.getText().toString().trim();

        if (TextUtils.isEmpty(nickname)) {
            edtname.setError("Please enter nick name");
            edtname.requestFocus();

            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            edtphone.setError("Please enter your phone number");
            edtphone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            edtaddress.setError("Please enter your address");
            edtaddress.requestFocus();
            return;
        }

        if (colleges.equals("---Please select the school location---")) {
            edtaddress.setError("Please choose your campus");
            edtaddress.requestFocus();
            return;
        }



        if (TextUtils.isEmpty(password)) {
            edtpassword.setError("Enter a password");
            edtpassword.requestFocus();
            return;
        }

        if (!confirmpwd.equals(password) ){
            edtconfitmpwd.setError("Your password matched");
            edtconfitmpwd.requestFocus();
            return;
        }



        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("student_name", studentname);
                params.put("studentID", studentnum);
                params.put("userpassword", password);
                params.put("colleges", colleges);
                params.put("user_mobile", mobile);
                params.put("user_nick_name", nickname);
                params.put("user_address", address);
                params.put("student_course", stucourse);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
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
                        createOrder();
                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();




    }

    private void createOrder() {

        int userid = SharedPrefManager.getInstance(getApplicationContext()).getStudent().getUserID();
        String orderStatus = "1"; //unpaid




        String link = "userID="+userid + "&total_price="+"0" + "&order_status="+orderStatus + "&product_delivery="+"test" + "&quantity="+"0";
        System.out.println("this step 1");


        String url = Config.ADD_ORDER + link;
        new CollectManager().execute(url);
    }
}