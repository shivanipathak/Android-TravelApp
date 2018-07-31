package com.shivani.letsgo.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.ReviewPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shivani Pathak on 7/11/2017.
 */

public class listviewcustomized_adapter extends BaseAdapter {

    Context context;
    listviewcustomized_adapter adapter;
    List <ReviewPojo> listviewpojoList;
    LayoutInflater li;
    TextView  content,authorname;
    ImageView imagev1;

    public listviewcustomized_adapter(Context context, List <ReviewPojo> listviewpojoList)
    {
        this.context=context;
        this.listviewpojoList=listviewpojoList;
        li=LayoutInflater.from(context);

    }

    @Override
    public int getCount()
    {
        return listviewpojoList.size();
    }

    @Override
    public ReviewPojo getItem(int position)
    {
        return listviewpojoList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view=null;
        if (view !=null)
        {
            view=convertView;
        }
        else
        {
            view=li.inflate(R.layout.review_customized_listview,null);
        }

        imagev1=(ImageView)view.findViewById(R.id.icon);
        content=(TextView)view.findViewById(R.id.textreviews);
        authorname=(TextView)view.findViewById(R.id.authoranme);

        ReviewPojo listviewpojo=listviewpojoList.get(position);
        authorname.setText(listviewpojo.getAuthor_name());
        content.setText(listviewpojo.getText()+"\n"+listviewpojo.getRating()+"\n"+listviewpojo.getTime()+"  "+listviewpojo.getRelative_time_description());
       //imagev1.setImageResource(listviewpojo.getProfile_photo_url());
        Picasso.with(context).load(listviewpojo.getProfile_photo_url()).into(imagev1);
        return view;
    }
}
