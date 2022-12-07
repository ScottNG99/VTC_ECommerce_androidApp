package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.product;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.allimage);
            allTxtProductName = itemView.findViewById(R.id.allpname);
            allTxtProductPrice = itemView.findViewById(R.id.allpprice);
            allTxtProductScore = itemView.findViewById(R.id.allscore);
        }
    }
}
