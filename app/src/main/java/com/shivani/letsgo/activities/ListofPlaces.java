package com.shivani.letsgo.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.Placeidpojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ListofPlaces extends AppCompatActivity {

    ListView listofplaces;
    Button searchbt;
    EditText searchedit;
    ArrayList<Placeidpojo> list;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_places);
        list=new ArrayList<>();
        listofplaces = (ListView) findViewById(R.id.listofplaces);
        searchbt=(Button)findViewById(R.id.searchbt);
        searchedit=(EditText)findViewById(R.id.searchedit);

        adapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_list_item,listname);

        listofplaces.setAdapter(adapter);

        listofplaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Placeidpojo placeidpojo= list.get(position);

                Toast.makeText(getApplicationContext(),""+placeidpojo.placeid,Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),DescriptionActivity.class);
                i.putExtra("placeid",placeidpojo.placeid);
                startActivity(i);

            }
        });

        searchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = searchedit.getText().toString();
                new Placesearch(place).execute();
            }
        });
    }

    List<String> listname=new ArrayList<>();


    class Placesearch extends AsyncTask<String, String, String> {

        String place;
        ArrayList<Placeidpojo> placeidlist;
        public Placesearch(String place) {
            this.place = place;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/" +
                        "json?query=" + URLEncoder.encode(place + " point of interest", "UTF-8") + "&key=AIzaSyBUtseU20QDOdHWoh0tT9bPsIHlXeuC5Sg");
                Log.d("hhhh",url.toString());
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
            Log.d("onPostExecute: ", s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    LatLng latLng = null;
                    JSONObject jsonObject1 = null;
                    listname.clear();
                    list.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject1 = jsonArray.getJSONObject(i);


                        latLng = new LatLng(jsonObject1.getJSONObject("geometry").getJSONObject("location").getDouble("lat"), jsonObject1.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                        String name = jsonObject1.getString("name");
                        String placeid = jsonObject1.getString("place_id");
                        Placeidpojo obj = new Placeidpojo(placeid, name);
                        listname.add(name);
                        list.add(obj);
                    }


                    adapter.addAll(listname);
                    adapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}