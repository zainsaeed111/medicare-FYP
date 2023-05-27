package com.example.mediworld.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.InboxListModel;
import com.example.mediworld.R;

import java.util.ArrayList;


public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ItemViewHolder> {
    private ArrayList<InboxListModel> inboxChatItems;


    public InboxAdapter(ArrayList<InboxListModel> inboxChatItems) {
        this.inboxChatItems = inboxChatItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_inbox_items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return inboxChatItems.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        InboxListModel inboxList = inboxChatItems.get(position);

        holder.shopName.setText(inboxList.getShopName());
        holder.message.setText(inboxList.getMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("ShopId", inboxList.getShopId());
                bundle.putString("ShopName", inboxList.getShopName());
                bundle.putString("senderRoomId", inboxList.getChatRoomId());

                Navigation.findNavController(view).navigate(R.id.action_Inbox_to_chatFragment2, bundle);
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView shopName;
        TextView message;
        TextView messageTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.tvName);
            message = itemView.findViewById(R.id.tvmessage);
        }
    }

}


