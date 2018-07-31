package com.shivani.letsgo.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.shivani.letsgo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shivani Pathak on 7/28/2017.
 */

public class Description_Adapter extends BaseAdapter {


    Context context;
    List<String> list;

    ImageView placephotos;
    LayoutInflater li;

    public Description_Adapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        li = LayoutInflater.from(context);

    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = li.inflate(R.layout.description_customized, null);

        }
        placephotos = (ImageView) view.findViewById(R.id.imagesv1);
        String photo = list.get(position);
        //Toast.makeText(context,"oooooo"+photo,Toast.LENGTH_LONG).show();
        Picasso.with(context).load(photo).into(placephotos);

        return view;
    }
}
