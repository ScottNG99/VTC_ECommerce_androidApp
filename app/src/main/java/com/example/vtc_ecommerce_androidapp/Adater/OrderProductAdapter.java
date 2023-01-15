package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Cart;
import com.example.vtc_ecommerce_androidapp.ModelClass.OrderProduct;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private Context context;

    List<OrderProduct> orderProducts = new ArrayList<>();

    public OrderProductAdapter(Context context, List<OrderProduct> orderProducts) {
        this.context = context;
        this.orderProducts = orderProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_style,parent,false);
        OrderProductAdapter.ViewHolder viewHolder = new OrderProductAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderProduct orderProduct = orderProducts.get(position);


        Glide.with(context).load(orderProduct.getPro_image1()).into(holder.orderimageView);
        holder.orderTxtProductName.setText(orderProduct.getPro_name());
        holder.orderTxtProductPrice.setText("$"+orderProduct.getPro_price());
        holder.orderTxtProductQty.setText("X"+orderProduct.getGoods_count());






    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView orderimageView;

        public TextView orderTxtProductName;
        public TextView orderTxtProductPrice;
        public TextView orderTxtProductQty;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderimageView = itemView.findViewById(R.id.orderimage);
            orderTxtProductName = itemView.findViewById(R.id.Orderpname);
            orderTxtProductPrice = itemView.findViewById(R.id.Orderprice);
            orderTxtProductQty = itemView.findViewById(R.id.orderProductQty);

        }
    }
}
