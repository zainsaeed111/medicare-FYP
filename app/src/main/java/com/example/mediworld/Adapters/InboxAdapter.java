package com.example.mediworld.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.InboxListModel;
import com.example.mediworld.R;
import com.example.mediworld.databinding.ChatListInboxItemsBinding;

import java.util.List;


public class InboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<InboxListModel> inboxList;

    public InboxAdapter(List<InboxListModel> inboxList) {
        this.inboxList = inboxList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_list_inbox_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InboxListModel inboxItem = inboxList.get(position);
        InboxAdapter.ViewHolder viewHolder = (InboxAdapter.ViewHolder) holder;

        viewHolder.binding.tvname.setText(inboxItem.getSenderId());
        viewHolder.binding.tvtime.setText(String.valueOf(inboxItem.getTimeStamp()));
        viewHolder.binding.tvmessage.setText(inboxItem.getMessage());
    }



    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ChatListInboxItemsBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ChatListInboxItemsBinding.bind(itemView);
        }
    }
}
