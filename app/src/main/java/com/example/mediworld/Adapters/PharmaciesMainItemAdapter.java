package com.example.mediworld.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.PharmaciesMainItemModel;
import com.example.mediworld.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PharmaciesMainItemAdapter extends RecyclerView.Adapter<PharmaciesMainItemAdapter.ViewHolder> {

    private List<PharmaciesMainItemModel> itemList;
    private Context context;

    public PharmaciesMainItemAdapter(Context context, List<PharmaciesMainItemModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PharmaciesMainItemModel item = itemList.get(position);

        // Load image using Picasso or Glide library
        Picasso.get().load(item.getImageUrl()).into(holder.productImg);
        holder.discountedTv.setText(item.getDiscount());
        holder.productQuantity.setText(item.getDescription());
        holder.discountedPriceTextView.setText(String.valueOf(item.getDiscountedPrice()));
        holder.realPriceTextView.setText(String.valueOf(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView discountedTv, productQuantity, discountedPriceTextView, realPriceTextView;
        private Button addToCartBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.productImg);
            discountedTv = itemView.findViewById(R.id.discountedTv);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            discountedPriceTextView = itemView.findViewById(R.id.discountedPriceTextView);
            realPriceTextView = itemView.findViewById(R.id.realPriceTextView);
            addToCartBtn = itemView.findViewById(R.id.addtocartBtn);
        }
    }
}