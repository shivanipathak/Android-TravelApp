package com.shivani.letsgo.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.shivani.letsgo.R;
import com.shivani.letsgo.activities.DashboardActivity;
import com.shivani.letsgo.activities.DatabaseHandler;
import com.shivani.letsgo.pojo.database_mostvisitedPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivani Pathak on 7/11/2017.
 */

public class customized_listviewadapter extends BaseAdapter {

    Context context;

    customized_listviewadapter adapter;
    DatabaseHandler dbms;
    List <database_mostvisitedPojo> mostvisitedplacepojoList;
    LayoutInflater li;
    ImageView imagev1,addlisticon,favicon,mapicon;
    AppCompatActivity appCompatActivity;
    ArrayList<database_mostvisitedPojo> list;
    ArrayList<String> placeids;
    public customized_listviewadapter(Context context, List <database_mostvisitedPojo> mostvisitedplacepojoList,AppCompatActivity appCompatActivity)
    {

        this.context=context;
        this.mostvisitedplacepojoList = mostvisitedplacepojoList;
        li=LayoutInflater.from(context);
        dbms= new DatabaseHandler(context);
        this.appCompatActivity=appCompatActivity;

    }

    @Override
    public int getCount()
    {
        return mostvisitedplacepojoList.size();
    }

    @Override
    public database_mostvisitedPojo getItem(int position)
    {
        return mostvisitedplacepojoList.get(position);
    }
public void setData(List <database_mostvisitedPojo> mostvisitedplacepojoList){
    this.mostvisitedplacepojoList = mostvisitedplacepojoList;
    notifyDataSetChanged();
}
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent)
    {
        View view=null;
        if (view !=null)
        {
            view=convertView;
        }
        else
        {
            view=li.inflate(R.layout.custom_listview_dashboard,null);
        }

        imagev1=(ImageView)view.findViewById(R.id.imagev1);
        addlisticon=(ImageView)view.findViewById(R.id.addlisticon);
        mapicon=(ImageView)view.findViewById(R.id.mapicon);

        final database_mostvisitedPojo mostvisitedplacepojo = mostvisitedplacepojoList.get(position);
        Log.d( "getView: ",mostvisitedplacepojo.getPhotorefernce());
        Picasso.with(context).load(mostvisitedplacepojo.getPhotorefernce()).into(imagev1);

        addlisticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=dbms.getDetails();
                placeids=new ArrayList<>();
                for (int i=0;i<list.size();i++)
                {
                    placeids.add(list.get(i).getPlaceid());
                }
                if (placeids.contains(mostvisitedplacepojo.getPlaceid()))
                {
                    Toast.makeText(context,"Already Added",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    dbms.addplace(mostvisitedplacepojo.getTitle(),mostvisitedplacepojo.getPlaceid(),mostvisitedplacepojo.getPhotorefernce()
                    );
                    if(appCompatActivity instanceof DashboardActivity){
                        ((DashboardActivity)appCompatActivity).update();
                    }
                    Toast.makeText(context,""+mostvisitedplacepojo.getTitle(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        mapicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+mostvisitedplacepojo.getTitle());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);

            }
        });


        return view;
    }
}
