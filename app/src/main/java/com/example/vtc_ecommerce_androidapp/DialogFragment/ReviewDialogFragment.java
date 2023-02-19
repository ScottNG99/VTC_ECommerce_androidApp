package com.example.vtc_ecommerce_androidapp.DialogFragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.PageView.OrderPageActivity;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

public class ReviewDialogFragment extends DialogFragment {

    String rate;
    String getpID;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        return super.onCreateDialog(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.review_dialog, container, false);
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

        RatingBar ratingBar = view.findViewById(R.id.ratingbar);
        EditText edtcomtent = view.findViewById(R.id.et_notice);
        Button btnsend = view.findViewById(R.id.sendreviewbtn);



        Bundle bundle = getArguments();
        getpID = bundle.getString("pID");


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //Toast.makeText(getActivity(),"rating:" + String.valueOf(v),Toast.LENGTH_SHORT).show();
                rate = String.valueOf(v);
            }
        });


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userCommentProduct = edtcomtent.getText().toString().trim();
                if (TextUtils.isEmpty(userCommentProduct)) {
                    edtcomtent.setError("please share your opinion");
                    edtcomtent.requestFocus();
                    return;
                }else {
                    int userid = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getStudent().getUserID();
                    String txtUserID = String.valueOf(userid);

                    String link = "userID="+txtUserID + "&productID="+getpID + "&rating="+rate + "&comment="+userCommentProduct;
                    String url = Config.ADD_RATING + link;

                    new CollectManager().execute(url);
                    dismiss();
                }

            }
        });








    }




    @Override
    public void onResume() {
        super.onResume();

    }



    @Override
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
