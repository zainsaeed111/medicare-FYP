package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediworld.Models.MyMessage;
import com.example.mediworld.databinding.FragmentMyChatBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyChatFragment extends Fragment {

    private FragmentMyChatBinding binding;
    private String selectedShopKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve the selected shop key from the arguments
            selectedShopKey = getArguments().getString("selectedKey");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivsendmessageMychat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the message text from the input field
                String messageText = binding.etMessagemychat.getText().toString().trim();

                // Send the message to the selected shop
                sendMessageToShop(selectedShopKey, messageText);
            }
        });

        // TODO: Implement the remaining logic for the chat fragment
    }

    // Method to send a message to the selected shop
    private void sendMessageToShop(String shopKey, String messageText) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
        String userKey = "MyUser"; // Replace "MyUser" with the actual user key or retrieve it from your data model

        // Generate a new key for the message
        String messageKey = messagesRef.child("MyShop").child(shopKey).push().getKey();

        // Create the message object
        MyMessage message = new MyMessage();
        message.setText(messageText);
     //   message.setTime(getCurrentTime());
        message.setSender(userKey);
        message.setReceiver(shopKey);

        // Save the message under the respective keys
        messagesRef.child("MyShop").child(shopKey).child(messageKey).setValue(message);
    }
}
