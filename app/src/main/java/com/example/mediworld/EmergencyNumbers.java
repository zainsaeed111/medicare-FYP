package com.example.mediworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import com.example.mediworld.databinding.FragmentEmergencyNumbersBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**

 A simple {@link Fragment} subclass.

 Use the {@link EmergencyNumbers#newInstance} factory method to

 create an instance of this fragment.
 */
public class EmergencyNumbers extends Fragment {

    // Declare the view binding variable
    private FragmentEmergencyNumbersBinding binding;
    private TextView numberTextView;

    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmergencyNumbers() {
// Required empty public constructor
    }

    /**

     Use this factory method to create a new instance of
     this fragment using the provided parameters.
     @param param1 Parameter 1.
     @param param2 Parameter 2.
     @return A new instance of fragment EmergencyNumbers.
     */
// TODO: Rename and change types and number of parameters
    public static EmergencyNumbers newInstance(String param1, String param2) {
        EmergencyNumbers fragment = new EmergencyNumbers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment using ViewBinding
        binding = FragmentEmergencyNumbersBinding.inflate(inflater, container, false);
// Set click listeners for each of the image views
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "";
                switch (view.getId()) {
                    case R.id.AmbImgbtn:
                        phoneNumber = "1133";
                        break;

                    case R.id.policeImgBtn:
                        phoneNumber = "15";
                        break;
                    case R.id.FireImgBtn:
                        phoneNumber = "16";
                        break;
                    case R.id.EdhiImgBtn:
                        phoneNumber = "115";
                        break;
                }
                binding.tvLabeli.setText(phoneNumber);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        };
        binding.AmbImgbtn.setOnClickListener(clickListener);
        binding.policeImgBtn.setOnClickListener(clickListener);
        binding.FireImgBtn.setOnClickListener(clickListener);
        binding.EdhiImgBtn.setOnClickListener(clickListener);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
// Nullify the view binding instance to avoid memory leaks
        binding = null;
    }
}

