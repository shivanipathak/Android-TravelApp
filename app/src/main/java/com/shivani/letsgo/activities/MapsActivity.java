package com.shivani.letsgo.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shivani.letsgo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText searchedit;
    Button searchbt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchedit =(EditText)findViewById(R.id.searchedit);
        searchbt=(Button)findViewById(R.id.searchbt);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        searchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place=searchedit.getText().toString();
                new Mapsearch(place).execute();

            }
        });
    }

    class Mapsearch extends AsyncTask<String,String,String> {

        String place;
        public Mapsearch(String place)
        {
            this.place=place;
        }
        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/" +
                        "json?query="+ URLEncoder.encode(place+" point of interest","UTF-8")+"&key=AIzaSyBUtseU20QDOdHWoh0tT9bPsIHlXeuC5Sg");
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                InputStream stream = conn.getInputStream();
                int r;
                StringBuilder builder= new StringBuilder();
                while((r=stream.read())!=-1)
                {

                    builder.append((char)r);
                }
                return builder.toString();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            Log.d( "onPostExecute: ",s);
            if (s!=null){
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray("results");
                    LatLng latLng=null;
                    JSONObject jsonObject1=null;
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject1=jsonArray.getJSONObject(i);

                        latLng= new LatLng(jsonObject1.getJSONObject("geometry").getJSONObject("location").getDouble("lat"), jsonObject1.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                        mMap.addMarker(new MarkerOptions().position(latLng).title(jsonObject1.getString("name")));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                              //  Toast.makeText(getApplicationContext(),marker.getTitle(),Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(),NearbyPlacesActivity.class);
                                Log.d("chla", "onMarkerClick: +++++++++++");
                                i.putExtra("title",marker.getTitle());
                                i.putExtra("Latlang",marker.getPosition());
                                startActivity(i);
                                return false;
                            }
                        });
                    }
                }catch (Exception e){e.printStackTrace();}
            }

        }
    }

}
