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

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.PageView.ProductDetailActivity;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.api.Config;

import java.util.ArrayList;
import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {

    private Context context;

    List<AllProducts> products = new ArrayList<>();

    public CollectAdapter(Context context, List<AllProducts> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CollectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collect_list_style,parent,false);
        CollectAdapter.ViewHolder viewHolder = new CollectAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectAdapter.ViewHolder holder, int position) {

        AllProducts product = products.get(position);

        holder.collectTxtProductName.setText(product.getPro_name());
        holder.collectTxtProductPrice.setText("$" + product.getPro_price());

        Glide.with(context).load(product.getPro_image1()).into(holder.imageView);

        holder.collectCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetailActivity.class);

                intent.putExtra("pName",product.getPro_name());
                intent.putExtra("pImage",product.getPro_image1());
                intent.putExtra("pPrice",product.getPro_price());
                intent.putExtra("pScore",product.getPro_score());
                intent.putExtra("pIntroduction",product.getPro_image2());
                intent.putExtra("pdesc",product.getPro_desc());
                intent.putExtra("cID",product.getCollectID());
                intent.putExtra("sendActivity","Collect");

                context.startActivity(intent);

            }
        });

        holder.collectHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getcollectID = product.getCollectID();




                String link = Config.DELETE_COLLECT_URL + getcollectID; //using this IP for Genymotion emulator
                new CollectManager().execute(link);
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView collectTxtProductName;
        public TextView collectTxtProductPrice;
        public TextView collectTxtProductScore;
        public CardView collectCardView;
        private TextView collecttxtdesc;
        private ImageView collecttestimg,collectHeart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.collectproduct);
            collectTxtProductName = itemView.findViewById(R.id.collectname);
            collectTxtProductPrice = itemView.findViewById(R.id.collectprice);
            collectTxtProductScore = itemView.findViewById(R.id.collectscore);
            collectCardView = itemView.findViewById(R.id.carviewthree);
            collecttxtdesc = itemView.findViewById(R.id.collectdesc);
            collecttestimg = itemView.findViewById(R.id.collectimgtwo);
            collectHeart = itemView.findViewById(R.id.collecticon);
        }
    }
}
