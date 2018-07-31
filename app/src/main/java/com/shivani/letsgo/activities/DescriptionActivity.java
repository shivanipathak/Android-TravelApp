package com.shivani.letsgo.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.PlaceDescriptionpojo;
import com.shivani.letsgo.pojo.ReviewPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DescriptionActivity extends AppCompatActivity {

    ListView view;
    TextView textv1;
    PlaceDescriptionpojo obj;
    List jsonfetchlist;
    ArrayList<String> photos;
    Description_Adapter adapter;
    Button searchbt;
    EditText searchedit;
    String placeid;
    Button reviewsbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_description);

        placeid = getIntent().getExtras().getString("placeid");
        photos = new ArrayList<>();
        view = (ListView) findViewById(R.id.photosgrid);
        textv1 = (TextView) findViewById(R.id.description);
        searchbt = (Button) findViewById(R.id.searchbt);
        searchedit = (EditText) findViewById(R.id.searchedit);
        reviewsbt = (Button) findViewById(R.id.reviewsbt);
        adapter = new Description_Adapter(getApplicationContext(), photos);
        view.setAdapter(adapter);
        new fetchdata().execute();

        reviewsbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent=new Intent(getApplicationContext(),ReviewDescriptionActivity.class);
                intent.putParcelableArrayListExtra("parclereview",obj.getReviews());
                startActivity(intent);
            }
        });


    }


    class fetchdata extends AsyncTask<Void, Void, String> {

        public ArrayList<String> getphotos(JSONArray jarray) {
            ArrayList<String> strings = new ArrayList<>();
            String photo = "";
            JSONObject jsonObject = null;
            for (int i = 0; i < jarray.length(); i++) {
                try {
                    jsonObject = jarray.getJSONObject(i);
                    photo = jsonObject.getString("photo_reference");
                    strings.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=700&photoreference=" + photo + "&key=AIzaSyBUtseU20QDOdHWoh0tT9bPsIHlXeuC5Sg");
                    //strings.add("https://media1.britannica.com/eb-media/95/156695-131-FF89C9FA.jpg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return strings;
        }

        public ArrayList<String> getweekdaytime(JSONArray jsonArray) {
            ArrayList<String> getweekdaytime = new ArrayList<>();
            String day = "";
            // JSONObject jobject=null;
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    day = jsonArray.getString(i);
                    getweekdaytime.add(day);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return getweekdaytime;
        }

        public ArrayList<ReviewPojo> getreviews(JSONArray jsonArray) {
            ArrayList<ReviewPojo> reviewlist = new ArrayList<>();
            JSONObject jobject = null;
            ReviewPojo obj;
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jobject = jsonArray.getJSONObject(i);
                    obj = new ReviewPojo();
                    obj.setAuthor_name(jobject.getString("author_name"));
                    obj.setAuthor_url(jobject.getString("author_url"));
                    obj.setLanguage(jobject.getString("language"));
                    obj.setProfile_photo_url(jobject.getString("profile_photo_url"));
                    obj.setRating(jobject.getString("rating"));
                    obj.setRelative_time_description(jobject.getString("relative_time_description"));
                    obj.setText(jobject.getString("text"));
                    obj.setTime(jobject.getString("time"));
                    reviewlist.add(obj);


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            return reviewlist;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&key=AIzaSyBUtseU20QDOdHWoh0tT9bPsIHlXeuC5Sg");
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


            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                try {
                    obj = new PlaceDescriptionpojo();
                    obj.setAddress(jsonObject1.getString("formatted_address"));
                    obj.setIcon(jsonObject1.getString("icon"));
                    obj.setId(jsonObject1.getString("id"));
                    obj.setPhoneno(jsonObject1.getString("formatted_phone_number"));
                    obj.setPhotos(getphotos(jsonObject1.getJSONArray("photos")));
                    obj.setWeekdaytime(getweekdaytime(jsonObject1.getJSONObject("opening_hours").getJSONArray("weekday_text")));
                }
                catch (Exception e) {
                    //e.printStackTrace();
                }

                textv1.setText("Address: " + obj.getAddress() + "\n\nContact at: " + obj.getPhoneno() + "\n\n" + "Weekday Openings: " + obj.getWeekdaytime());
                obj.setReviews(getreviews(jsonObject1.getJSONArray("reviews")));

                // Toast.makeText(getApplicationContext(),""+obj.getPhotos().get(0),Toast.LENGTH_LONG).show();
                Log.d("tag", "" + obj.getPhotos().get(0));
                adapter.setData(obj.getPhotos());


            } catch (Exception e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }


    }
}