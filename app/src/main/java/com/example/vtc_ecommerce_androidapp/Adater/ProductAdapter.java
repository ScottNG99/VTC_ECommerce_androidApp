package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.vtc_ecommerce_androidapp.ModelClass.product;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.Request.CustomVolleyRequest;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    List<product> products;


    public ProductAdapter(List<product> products, Context context){
        super();
        //Getting all superheroes
        this.products = products;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_product_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        product product = products.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(product.getPro_image1(), ImageLoader.getImageListener(holder.imageView, R.drawable.loading, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(product.getPro_image1(),imageLoader);
        holder.txtProductName.setText(product.getPro_name());
        holder.txtProductPrice.setText("$" + product.getPro_price());
        holder.txtProductScore.setText(product.getPro_score());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView imageView;
        public TextView txtProductName;
        public TextView txtProductPrice;
        public TextView txtProductScore;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgproduct);
            txtProductName = itemView.findViewById(R.id.pname);
            txtProductPrice = itemView.findViewById(R.id.pprice);
            txtProductScore = itemView.findViewById(R.id.txtscore);



        }
    }
}
