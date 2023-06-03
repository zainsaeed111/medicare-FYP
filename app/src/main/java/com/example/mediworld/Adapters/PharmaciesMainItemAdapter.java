package com.example.mediworld.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.PharmaciesMainItemModel;
import com.example.mediworld.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PharmaciesMainItemAdapter extends RecyclerView.Adapter<PharmaciesMainItemAdapter.ViewHolder> {

    private List<PharmaciesMainItemModel> itemList;
    private Context context;
    private boolean isViewPharmciesItems;

    public PharmaciesMainItemAdapter(Context context, List<PharmaciesMainItemModel> itemList, boolean isViewPharmciesItems) {
        this.itemList = itemList;
        this.context = context;
        this.isViewPharmciesItems = isViewPharmciesItems;
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
        holder.productImg.setImageBitmap(null);
        Picasso.get()
                .load(item.getImageUrl())
                .into(holder.productImg);
        holder.discountedTv.setText(item.getDiscount());
        holder.productQuantity.setText(item.getDescription());
        holder.catogeryTv.setText(item.getCategory());
        holder.discountedPriceTextView.setText(String.valueOf(item.getDiscountedPrice()));
        holder.realPriceTextView.setText(String.valueOf(item.getPrice()));

        // Handle item click event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a bundle and add necessary data
                Bundle bundle = new Bundle();
                bundle.putString("productId", item.getProductId());

                // Get the NavController from the itemView
                NavController navController = Navigation.findNavController(view);

                // Check if the current fragment is "SeeAllProducts"
                if (isViewPharmciesItems) {
                    // Navigate to the "seel.toproduct" action and pass the bundle
                    navController.navigate(R.id.action_viewPharmciesItems_to_productDetail, bundle);
                } else {
                    // Navigate to the default product detail screen and pass the bundle
                    navController.navigate(R.id.productDetail, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView discountedTv, productQuantity, discountedPriceTextView, realPriceTextView, catogeryTv;
        private Button addToCartBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.productImg);
            discountedTv = itemView.findViewById(R.id.discountedTv);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            discountedPriceTextView = itemView.findViewById(R.id.discountedPriceTextView);
            realPriceTextView = itemView.findViewById(R.id.realPriceTextView);
            catogeryTv = itemView.findViewById(R.id.catogeryTv);
            addToCartBtn = itemView.findViewById(R.id.addtocartBtn);
        }
    }
}