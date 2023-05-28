package com.example.mediworld.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.ChatMessage;
import com.example.mediworld.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int VIEW_TYPE_USER = 0;
    private static final int VIEW_TYPE_BOT = 1;
    private List<ChatMessage> mMessages;

    public MessageAdapter(List<ChatMessage> messages) {
        mMessages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == VIEW_TYPE_USER) {
            view = inflater.inflate(R.layout.row_message_sender, parent, false);
        } else {
            view = inflater.inflate(R.layout.row_message_receiver, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = mMessages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = mMessages.get(position);
        return message.isSentByUser() ? VIEW_TYPE_USER : VIEW_TYPE_BOT;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tvUserMsg);
        }

        public void bind(ChatMessage message) {
            mTextView.setText(message.getText());
        }
    }
}