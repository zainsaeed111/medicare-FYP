package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UploadPrescriptionBottomSheet extends BottomSheetDialogFragment {

    private String chatRoomId;

    public void setReceiverId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for the bottom sheet
        View view = inflater.inflate(R.layout.upload_prescriptiob_bottom_sheet, container, false);

        // Find the button within the bottom sheet
        Button uploadPrescriptionBtn = view.findViewById(R.id.uploadPrescriptionbtn);

        // Set click listener for the button
        uploadPrescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                // Here, you can navigate to the next fragment

                // Assuming you have a method to navigate to the next fragment
                navigateToNextFragment();
            }
        });

        return view;
    }

    private void navigateToNextFragment() {
        // Create a bundle with the receiver's ID
        Bundle bundle = new Bundle();
        bundle.putString("chatRoomId", chatRoomId);

        //  to the next fragment
        UserOrderFromPrescription fragment = new UserOrderFromPrescription();
        fragment.setArguments(bundle);

        // Use the navigation controller to navigate to the next fragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.showBottomHome);
        navController.navigate(R.id.userOrderFromPrescription, bundle);

        // Dismiss the bottom sheet
        dismiss();
    }
}
