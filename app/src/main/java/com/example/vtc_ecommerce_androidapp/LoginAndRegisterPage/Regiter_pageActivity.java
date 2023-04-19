package com.example.vtc_ecommerce_androidapp.LoginAndRegisterPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vtc_ecommerce_androidapp.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.Calendar;


public class Regiter_pageActivity extends AppCompatActivity {

    ImageView image,imgback;
    Button btnselectphoto,btncheck,btncomfirm;
    TextView txtshowname,txtshownumber,txtshowcourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_page);

        image = findViewById(R.id.iv_image);
        btnselectphoto = findViewById(R.id.btnselect);
        txtshowname = findViewById(R.id.stuname);
        txtshownumber= findViewById(R.id.stuid);
        btncheck = findViewById(R.id.tocheck);
        imgback = findViewById(R.id.backtologin);
        btncomfirm = findViewById(R.id.btnconfirm);
        txtshowcourse = findViewById(R.id.stucourse);

        btnselectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,2);
            }
        });

        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detect();
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Regiter_pageActivity.this,login_page.class);
                startActivity(intent);
            }
        });




    }

    public void detect() {



       TextRecognizer recognizer = new TextRecognizer.Builder(Regiter_pageActivity.this).build();

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();

        String[] listStudentcardinfo = new String[sparseArray.size()];


        for (int i =0; i<sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();

            listStudentcardinfo[i] = str;
            stringBuilder.append(str);
            System.out.println("get text: " + listStudentcardinfo[i]);

        }

        //System.out.println("show 44 " + listStudentcardinfo[0]);


        String [] arrsplitthree = listStudentcardinfo[0].split("\n+");
        String [] arrsplittl= new String[arrsplitthree.length];
        for (int i =0; i<arrsplitthree.length; i++){
            arrsplittl[i] = arrsplitthree[i];
            //get student ID [1]
            System.out.println("studnID = " + arrsplittl[i]);
        }



        String [] studentidlist = arrsplittl[6].split("\\s+");
        System.out.println("can show id?? " + studentidlist[0]);





//        boolean isFound;
//        String[] filterArray = new String[listStudentcardinfo.length];
//        filterArray[0] = listStudentcardinfo[0];
//        for (int i = 0; i<listStudentcardinfo.length; i++){
//
//            if (isFound = listStudentcardinfo[i].contains("Valid")){
//                filterArray[1] = listStudentcardinfo[i];
//            }
//
//
//
//        }

        boolean isFound;
        String[] filterArray = new String[arrsplittl.length];
        filterArray[0] = arrsplittl[0];
        for (int i = 0; i<arrsplittl.length; i++){

            if (isFound = arrsplittl[i].contains("Valid")){
                filterArray[1] = arrsplittl[i];
                System.out.println("inter this11 " + filterArray[1]);
            }



        }


        String [] arrsplit = filterArray[1].split("\\s+");
        String [] arrsplittwo = new String[arrsplit.length];
        for (int i =0; i<arrsplit.length; i++){
            arrsplittwo[i] = arrsplit[i];
            //get Valid Through 08.2023 Full Time
            System.out.println("assr = " + arrsplittwo[i]);
        }

        String[] timearry = arrsplittwo[2].split("\\.");
        String [] filtertime = new String[timearry.length];

        for (int n=0; n <timearry.length; n++){
            filtertime[n] = timearry[n];
            //get 2023 [1]
            System.out.println("xxxd " + filtertime[n]);
        }



        //final array student info

        String studentname = arrsplittl[0];
        String studentnum =  studentidlist[0];
        String studentValid = filtertime[1];
        String studentCourse = arrsplittl[7];



        txtshowname.setText(studentname);
        txtshownumber.setText(studentnum);
        txtshowcourse.setText(studentCourse);
        //Check if the student ID is valid

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int convertyear = Integer.parseInt(studentValid);

        System.out.println("year lis=  " + year + "  -  " + convertyear);

        if (convertyear >= year){

            btncomfirm.setBackgroundResource(R.drawable.btn_login_color);
            btncomfirm.setEnabled(true);
            Toast.makeText(this,"Student card is valid",Toast.LENGTH_SHORT).show();
        }else {
            btnselectphoto.setBackgroundResource(R.drawable.btn_login_color);
            Toast.makeText(this,"Student card has expired",Toast.LENGTH_SHORT).show();
        }

        btncomfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassDataToDetailActivity(studentname,studentnum,studentCourse);
            }
        });











    }

    private void PassDataToDetailActivity(String sname,String snum,String scourse) {

        Intent intent = new Intent(Regiter_pageActivity.this,InputStudentDetailInfoActivity.class);

        intent.putExtra("studentName",sname);
        intent.putExtra("studentNum",snum);
        intent.putExtra("studentCourse",scourse);

        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 ){
            Log.e(this.getClass().getName(),"Result: " + data.toString());

            if (data != null){
                Uri uri = data.getData();
                image.setImageURI(uri);
                Log.e(this.getClass().getName(),"Uri: " + String.valueOf(uri));

                btnselectphoto.setBackgroundResource(R.drawable.btn_check_color);
                btncheck.setEnabled(true);
                btncheck.setBackgroundResource(R.drawable.btn_login_color);




            }

        }
    }
}