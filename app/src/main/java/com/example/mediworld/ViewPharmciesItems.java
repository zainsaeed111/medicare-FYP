//Last Working  ViewPharmaies:
package com.example.mediworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Adapters.PharmaciesMainItemAdapter;
import com.example.mediworld.Models.PharmaciesMainItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPharmciesItems extends Fragment {
    private static final String TAG = "ViewPharmciesItems";

    private String regNo;
    private List<PharmaciesMainItemModel> productList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PharmaciesMainItemAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pharmcies_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.pharmaciesmainitmesRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        productList = new ArrayList<>();

        Bundle args = getArguments();
        regNo = args.getString("regNo");
        Log.d("viewpharmacies", regNo);

        DatabaseReference shopRef = FirebaseDatabase.getInstance().getReference().child("MyShop");
        shopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                productList.clear();
                for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                    String shopKey = shopSnapshot.getKey();
                    String shopRegNo = shopSnapshot.child("regNo").getValue(String.class);
                    Log.d("shopKey: ", shopKey.toString());

                    if (shopRegNo.equals(regNo)) {
                        DataSnapshot productsSnapshot = shopSnapshot.child("products");
                        Log.d(shopRegNo, "Products are: ");
                        for (DataSnapshot productSnapshot : productsSnapshot.getChildren()) {
                            Log.d("Productsis: ", productSnapshot.toString());

                            PharmaciesMainItemModel item = productSnapshot.getValue(PharmaciesMainItemModel.class);
                            productList.add(item);
                            Log.d("listsize: ", String.valueOf(productList.size()));
                        }
                        break; // Found matching shop, exit the loop
                    }
                }

                mAdapter = new PharmaciesMainItemAdapter(getContext(), productList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }
}