package com.shivani.letsgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.adapters.WishlistAdapter;
import com.shivani.letsgo.pojo.database_mostvisitedPojo;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    ListView wishlist;
    ArrayList<String> titleList;
    ArrayList<database_mostvisitedPojo> detailList;
    WishlistAdapter adapter;
    database_mostvisitedPojo obj;
DatabaseHandler databaseHandler;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        wishlist=(ListView)findViewById(R.id.wishlist);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        databaseHandler=new DatabaseHandler(getApplicationContext());
       detailList=databaseHandler.getDetails();
        setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleList=new ArrayList<>();
        for (int i=0;i<detailList.size();i++)
        {
            titleList.add(detailList.get(i).getTitle());
        }
        Log.d( "onCreate: ",titleList.get(0));

        adapter= new WishlistAdapter(getApplicationContext(), R.layout.simple_list_item, titleList);
        wishlist.setAdapter(adapter);
        wishlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.toggleSelection(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wishlist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            EditText editt1=(EditText)findViewById(R.id.editt1);
            String value= editt1.getText().toString();
            titleList.add(value);
            editt1.setVisibility(View.VISIBLE);

            return true;
        }
        if(id == R.id.action_delet)
        {



        }


        return super.onOptionsItemSelected(item);
    }
}
