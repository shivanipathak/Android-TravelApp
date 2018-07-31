package com.shivani.letsgo.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.shivani.letsgo.R;
import com.shivani.letsgo.adapters.customized_listviewadapter;
import com.shivani.letsgo.pojo.database_mostvisitedPojo;
import com.shivani.letsgo.utils.SessionMaintain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView mostvisitedlist;
    database_mostvisitedPojo obj;
    List<database_mostvisitedPojo> mostvisitedplacepojoList;
    SessionMaintain  sessionMaintain;
    customized_listviewadapter customized_listviewadapter;
    database_mostvisitedPojo dbpojo;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
databaseHandler=new DatabaseHandler(getApplicationContext());
       // databaseHandler.addplace("adsd","fy","gj");
        //ArrayList<database_mostvisitedPojo> database_mostvisitedPojos=databaseHandler.getDetails();
        //Toast.makeText(getApplicationContext(),"Tab:"+database_mostvisitedPojos.size(),Toast.LENGTH_LONG).show();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sessionMaintain=new SessionMaintain(this);
        if (sessionMaintain.checkLogin())
        {
            finish();
        }

        new databasefetch().execute();

        mostvisitedlist=(ListView)findViewById(R.id.mostvisitedlist);
        mostvisitedplacepojoList =new ArrayList<>();


        customized_listviewadapter= new customized_listviewadapter(getApplicationContext(),mostvisitedplacepojoList,this);
        mostvisitedlist.setAdapter(customized_listviewadapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

      /*  if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/
         if(id==R.id.news)
        {
            Intent i = new Intent(getApplicationContext(),NewsActivity.class);
            startActivity(i);

        }
        else if(id == R.id.news1)
        {
            Intent i = new Intent(getApplicationContext(),NewsActivity.class);
            startActivity(i);

        }
        else if(id == R.id.search1)
        {
            Intent i = new Intent(getApplicationContext(),ListofPlaces.class);
            startActivity(i);
        }
        else if (id == R.id.nearbypalces)
        {

            Intent i = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(i);
        }
        else if(id == R.id.Wishlist)
        {
            Toast.makeText(getApplicationContext(),"my wishlist clicked",Toast.LENGTH_SHORT).show();
            Intent i= new Intent(getApplicationContext(),WishlistActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
public void update(){
    new databasefetch().execute();
}
    class databasefetch extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {


            try {
                URL url = new URL("http://funstudy.co.in/shivani/getplace.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream stream = conn.getInputStream();
                int r;
                StringBuilder builder = new StringBuilder();
                while ((r = stream.read()) != -1) {

                    builder.append((char) r);
                }
                return builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("TAG",s);

            try
            {

                JSONObject json = new JSONObject(s);
                JSONArray jsonArray= json.getJSONArray("data");
                JSONObject jsonObject=null;
                ArrayList<database_mostvisitedPojo> list=databaseHandler.getDetails();
                ArrayList<String> placeids=new ArrayList<>();
                for (int i=0;i<list.size();i++)
                {
                    placeids.add(list.get(i).getPlaceid());
                }
                mostvisitedplacepojoList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    jsonObject=jsonArray.getJSONObject(i);
                    dbpojo= new database_mostvisitedPojo();
                    dbpojo.setTitle(jsonObject.getString("title"));
                    dbpojo.setPlaceid(jsonObject.getString("placeid"));
                    dbpojo.setPhotorefernce("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+jsonObject.getString("image")+"&key=AIzaSyBUtseU20QDOdHWoh0tT9bPsIHlXeuC5Sg");

                    //if (!placeids.contains(jsonObject.getString("placeid")))
                    {
                        mostvisitedplacepojoList.add(dbpojo);
                    }


                }

                customized_listviewadapter.setData(mostvisitedplacepojoList);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }

}
