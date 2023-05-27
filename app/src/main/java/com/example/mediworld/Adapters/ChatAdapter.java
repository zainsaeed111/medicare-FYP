package com.example.mediworld.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.ChatModel;
import com.example.mediworld.R;
import com.example.mediworld.databinding.RowMessageReceiverBinding;
import com.example.mediworld.databinding.RowMessageSenderBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String PREF_USER_KEY = "USER_KEY";
    private static final String USER_KEY = "userKey";
    private ArrayList<ChatModel> list;
    private final int ITEM_SENT = 1;
    private final int ITEM_RECEIVE = 2;
    private Context context;

    public ChatAdapter(ArrayList<ChatModel> list, Context context) {
        this.context = context;

        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_sender, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatModel message = list.get(position);

        if (holder.getItemViewType() == ITEM_SENT) {
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.binding.tvUserMsg.setText(message.getMessage());
        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.binding.tvUserMsg.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        String value = retrieveValue(context);
        String currentUserId =value;
        String senderId = list.get(position).getSenderId();

        if (senderId.equals(currentUserId)) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {
        private RowMessageSenderBinding binding;

        public SentViewHolder(View itemView) {
            super(itemView);
            binding = RowMessageSenderBinding.bind(itemView);
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private RowMessageReceiverBinding binding;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            binding = RowMessageReceiverBinding.bind(itemView);
        }
    }
    private static String retrieveValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_KEY, null);
    }
}