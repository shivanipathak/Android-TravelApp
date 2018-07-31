package com.shivani.letsgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.ReviewPojo;

import java.util.ArrayList;

public class ReviewDescriptionActivity extends AppCompatActivity {
    ///PlaceDescriptionpojo obj;
    ReviewPojo reviewPojo;
    ArrayList<ReviewPojo> reviewslist;
    listviewcustomized_adapter adapter;
    ListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_description);

        reviewslist=new ArrayList<>();
        view=(ListView)findViewById(R.id.reviewlist);
        reviewslist=getIntent().getParcelableArrayListExtra("parclereview");
    //   for (int i=0;i<reviewslist.size();i++)
      // {

       //}

        adapter= new listviewcustomized_adapter(getApplicationContext(),reviewslist);
        view.setAdapter(adapter);
    }
}
