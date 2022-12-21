package com.example.vtc_ecommerce_androidapp.Adater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vtc_ecommerce_androidapp.Manager.CollectManager;
import com.example.vtc_ecommerce_androidapp.Manager.SharedPrefManager;
import com.example.vtc_ecommerce_androidapp.ModelClass.AllProducts;
import com.example.vtc_ecommerce_androidapp.ModelClass.Cart;
import com.example.vtc_ecommerce_androidapp.PageView.ProductDetailActivity;
import com.example.vtc_ecommerce_androidapp.R;
import com.example.vtc_ecommerce_androidapp.Service.RequestHandler;
import com.example.vtc_ecommerce_androidapp.api.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;

    List<Cart> carts = new ArrayList<>();


    ArrayList<Integer> qtyArray = new ArrayList<Integer>();


    int totoalPrice ,sum;






    public CartAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_style,parent,false);
        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Cart cart = carts.get(position);

        qtyArray.add(Integer.valueOf(cart.getGoods_count()));

        totoalPrice = totoalPrice + Integer.parseInt(cart.getPro_price()) * Integer.parseInt(cart.getGoods_count());






        holder.cartTxtProductName.setText(cart.getPro_name());
        holder.cartTxtProductPrice.setText("$"+cart.getPro_price());
        holder.cartTxtProductQty.setText(cart.getGoods_count());
        Glide.with(context).load(cart.getPro_image1()).into(holder.cartimageView);


        holder.cartdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userid = SharedPrefManager.getInstance(context.getApplicationContext()).getStudent().getUserID();

                String txtUserID = String.valueOf(userid);
                String cartID = cart.getCartID();

                String targetURL = Config.DELETE_CART + "userID="+txtUserID + "&cartID=" +cartID;
                new CollectManager().execute(targetURL);
                Toast.makeText(context,"successfully removed",Toast.LENGTH_LONG).show();
            }
        });


        holder.addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String cartID = cart.getCartID();

                if (qtyArray.get(position) == 10){

                    Toast.makeText(context.getApplicationContext(), "Cant Order More than 10", Toast.LENGTH_SHORT).show();
                }else {
                    qtyArray.set(position,qtyArray.get(position)+1);
                    holder.cartTxtProductQty.setText(String.valueOf(qtyArray.get(position)));
                    updateQty(cartID,qtyArray.get(position));


                    totoalPrice = totoalPrice + Integer.parseInt(cart.getPro_price());
                    System.out.println(totoalPrice);

//                    showTotalPrice(totoalPrice);
//                    showTotalPrice();


                }


            }
        });


        holder.minusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cartID = cart.getCartID();

                if (qtyArray.get(position) == 1) {

                    Toast.makeText(context.getApplicationContext(), "A minimum of one", Toast.LENGTH_SHORT).show();
                }else {

                    qtyArray.set(position,qtyArray.get(position)-1);
                    holder.cartTxtProductQty.setText(String.valueOf(qtyArray.get(position)));
                    updateQty(cartID,qtyArray.get(position));

                    totoalPrice = totoalPrice - Integer.parseInt(cart.getPro_price());
//                    showTotalPrice(totoalPrice);
//                    showTotalPrice();
                }

            }
        });





    }

    private void updateQty(String cartID, int qtyCount) {

        final String cartid = cartID;
        final int count = qtyCount;

        class Modify extends AsyncTask<Void, Void, String>{

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("cartID", cartid);
                params.put("goods_count", String.valueOf(count));

                //returing the response
                return requestHandler.sendPostRequest(Config.UPDATE_CART_QTY, params);
            }
        }

        Modify ul = new Modify();
        ul.execute();

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView cartimageView;
        public ImageView cartdelete;
        public TextView cartTxtProductName;
        public TextView cartTxtProductPrice;
        public TextView cartTxtProductQty;


        ImageView addQty,minusQty;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartdelete = itemView.findViewById(R.id.deletCart);
            cartimageView = itemView.findViewById(R.id.cartimage);
            cartTxtProductName = itemView.findViewById(R.id.Cartpname);
            cartTxtProductPrice = itemView.findViewById(R.id.Cartpprice);
            cartTxtProductQty = itemView.findViewById(R.id.goodsCount);

            addQty = itemView.findViewById(R.id.addQ);
            minusQty = itemView.findViewById(R.id.minusQ);



        }
    }






}
