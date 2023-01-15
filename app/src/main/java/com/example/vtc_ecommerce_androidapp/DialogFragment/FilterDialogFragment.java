package com.example.vtc_ecommerce_androidapp.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.PageView.CheckOutActivity;
import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.PageView.OrderPageActivity;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.ArrayList;

public class FilterDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//
//        AlertDialog alertDialog = builder.create();
//        Window window = alertDialog.getWindow();
//        window.setBackgroundDrawableResource(android.R.color.white);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.BOTTOM;
//        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(wlp);
//
//
//
//
//
//        return alertDialog;
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.filter_dialog, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        }

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         AutoCompleteTextView categoryAutoView = view.findViewById(R.id.filtercategoryTxtView);
         ArrayList<String> categoryList = getCategoryList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,categoryList);
        categoryAutoView.setAdapter(adapter);






        EditText edtlowestPrice = view.findViewById(R.id.edtLowestPrice);
        EditText edthighestPrice = view.findViewById(R.id.edthighestprice);

        Button btnResrt = view.findViewById(R.id.resetDialog);
        Button btnFinish = view.findViewById(R.id.finishDialog);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getrec = categoryAutoView.getText().toString();
                String categoryID = getcategoryID(getrec);

                String lowestPrice = edtlowestPrice.getText().toString().trim();
                String highestPrice = edthighestPrice.getText().toString().trim();

                if (TextUtils.isEmpty(lowestPrice)){
                    lowestPrice = "0";
                }
                if (TextUtils.isEmpty(highestPrice)){
                    highestPrice = "0";
                }


                sendLocalBroadcastmsg(categoryID, lowestPrice, highestPrice);

                dismiss();
            }
        });

        btnResrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryID = "0";
                String lowestPrice = "0";
                String highestPrice = "0";

                sendLocalBroadcastmsg(categoryID, lowestPrice, highestPrice);
                dismiss();
            }
        });














    }

    private void sendLocalBroadcastmsg(String categoryID, String lowestPrice, String highestPrice) {
        Intent intent = new Intent("Filtermessage");

        intent.putExtra("categoryID",categoryID);
        intent.putExtra("lowestPrice",lowestPrice);
        intent.putExtra("highestPrice",highestPrice);

        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


    }

    private String getcategoryID(String getrec) {
        String getcategoryID = "";

        if (getrec.equals("Windows")){
            getcategoryID = "1";
        }else if (getrec.equals("Mac")){
            getcategoryID = "2";
        }else if (getrec.equals("Tablet")){
            getcategoryID = "10";
        }

        return getcategoryID;
    }

    private ArrayList<String> getCategoryList() {

        ArrayList<String> category = new ArrayList<>();


        category.add("Windows");
        category.add("Mac");
        category.add("Tablet");


        return category;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }




}
