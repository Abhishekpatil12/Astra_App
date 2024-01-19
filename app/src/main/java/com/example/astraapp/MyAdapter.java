package com.example.astraapp;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataClass> dataList;
    LayoutInflater layoutInflater;

    public MyAdapter(Context context, ArrayList<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //System.out.println("data : "+dataList.get(0).getImageurl());

        if(layoutInflater==null)
        {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view==null)
        {
            view = layoutInflater.inflate(R.layout.grid_item,null);
        }

        TextView gridcaption = view.findViewById(R.id.gridCaption);
        ImageView gridImage = view.findViewById(R.id.gridImage);


        Glide.with(context).load(dataList.get(i).getImageurl()).into(gridImage);
        gridcaption.setText(dataList.get(i).getName());

        return view;
    }
}
