package com.example.mediworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class ImageSlider extends SliderViewAdapter<ImageSlider.Myholder>{
    Context context;
    ArrayList<Integer> arrayList;

    public ImageSlider(Context context, ArrayList<Integer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater=LayoutInflater.from(context);
    }

    LayoutInflater layoutInflater;
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_file,parent,false);
        return  new Myholder(view);


    }

    @Override
    public void onBindViewHolder(Myholder viewHolder, int position) {
            viewHolder.imageView.setImageResource(arrayList.get(position));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public class Myholder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public Myholder(View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.imagView);
        }
    }
}
