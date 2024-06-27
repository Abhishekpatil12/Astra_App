package com.example.astraapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.astraapp.models.DataClass;
import com.example.astraapp.R;

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

        //done changes
        ImageView gridImage = view.findViewById(R.id.gridImage);
        TextView txtname = view.findViewById(R.id.textname);
        Glide.with(context).load(dataList.get(i).getImageurl()).into(gridImage);
        txtname.setText(dataList.get(i).getName());

        return view;
    }
}
