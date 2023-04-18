package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.category;
import com.example.vtc_ecommerce_androidapp.PageView.ListAllProductActivity;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder>{

    private Context context;

    List<category>  categories= new ArrayList<>();

    public categoryAdapter(Context context, List<category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorys_icon_list_style,parent,false);
        categoryAdapter.ViewHolder viewHolder = new categoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        category category = categories.get(position);
        Glide.with(context).load(category.getCategorycol_image()).into(holder.imgicon);
        holder.categoryname.setText(category.getCategory_name());

        holder.imgicon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {


                Intent intent  = new Intent(context,ListAllProductActivity.class);
                intent.putExtra("categoryID",category.getCategoryID());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgicon;
        TextView categoryname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgicon = itemView.findViewById(R.id.categoryicon);
            categoryname = itemView.findViewById(R.id.categoryname);
        }
    }
}
