package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class home extends Fragment {

   LinearLayout orderMedicinebox,onlinePharmaciesBox;
   LinearLayout aimedicineBox;
   LinearLayout emgNumBox;
   LinearLayout nearbyHospitalBox;
   LinearLayout nearbyPharmciesBox;
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
        emgNumBox=getView().findViewById(R.id.emgNumBox);
        aimedicineBox=getView().findViewById(R.id.aimedicineBox);
        nearbyHospitalBox=getView().findViewById(R.id.nearbyHospitalsBox);
        onlinePharmaciesBox=getView().findViewById(R.id.onlinePharmaciesBox);
        nearbyPharmciesBox=getView().findViewById(R.id.nearbyPharmaciesBox);
       Button btnViewmyorders=getView().findViewById(R.id.btnViewmyorders);

        sliderView=getView().findViewById(R.id.image_slider);
        arrayList.add(R.drawable.sliderimgiii);
        arrayList.add(R.drawable.sliderimgn);
        arrayList.add(R.drawable.sliderimgiv);
        arrayList.add(R.drawable.sliderimgv);
        ImageSlider imageSlider=new ImageSlider(getActivity(),arrayList);
        sliderView.setSliderAdapter(imageSlider);
        sliderView.startAutoCycle();

        onlinePharmaciesBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.action_home2_to_onlinePharmacies);
            }
        });


        btnViewmyorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.action_userHome_to_myOrders);
            }
        });

        nearbyPharmciesBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.action_userHome_to_nearbyPharmacies);
            }
        });
        orderMedicinebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(),OrderNow_Shop.class);
                startActivity(intent);*/
                NavController navController = Navigation.findNavController(v);

                  navController.navigate(R.id.action_userHome_to_userOrderMedicine);



            }
        });


        aimedicineBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.action_userHome_to_userAIMedicalAssitant);
            }
        });

        emgNumBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain an instance of the NavController
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.userEmgno);
            }
        });
        nearbyHospitalBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain an instance of the NavController
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.action_userHome_to_userNearbyHospitals2);
            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;


    }}