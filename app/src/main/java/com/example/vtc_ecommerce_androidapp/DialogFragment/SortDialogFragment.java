package com.example.vtc_ecommerce_androidapp.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.PageView.OrderPageActivity;
import com.example.vtc_ecommerce_androidapp.R;

public class SortDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_sort_fragment, container, false);
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

        LinearLayout sortlowestprice = view.findViewById(R.id.sortlowestprice);
        LinearLayout sorthighestprice = view.findViewById(R.id.sorthighestprice);
        LinearLayout sorthighestrated = view.findViewById(R.id.sorthighestscore);




        sorthighestprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendSortMsg;
                sendSortMsg = "highest";
                sendMsg(sendSortMsg);
                dismiss();
            }
        });

        sortlowestprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendSortMsg;
                sendSortMsg = "lowest";
                sendMsg(sendSortMsg);
                dismiss();
            }
        });

        sorthighestrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendSortMsg;
                sendSortMsg = "highestScore";
                sendMsg(sendSortMsg);
                dismiss();

            }
        });








    }

    private void sendMsg(String sendSortMsg) {
        Intent intent = new Intent("Sortmessage");

        intent.putExtra("msg",sendSortMsg);


        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
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
