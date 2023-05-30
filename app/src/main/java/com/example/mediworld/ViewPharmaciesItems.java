//Last Working  ViewPharmaies:
package com.example.mediworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class ViewPharmaciesItems extends Fragment {
    private static final String TAG = "ViewPharmaciesItems";

    private String regNo;
    private List<PharmaciesMainItemModel> popularItemsList = new ArrayList<>();
    private List<PharmaciesMainItemModel> latestItemsList = new ArrayList<>();
    private RecyclerView popularItemsRecyclerView;
    private RecyclerView latestItemsRecyclerView;
    private PharmaciesMainItemAdapter popularItemsAdapter;
    private PharmaciesMainItemAdapter latestItemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pharmcies_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularItemsRecyclerView = view.findViewById(R.id.popularItemsrecyler);
        latestItemsRecyclerView = view.findViewById(R.id.latestItemsrecyler);
        TextView tvseeallItems = view.findViewById(R.id.tvseeallItems);
        tvseeallItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the NavController from the host fragment/activity
                NavController navController = Navigation.findNavController(view);

                // Navigate to the desired destination fragment
                navController.navigate(R.id.action_viewPharmciesItems_to_seeAllProducts);

            }
        });


        popularItemsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        popularItemsRecyclerView.setLayoutManager(popularLayoutManager);

        latestItemsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager latestLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        latestItemsRecyclerView.setLayoutManager(latestLayoutManager);


        Bundle args = getArguments();
        regNo = args.getString("regNo");
        Log.d("viewpharmacies", regNo);

        DatabaseReference shopRef = FirebaseDatabase.getInstance().getReference().child("MyShop");
        shopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                popularItemsList.clear();
                latestItemsList.clear();

                for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                    String shopKey = shopSnapshot.getKey();
                    String shopRegNo = shopSnapshot.child("regNo").getValue(String.class);
                    Log.d("shopKey: ", shopKey);

                    if (shopRegNo != null && shopRegNo.equals(regNo)) {
                        DataSnapshot productsSnapshot = shopSnapshot.child("products");
                        Log.d("Products are: ", productsSnapshot.toString());

                        for (DataSnapshot productSnapshot : productsSnapshot.getChildren()) {
                            PharmaciesMainItemModel item = productSnapshot.getValue(PharmaciesMainItemModel.class);
                            if (item != null) {
                                String subcategory = item.getSubcategory();
                                Log.d("Category: ", subcategory);
                                if (subcategory != null) {
                                    if (subcategory.equals("Popular")) {
                                        popularItemsList.add(item);
                                    } else if (subcategory.equals("Latest")) {
                                        latestItemsList.add(item);
                                    }
                                }
                            }
                        }
                        break; // Found matching shop, exit the loop
                    }
                }

                // Set up the adapters for the RecyclerViews
                popularItemsAdapter = new PharmaciesMainItemAdapter(getContext(), popularItemsList);
                popularItemsRecyclerView.setAdapter(popularItemsAdapter);
            /*    popularItemsRecyclerView.setAdapter(popularItemsAdapter);
                latestItemsRecyclerView.setAdapter(latestItemsAdapter);*/

                latestItemsAdapter = new PharmaciesMainItemAdapter(getContext(), latestItemsList);
                latestItemsRecyclerView.setAdapter(latestItemsAdapter);

                // Notify the adapters about the data change
                popularItemsAdapter.notifyDataSetChanged();
                latestItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });



    }

}
