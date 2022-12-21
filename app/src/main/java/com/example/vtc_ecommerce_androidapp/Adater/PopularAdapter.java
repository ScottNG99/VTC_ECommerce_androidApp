package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.PageView.ProductDetailActivity;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private Context context;

    List<AllProducts> products = new ArrayList<>();

    public PopularAdapter(Context context, List<AllProducts> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_list,parent,false);
        PopularAdapter.ViewHolder viewHolder = new PopularAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllProducts product = products.get(position);

        holder.txtProductName.setText(product.getPro_name());
        holder.txtProductPrice.setText("$" + product.getPro_price());
        holder.txtProductScore.setText(product.getPro_score());
        Glide.with(context).load(product.getPro_image1()).into(holder.homeimageView);
        Glide.with(context).load(product.getPro_image2()).into(holder.hometestimg);
        Glide.with(context).load(product.getPro_image3()).into(holder.testimgtwo);
        holder.hometxtdesc.setText(product.getPro_desc());


        holder.homeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetailActivity.class);

                intent.putExtra("pName",product.getPro_name());
                intent.putExtra("pImage",product.getPro_image1());
                intent.putExtra("pPrice",product.getPro_price());
                intent.putExtra("pScore",product.getPro_score());
                intent.putExtra("pIntroduction",product.getPro_image2());
                intent.putExtra("pdesc",product.getPro_desc());
                intent.putExtra("sendActivity","ListAll");
                intent.putExtra("PID",product.getProductID());
                intent.putExtra("pIntroductiontwo",product.getPro_image3());
                intent.putExtra("sendActivity","Popular");

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView homeimageView;
        public TextView txtProductName;
        public TextView txtProductPrice;
        public TextView txtProductScore;

        public CardView homeCardView;
        private TextView hometxtdesc;
        private ImageView hometestimg;
        private ImageView testimgtwo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homeimageView = itemView.findViewById(R.id.imgproduct);
            txtProductName = itemView.findViewById(R.id.pname);
            txtProductPrice = itemView.findViewById(R.id.pprice);
            txtProductScore = itemView.findViewById(R.id.txtscore);
            homeCardView = itemView.findViewById(R.id.carviewthree);
            hometxtdesc = itemView.findViewById(R.id.homedesc);
            hometestimg = itemView.findViewById(R.id.homeImgtwo);
            testimgtwo = itemView.findViewById(R.id.homeImgthree);

        }
    }
}
