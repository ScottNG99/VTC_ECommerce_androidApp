package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.DialogFragment.FilterDialogFragment;
import com.example.vtc_ecommerce_androidapp.DialogFragment.ReviewDialogFragment;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Order;
import com.example.vtc_ecommerce_androidapp.PageView.PaymentPageActivity;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;
import com.google.android.material.button.MaterialButton;
import com.example.vtc_ecommerce_androidapp.PageView.OrderPageActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private Context context;

    List<Order> orders = new ArrayList<>();



    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_list_all_style,parent,false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Order order = orders.get(position);

        holder.txtorderpname.setText(order.getPro_name());
        holder.txtorderpprice.setText("$" + order.getPro_price());
        holder.txtorderproductqty.setText("X" + order.getPro_num());

        Glide.with(context).load(order.getPro_image1()).into(holder.orderproductimg);

        String getStatus = order.getOrder_status();





        if (getStatus.equals("2")){
            holder.orderproductstatus.setText("waiting");
            holder.orderproductstatus.setTextColor(Color.parseColor("#F36D31"));

            holder.btnreceived.setVisibility(View.GONE);
            holder.btncancel.setVisibility(View.VISIBLE);

        } else if (getStatus.equals("4")){
            holder.orderproductstatus.setText("Received");
            holder.orderproductstatus.setTextColor(Color.parseColor("#3EA7FE"));
            holder.btnreview.setVisibility(View.VISIBLE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.btncancel.setVisibility(View.VISIBLE);
            holder.btncancel.setText("Cancel Record");

        }else if (getStatus.equals("1")){
            holder.btncancel.setVisibility(View.GONE);
            holder.btnreceived.setVisibility(View.GONE);
            holder.btntopay.setVisibility(View.VISIBLE);
            holder.orderproductstatus.setText("Unpaid");
            holder.orderproductstatus.setTextColor(Color.parseColor("#92A3B6"));

        }else if (getStatus.equals("3")){
            holder.orderproductstatus.setText("Shipped");
            holder.orderproductstatus.setTextColor(Color.parseColor("#71CB4F"));
            holder.btncancel.setVisibility(View.GONE);
        }



        int userid = SharedPrefManager.getInstance(context.getApplicationContext()).getStudent().getUserID();
        String txtUserID = String.valueOf(userid);


        holder.btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selection = "cancel";

                String msg = order.getOrder_status();

                Toast.makeText(context,"This product has been canceled",Toast.LENGTH_LONG).show();

                apiCancelAndUpdate(order.getOrderlineID(),selection,txtUserID,order.getOrderID());

                updatePage(msg);






            }
        });

        holder.btnreceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selection = "received";

                String msg = order.getOrder_status();


                apiCancelAndUpdate(order.getOrderlineID(),selection,txtUserID,order.getOrderID());
                Toast.makeText(context,"Received",Toast.LENGTH_LONG).show();

                updatePage(msg);





            }
        });

        holder.btntopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PaymentPageActivity.class);
                intent.putExtra("proPrice",order.getPro_price());
                intent.putExtra("sendMsgToBuy","paided");
                intent.putExtra("sendOrderID",order.getOrderID());
                context.startActivity(intent);

            }
        });

        holder.btnreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                ReviewDialogFragment newFragment = new ReviewDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("pID",order.getProductID());
                newFragment.setArguments(bundle);


                newFragment.show(ft, "dialog");
            }
        });


    }

    private void updatePage(String msg) {


        Intent intent = new Intent("message");
        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
        intent.putExtra("msg",msg);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


    }


    private void apiCancelAndUpdate(String orderlineID, String selection, String txtUserID, String orderID) {

        String link = "orderlineID="+orderlineID + "&selection="+selection + "&userID="+txtUserID + "&orderID="+orderID;
        String url = Config.UPDATE_AND_CANCEL_ORDER + link;
        new CollectManager().execute(url);





    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView orderproductimg;
        TextView txtorderpname,txtorderpprice,txtorderproductqty,orderproductstatus;
        MaterialButton btncancel,btnreceived,btntopay,btnreview;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderproductimg = itemView.findViewById(R.id.orpimage);
            txtorderpname = itemView.findViewById(R.id.orppname);
            txtorderpprice = itemView.findViewById(R.id.orpprice);
            txtorderproductqty = itemView.findViewById(R.id.orProductQty);
            orderproductstatus = itemView.findViewById(R.id.orderStatus);
            btncancel = itemView.findViewById(R.id.cancel_button);
            btnreceived = itemView.findViewById(R.id.confirm_button);
            btntopay = itemView.findViewById(R.id.toPaid_button);
            btnreview = itemView.findViewById(R.id.addReview);

        }
    }

}
