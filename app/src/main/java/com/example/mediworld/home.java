package com.example.mediworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {

   LinearLayout orderMedicinebox;
    View view;
    SliderView sliderView;
    ArrayList<Integer> arrayList= new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       orderMedicinebox=getView().findViewById(R.id.orderMedicinebox);
        sliderView=getView().findViewById(R.id.image_slider);
        arrayList.add(R.drawable.sliderimgiii);
        arrayList.add(R.drawable.sliderimgn);
        arrayList.add(R.drawable.sliderimgiv);
        arrayList.add(R.drawable.sliderimgv);
        ImageSlider imageSlider=new ImageSlider(getActivity(),arrayList);
        sliderView.setSliderAdapter(imageSlider);
        sliderView.startAutoCycle();

        orderMedicinebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OrderNow_Shop.class);
                startActivity(intent);

            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;


    }}