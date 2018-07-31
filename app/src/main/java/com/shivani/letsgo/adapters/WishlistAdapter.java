package com.shivani.letsgo.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shivani.letsgo.R;

import java.util.ArrayList;

/**
 * Created by Shivani Pathak on 7/31/2017.
 */

public class WishlistAdapter extends ArrayAdapter<String> {

    Context context;
    LayoutInflater li;
    ArrayList<String> titleList;
    SparseBooleanArray sparseBooleanArray;
    TextView textView;
    public WishlistAdapter(@NonNull Context context, @LayoutRes int resource,ArrayList<String> titleList)
    {
        super(context, resource,titleList);
        this.context=context;
        this.titleList=titleList;
        li= LayoutInflater.from(context);
        sparseBooleanArray=new SparseBooleanArray();
    }
public void setData(ArrayList<String> titleList){
    this.titleList=titleList;
    notifyDataSetChanged();
}
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=null;
        if (view!=null)
        {
            view=convertView;
        }
        else
        {
            view=li.inflate(R.layout.wishlist_listview,null);
        }

        textView=(TextView)view.findViewById(R.id.wishlist_textview);
        textView.setText(titleList.get(position));
        return view;
    }

    @Override
    public void remove(@Nullable String object) {
        titleList.remove(object);
        notifyDataSetChanged();
        //super.remove(object);
    }
    public ArrayList<String> getItemsData(){
        return titleList;
    }
    public void toggleSelection(int postion){
        selectView(postion,!sparseBooleanArray.get(postion));
        selectPostion(postion,!sparseBooleanArray.get(postion));

    }
    public void selectView(int pos,boolean value){
        if (value){
            sparseBooleanArray.put(pos, value);
        }
        else
        {
            sparseBooleanArray.delete(pos);
        }
        notifyDataSetChanged();
    }
    ArrayList<Integer> postions=new ArrayList<>();
    public void selectPostion(int pos,boolean value){
        if (value){
            postions.remove(new Integer(pos));
        }
        else
        {
            postions.add(new Integer(pos));
        }
        notifyDataSetChanged();
    }
    public void removeSelection(){
        sparseBooleanArray=new SparseBooleanArray();
        postions.clear();
        notifyDataSetChanged();
    }

    public int getSelectedCount(){
       return sparseBooleanArray.size();
    }
    public SparseBooleanArray getSelectedIds(){
        return sparseBooleanArray;
    }
}
