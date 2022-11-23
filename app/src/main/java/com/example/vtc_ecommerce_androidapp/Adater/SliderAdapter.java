package com.example.vtc_ecommerce_androidapp.Adater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vtc_ecommerce_androidapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    int[] images;

    public SliderAdapter(int[] images){

        this.images = images;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        /* 獲取模板的layout*/

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        /* 獲取擺放位置*/

        viewHolder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            /* 圖片擺放位置ID*/
            imageView = itemView.findViewById(R.id.image_view);

        }
    }

}
