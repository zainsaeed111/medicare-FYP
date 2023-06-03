package com.example.mediworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediworld.Adapters.ChatAdapter;
import com.example.mediworld.Models.ChatModel;
import com.example.mediworld.databinding.FragmentChatBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private FirebaseDatabase database;

    private String senderUid;
    private String receiverUid;
    private String UserId;
    private String ShopRegId;

    private String bPicture;
    private String adName;
    private  BottomSheetDialog bottomSheetDialog;

    private String chatRoomId;

    private ArrayList<ChatModel> list;
    private static final String PREF_NAME = "MyPreferences";
    private static final String KEY_VALUE = "myValue";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
        String value = retrieveValue(requireContext());
        // Retrieve the extracted text from the arguments
        String extractedText = getArguments().getString("extractedText");

        // Set the extracted text in the EditText field
        binding.etMessage.setText(extractedText);

        bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.upload_prescriptiob_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

// Set click listener for the button that opens the bottom sheet
        binding.ivuploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the bottom sheet
                UploadPrescriptionBottomSheet bottomSheet = new UploadPrescriptionBottomSheet();

                // Pass the receiver's ID to the bottom sheet
                bottomSheet.setReceiverId(receiverUid);

                // Show the bottom sheet
                bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
            }
        });






        database = FirebaseDatabase.getInstance();

        Bundle args1 = getArguments();
        Boolean isInboxGraph = args1.getBoolean("isInboxGraph");
        if (value == "Shop") {
            if (isInboxGraph) {
                Bundle args = getArguments();
                receiverUid = args.getString("shopID");
                chatRoomId = args.getString("chatRoom");
                senderUid = args.getString("shopID");
                Log.d("FromInbox", "FromInbox");
                Log.d("FromInbox", receiverUid);
                Log.d("FromInbox", chatRoomId);
            } else {
                Bundle args = getArguments();
                UserId = args.getString("UserId");
                ShopRegId = args.getString("ShopRegId");
                Log.d("chatRoomId", UserId + ShopRegId);
                senderUid = UserId;
                receiverUid = ShopRegId;
                chatRoomId = senderUid + receiverUid;
            }
        }
        else{
            if (isInboxGraph) {
                Bundle args = getArguments();
                receiverUid = args.getString("shopID");
                chatRoomId = args.getString("chatRoom");
                senderUid = args.getString("SenderId");
                Log.d("FromInbox", "FromInbox");
                Log.d("FromInbox", receiverUid);
                Log.d("FromInbox", chatRoomId);
            } else {
                Bundle args = getArguments();
                UserId = args.getString("UserId");
                ShopRegId = args.getString("ShopRegId");
                Log.d("chatRoomId", UserId + ShopRegId);
                senderUid = UserId;
                receiverUid = ShopRegId;
                chatRoomId = senderUid + receiverUid;
            }
        }



        bPicture = requireArguments().getString("bPicture");
        adName = requireArguments().getString("adName");

        list = new ArrayList<>();


        binding.sendMessage.setOnClickListener(view1 -> {
            if (binding.etMessage.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter your ChatMessage", Toast.LENGTH_SHORT).show();
            } else {
                ChatModel message = new ChatModel(binding.etMessage.getText().toString(), senderUid, new Date().getTime());
                String randomKey = database.getReference().push().getKey();

                database.getReference().child("chats")
                        .child(receiverUid)
                        .child(chatRoomId)
                        .child("messages")
                        .child(randomKey)
                        .setValue(message)
                        .addOnSuccessListener(aVoid -> {
                            binding.etMessage.setText(null);
                            Toast.makeText(getContext(), "ChatMessage sent", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        getMessages();
    }

/*
        private void getMessages() {
        database.getReference().child("chats").child(receiverUid).child(chatRoomId)
                .child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ChatModel model = dataSnapshot.getValue(ChatModel.class);
                            if (model != null) {
                                list.add(model);
                                Log.d("listdata", list.toString());

                            }
                        }
                        Log.d("listsize", String.valueOf(list.size()));
                        ChatAdapter chatAdapter = new ChatAdapter(list, getContext());
                        chatAdapter.notifyDataSetChanged();
                        binding.rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rvChat.setAdapter(chatAdapter);

                    }



                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("ChatFragment", error.getMessage());
                    }
                });
    }
*/
private void getMessages() {
    if (receiverUid != null && chatRoomId != null) {
        database.getReference().child("chats").child(receiverUid).child(chatRoomId)
                .child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ChatModel model = dataSnapshot.getValue(ChatModel.class);
                            if (model != null) {
                                list.add(model);
                                Log.d("listdata", list.toString());
                            }
                        }
                        Log.d("listsize", String.valueOf(list.size()));
                        ChatAdapter chatAdapter = new ChatAdapter(list, getContext());
                        chatAdapter.notifyDataSetChanged();
                        binding.rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rvChat.setAdapter(chatAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("ChatFragment", error.getMessage());
                    }
                });
    } else {
        Log.e("ChatFragment", "receiverUid or chatRoomId is null");
    }
}

    private static String retrieveValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_VALUE, null);
     /*   String extractedText = binding.textId.getText().toString();
        String chatRoomId = getArguments().getString("chatRoomId");
        openChatFragment(extractedText, chatRoomId);*/
    }
}
