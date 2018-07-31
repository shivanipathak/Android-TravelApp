package com.shivani.letsgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.database_mostvisitedPojo;
import com.shivani.letsgo.utils.SessionMaintain;

import java.util.ArrayList;
import java.util.List;

public class Dashboard1 extends AppCompatActivity {
    ListView mostvisitedlist;
    database_mostvisitedPojo obj;
    List<database_mostvisitedPojo> mostvisitedplacepojoList;
SessionMaintain sessionMaintain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);
        sessionMaintain=new SessionMaintain(this);
        if (sessionMaintain.checkLogin())
        {
            finish();
        }
        mostvisitedlist=(ListView)findViewById(R.id.mostvisitedlist);
        mostvisitedplacepojoList =new ArrayList<>();

        obj=new database_mostvisitedPojo();
        //obj.setImg(R.drawable.s4);
        mostvisitedplacepojoList.add(obj);


        obj=new database_mostvisitedPojo();
        //obj.setImg(R.drawable.slider2);
        mostvisitedplacepojoList.add(obj);

        obj=new database_mostvisitedPojo();
        //obj.setImg(R.drawable.slider3);
        mostvisitedplacepojoList.add(obj);



    }
}
