package com.example.mediworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Adapters.InboxAdapter;
import com.example.mediworld.Models.InboxListModel;
import com.example.mediworld.databinding.FragmentInboxBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Inbox extends Fragment {

    private RecyclerView recyclerView;
    // private UserInboxAdapter userInboxAdapter;
    private ArrayList<InboxListModel> inboxList;
    private static final String PREF_Shop_KEY = "Shop_KEY";
    private static final String Shop_KEY = "shopKey";

    private DatabaseReference myShopReference;
    private FragmentInboxBinding binding;
    private InboxAdapter adapter;
    private FirebaseDatabase database;
    private String currentShopId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        birdImageLink = "";
//        birdId = "";
//        birdname = "";
        currentShopId = retrieveValue(getContext());
        Log.d("retrieveValue", currentShopId);

        database = FirebaseDatabase.getInstance();
        inboxList = new ArrayList<>();
        recyclerView = binding.InboxRecycler;
        adapter = new InboxAdapter(inboxList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        gptAnswer();
    }

    private void gptAnswer() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                List<Pair<String, String>> birdNameMapList = new ArrayList<>();
//                List<Pair<String, String>> birdIdMapList = new ArrayList<>();
//                List<Pair<String, String>> senderRooomMapList = new ArrayList<>();
//                List<Pair<String, String>> birdImgMapList = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String ShopId = ds.getKey();
                    Log.d("TAGShopId", ShopId); //ShopId==1

                    DatabaseReference childRef = dbRef.child(ShopId);
                    childRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
//                            inboxList.clear();
                            for (DataSnapshot child : snapshot.getChildren()) {
                                String childkey = child.getKey();
                                Log.d("child key ", childkey); //chatRoomId = -NVZ-WO8hEAjATiIgdwq123456789
                                if (childkey != null && childkey.contains(currentShopId)) {
                                    Log.d("if", "child key " + childkey + "!!"); //got the node of chatroom
                                    Log.d("datagot", child.toString());

                                    DataSnapshot messagesSnapshot = child.child("messages");
                                    Log.d("messagesSnapshot", messagesSnapshot.toString());
                                    for (DataSnapshot messageSnapshot : messagesSnapshot.getChildren()) {
                                        String senderId = messageSnapshot.child("senderId").getValue(String.class);
                                        Long timeStamp = messageSnapshot.child("timeStamp").getValue(Long.class);
                                        String message = messageSnapshot.child("message").getValue(String.class);

                                        InboxListModel inboxListModel = new InboxListModel(senderId, timeStamp, message);
                                        Log.d("inboxListModel", inboxListModel.toString());

                                        inboxList.add(inboxListModel);
                                        Log.d("inboxListModel", String.valueOf(inboxList.size()));
                                        adapter.notifyDataSetChanged();

                                    }

//                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("MyShop");
//                                    dbRef.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot snapshot) {
//                                            for (DataSnapshot ds : snapshot.getChildren()) {
//                                                String usersIds = ds.getKey();
//                                                DatabaseReference usersAds = dbRef.child(usersIds);
//                                                usersAds.addValueEventListener(new ValueEventListener() {
//                                                    @Override
//                                                    public void onDataChange(DataSnapshot usersAds) {
//                                                        for (DataSnapshot userAdsloop : usersAds.getChildren()) {
//                                                            String AdId_UsersNode =  usersAds.child("regNo").getValue(String.class);
//
//                                                            if (AdId_UsersNode.equals(ShopId)) {
////                                                                senderRooomMapList.add(new Pair<>(key, childkey));
////                                                                birdname = userAdsloop.child("birdname").getValue(String.class).toString();
////                                                                birdNameMapList.add(new Pair<>(key, birdname));
////                                                                birdId = userAdsloop.child("birdid").getValue(String.class).toString();
////                                                                birdIdMapList.add(new Pair<>(key, birdId));
////                                                                List<String> birdImageLinkList = userAdsloop.child("birdimagelink").getValue(new GenericTypeIndicator<List<String>>() {});
////                                                                birdImageLink = birdImageLinkList != null && !birdImageLinkList.isEmpty() ? birdImageLinkList.get(0) : "";
////                                                                birdImgMapList.add(new Pair<>(key, birdImageLink));
//
//                                                                Log.d("TAG", "Gotchildkeyinloop " + childkey.toString());
//                                                                Log.d("TAG", "Got Ad Node UID " + childkey);
//                                                                Log.d("TAG", "userAdsloop " + userAdsloop);
//                                                            }
//                                                        }
//                                                    }
//
//                                                    @Override
//                                                    public void onCancelled(DatabaseError error) {
//                                                        // Handle error
//                                                    }
//                                                });
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError error) {
//                                            // Handle error
//                                        }
//                                    });

                                    Log.d("keybeforechat", ShopId);
                                    Log.d("childkeybeforechat", childkey);

//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            database.getReference().child("chats").child(key).child(childkey).child("messages").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot snapshot) {
//                                                    Log.d("keyafterchat", key);
//                                                    Log.d("childkeyafterchat", childkey);
//                                                    Log.d("DataforInbox", snapshot.toString());
//
//                                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                                                        String matchingsenderRoomId = null;
//                                                        String matchingBirdName = null;
//                                                        String matchingBirdId = null;
//
//                                                        for (Pair<String, String> pair : senderRooomMapList) {
//                                                            if (pair.first.equals(key)) {
//                                                                matchingsenderRoomId = pair.second;
//                                                                break;
//                                                            }
//                                                        }
//
//                                                        for (Pair<String, String> pair : birdNameMapList) {
//                                                            if (pair.first.equals(key)) {
//                                                                matchingBirdName = pair.second;
//                                                                break;
//                                                            }
//                                                        }
//
//                                                        for (Pair<String, String> pair : birdIdMapList) {
//                                                            if (pair.first.equals(key)) {
//                                                                matchingBirdId = pair.second;
//                                                                break;
//                                                            }
//                                                        }
//
//                                                        Log.d("birdname", matchingBirdName);
//                                                        InboxListModel inboxModel = snapshot1.getValue(InboxModel.class);
//                                                        if (inboxModel != null) {
//                                                            inboxModel.setSenderRoomId(matchingsenderRoomId);
//                                                            inboxModel.setAd_Id(matchingBirdId);
//                                                            inboxModel.setAd_name(matchingBirdName);
//                                                            inboxModel.setAd_Img(birdImageLink);
//                                                            inboxList.add(inboxModel);
//                                                            Log.d("listdata", inboxModel.toString());
//                                                        }
//                                                    }
//
//                                                    Log.d("inboxList size", String.valueOf(inboxList.size()));
//                                                    adapter.notifyDataSetChanged();
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError error) {
//                                                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                        }
//                                    }, 1000);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Handle error
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private static String retrieveValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_Shop_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Shop_KEY, null);
    }
}
