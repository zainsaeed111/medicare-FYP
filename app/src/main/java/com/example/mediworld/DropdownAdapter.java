package com.example.mediworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class DropdownAdapter extends RecyclerView.Adapter<DropdownAdapter.ViewHolder> {
    private List<String> items;
    private OnItemClickListener onItemClick;

    public DropdownAdapter(List<String> items, OnItemClickListener onItemClick) {
        this.items = items;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.dropdown_item,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dropdownItem;

        public ViewHolder(View itemView) {
            super(itemView);
            dropdownItem = itemView.findViewById(R.id.dropdownItem);
        }

        public void bind(String item) {
            dropdownItem.setText(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }
}
