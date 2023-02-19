package com.example.vtc_ecommerce_androidapp.Adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vtc_ecommerce_androidapp.ModelClass.Cart;
import com.example.vtc_ecommerce_androidapp.ModelClass.Review;
import com.example.vtc_ecommerce_androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private Context context;

    List<Review> reviews = new ArrayList<>();

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_style,parent,false);
        ReviewAdapter.ViewHolder viewHolder = new ReviewAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Review review = reviews.get(position);

        String [] date = review.getCreat_time().split("\\s+");

        holder.username.setText(review.getUser_nick_name());
        holder.ratingBar.setRating(Float.parseFloat(review.getRating()));
        holder.userRateingTime.setText(date[0]);
        holder.userComments.setText(review.getComment());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        RatingBar ratingBar;
        TextView userRateingTime;
        TextView userComments;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.reviewUserName);
            ratingBar = itemView.findViewById(R.id.reviewratingbar);
            userRateingTime = itemView.findViewById(R.id.reviewUsertime);
            userComments = itemView.findViewById(R.id.userComments);
        }
    }
}
