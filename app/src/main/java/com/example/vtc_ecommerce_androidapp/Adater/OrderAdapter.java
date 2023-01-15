package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Order;
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
        } else if (getStatus.equals("4")){
            holder.orderproductstatus.setText("Received");
            holder.orderproductstatus.setTextColor(Color.parseColor("#3EA7FE"));

            holder.btnreceived.setVisibility(View.GONE);
            holder.btncancel.setText("Cancel Record");

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
        MaterialButton btncancel,btnreceived;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderproductimg = itemView.findViewById(R.id.orpimage);
            txtorderpname = itemView.findViewById(R.id.orppname);
            txtorderpprice = itemView.findViewById(R.id.orpprice);
            txtorderproductqty = itemView.findViewById(R.id.orProductQty);
            orderproductstatus = itemView.findViewById(R.id.orderStatus);
            btncancel = itemView.findViewById(R.id.cancel_button);
            btnreceived = itemView.findViewById(R.id.confirm_button);

        }
    }

}
