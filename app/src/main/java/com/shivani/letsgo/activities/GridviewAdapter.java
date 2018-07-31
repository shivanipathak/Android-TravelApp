package com.shivani.letsgo.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.NewsPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shivani Pathak on 7/24/2017.
 */

public class GridviewAdapter extends BaseAdapter {

    LayoutInflater li;
    Context context;
    List<NewsPojo> gridviewnews;
    TextView newstext,newstitle,url;
    ImageView newsimage;

    public GridviewAdapter(Context context, List<NewsPojo> gridviewnews) {
        this.context = context;
        this.gridviewnews = gridviewnews;
        li = LayoutInflater.from(context);
    }

    public void setData(List<NewsPojo> gridviewnews) {
        this.gridviewnews = gridviewnews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gridviewnews.size();
    }

    @Override
    public Object getItem(int position) {
        return gridviewnews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view != null) {
            view = convertView;
        } else {
            view = li.inflate(R.layout.gridview_news, null);
        }

        newsimage = (ImageView) view.findViewById(R.id.newsimage);
        newstext = (TextView) view.findViewById(R.id.textnews);
        url=(TextView)view.findViewById(R.id.url);
        newstitle=(TextView)view.findViewById(R.id.newstitle);

        NewsPojo newspojo = gridviewnews.get(position);
        newstitle.setText(newspojo.getTitle());
        newstext.setText( newspojo.getAuthor() + "\n" + newspojo.getDescription()  );
        url.setText(newspojo.getUrl());
        Picasso.with(context).load(newspojo.getUrlToImage()).into(newsimage);
        return view;
    }
}
