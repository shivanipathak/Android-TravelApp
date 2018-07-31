package com.shivani.letsgo.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shivani.letsgo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

public class NearbyPlacesActivity extends FragmentActivity implements OnMapReadyCallback {
    Spinner placespinner;
    Button findbt;
    GoogleMap mGoogleMap;

    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;

    String title="";
    double mLatitude = 0;
    double mLongitude = 0;
    LatLng latlng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);

        title=getIntent().getExtras().getString("title");
        latlng=(LatLng) getIntent().getExtras().get("Latlang");
        mLatitude=latlng.latitude;
        mLongitude=latlng.longitude;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mPlaceType=getResources().getStringArray(R.array.place_type);
        mPlaceTypeName= getResources().getStringArray(R.array.place_type_name);

        ArrayAdapter adapter= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,mPlaceTypeName);

        placespinner=(Spinner)findViewById(R.id.places_spinner);
        placespinner.setAdapter(adapter);
        findbt=(Button)findViewById(R.id.nearbypalcesbt);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        final Resources res = getResources();
        findbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedposition=placespinner.getSelectedItemPosition();
                String type= mPlaceType[selectedposition];


                StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                sb.append("location="+mLatitude+","+mLongitude);
                sb.append("&radius=5000");
                sb.append("&types="+type);
                sb.append("&sensor=true");
                sb.append("&key="+res.getText(R.string.google_maps_key));

                new PlacesTask().execute(sb.toString());
            }
        });
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_action_selectedloc);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude,mLongitude)).title(title).icon(icon));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude,mLongitude),10.0f));
    }


    private class PlacesTask extends AsyncTask<String ,Integer,String>
    {

        @Override
        protected String doInBackground(String... strurl) {

            try
            {
                URL url = new URL(strurl[0]);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                InputStream stream = conn.getInputStream();
                int r;
                StringBuilder builder= new StringBuilder();;
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
        protected void onPostExecute(String s) {



            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            new ParserTask().execute(s);
            //super.onPostExecute(s);

        }
    }


    private class ParserTask extends AsyncTask<String,String,List<HashMap<String,String>>>
    {
        JSONObject jobject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
            List<HashMap<String, String>> places = null;
            PlaceJsonparser placeJsonParser = new PlaceJsonparser();

            try {
                jobject = new JSONObject(jsonData[0]);
                places=placeJsonParser.parse(jobject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            super.onPostExecute(hashMaps);

            for (int i=0;i<hashMaps.size();i++)
            {
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = hashMaps.get(i);

                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                String name = hmPlace.get("place_name");

                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");

                LatLng latLng = new LatLng(lat, lng);

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                //This will be displayed on taping the marker
                markerOptions.title(name + " : " + vicinity);

                // Placing a marker on the touched position
                mGoogleMap.addMarker(markerOptions);

            }
        }
    }




}
