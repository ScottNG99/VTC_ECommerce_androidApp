package com.example.vtc_ecommerce_androidapp.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.vtc_ecommerce_androidapp.PageView.HomePage_Activity;
import com.example.vtc_ecommerce_androidapp.PageView.OrderPageActivity;
import com.example.vtc_ecommerce_androidapp.R;

public class SuccessfulAlertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.order_successful_dialog, container, false);
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

        Button btnViewOrder = view.findViewById(R.id.toOrderPage);
        Button btnCancelDialog = view.findViewById(R.id.closeDialog);



        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getActivity(), HomePage_Activity.class);
                startActivity(intent);
            }
        });

        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getActivity(), OrderPageActivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();

    }

//    @Override
//    public void onStart() {
//        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        getDialog().getWindow().setAttributes(params);
//        getDialog().getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
//        super.onStart();
//    }


}
