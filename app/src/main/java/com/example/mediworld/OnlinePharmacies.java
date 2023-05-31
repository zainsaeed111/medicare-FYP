package com.example.mediworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Adapters.OnlinePharmaciesAdapter;
import com.example.mediworld.Models.OnlinePharmaciesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlinePharmacies extends Fragment {

    private RecyclerView recyclerView;
    private OnlinePharmaciesAdapter adapter;
    private ArrayList<OnlinePharmaciesModel> pharmaciesList;

    public OnlinePharmacies() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_pharmacies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.onlinepharmaciesRecyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pharmaciesList = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference().child("MyShop");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pharmaciesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("snapshot", snapshot.toString());

                    OnlinePharmaciesModel pharmacy = snapshot.getValue(OnlinePharmaciesModel.class);
                    pharmaciesList.add(pharmacy);
                    Log.d("pharmaciesList", pharmaciesList.toString());

                }
                adapter = new OnlinePharmaciesAdapter(getContext(), pharmaciesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}