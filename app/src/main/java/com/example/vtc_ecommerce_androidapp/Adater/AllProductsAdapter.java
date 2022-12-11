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
import com.example.vtc_ecommerce_androidapp.ModelClass.product;
import com.example.vtc_ecommerce_androidapp.PageView.ProductDetailActivity;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ViewHolder> {

    private Context context;

    List<AllProducts> products = new ArrayList<>();

    public AllProductsAdapter(Context context, List<AllProducts> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public AllProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item_layout,parent,false);
        AllProductsAdapter.ViewHolder viewHolder = new AllProductsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductsAdapter.ViewHolder holder, int position) {

        AllProducts product = products.get(position);

        holder.allTxtProductName.setText(product.getPro_name());
        holder.allTxtProductPrice.setText("$" + product.getPro_price());
        holder.allTxtProductScore.setText(product.getPro_score());
        Glide.with(context).load(product.getPro_image1()).into(holder.imageView);
        Glide.with(context).load(product.getPro_image2()).into(holder.testimg);
        holder.alltxtdesc.setText(product.getPro_desc());


        holder.allCardView.setOnClickListener(new View.OnClickListener() {
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
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView allTxtProductName;
        public TextView allTxtProductPrice;
        public TextView allTxtProductScore;
        public CardView allCardView;
        private TextView alltxtdesc;
        private ImageView testimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.allimage);
            allTxtProductName = itemView.findViewById(R.id.allpname);
            allTxtProductPrice = itemView.findViewById(R.id.allpprice);
            allTxtProductScore = itemView.findViewById(R.id.allscore);
            allCardView = itemView.findViewById(R.id.cardview);
            alltxtdesc = itemView.findViewById(R.id.desc);
            testimg = itemView.findViewById(R.id.imgtwo);
        }
    }
}
