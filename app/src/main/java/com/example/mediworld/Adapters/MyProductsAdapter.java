package com.example.mediworld.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.R;
import com.example.mediworld.Shop.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.ViewHolder> {
    private List<Product> productList;
    // Constructor
    public MyProductsAdapter() {
        productList = new ArrayList<>();
    }

    // ViewHolder class to hold the views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView myproductImg;
        private TextView myproductDiscount, myproductDescription, myproductName, myproductPrice;
        private Button addToCartBtn;
        private ImageView deleteivBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myproductImg = itemView.findViewById(R.id.myproductImg);
            myproductDiscount = itemView.findViewById(R.id.myproductDiscount);
            myproductDescription = itemView.findViewById(R.id.myproductDescription);
            myproductName = itemView.findViewById(R.id.myproductName);
            myproductPrice = itemView.findViewById(R.id.myproductPrice);
            deleteivBtn = itemView.findViewById(R.id.deleteivBtn);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_produtcs_signle_row, parent, false);
        return new ViewHolder(itemView);
    }
    // Interface for delete callback


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.myproductImg.setImageBitmap(null);
        Picasso.get()
                .load(product.getImageUrl())
                .into(holder.myproductImg);
        // Set the values to the views
        holder.myproductName.setText(product.getName());
        holder.myproductPrice.setText(String.valueOf(product.getPrice()));
        holder.myproductDescription.setText(String.valueOf(product.getDescription()));
        holder.myproductDiscount.setText(String.valueOf(product.getDiscount()));
//        holder.myproductDiscount.setText(String.valueOf(product.getDiscount()));


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProducts(List<Product> products) {
        productList = products;
        notifyDataSetChanged();
    }
}