package com.example.vtc_ecommerce_androidapp.Manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.vtc_ecommerce_androidapp.LoginAndRegisterPage.login_page;
import com.example.vtc_ecommerce_androidapp.ModelClass.Student;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_STUDENTNAME = "keystudentname";
    private static final String KEY_NICKNAME = "keynickname";
    private static final String KEY_STUDENTID = "keystudentID";
    private static final String KEY_USERID = "keyuserid";
    private static final String KEY_MOBILE = "keymobile";
    private static final String KEY_COLLEGES = "keycolleges";
    private static final String KEY_COURSE = "keycourse";
    private static final String KEY_ADDRESS = "keyaddress";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Student student) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USERID, student.getUserID());
        editor.putString(KEY_STUDENTNAME, student.getStudent_name());
        editor.putString(KEY_NICKNAME, student.getUser_nick_name());
        editor.putString(KEY_STUDENTID, student.getStudentID());
        editor.putString(KEY_MOBILE, student.getUser_mobile());
        editor.putString(KEY_COLLEGES, student.getColleges());
        editor.putString(KEY_COURSE, student.getStudent_course());
        editor.putString(KEY_ADDRESS, student.getUser_address());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_STUDENTID, null) != null;
    }

    //this method will give the logged in user
    public Student getStudent() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Student(
                sharedPreferences.getInt(KEY_USERID, -1),
                sharedPreferences.getString(KEY_STUDENTNAME, null),
                sharedPreferences.getString(KEY_NICKNAME, null),
                sharedPreferences.getString(KEY_STUDENTID, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_COLLEGES, null),
                sharedPreferences.getString(KEY_COURSE, null),
                sharedPreferences.getString(KEY_ADDRESS, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        mCtx.startActivity(new Intent(mCtx, login_page.class));
    }



}
