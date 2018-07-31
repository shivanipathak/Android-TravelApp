package com.shivani.letsgo.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.pojo.NewsPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    GridView gridView;
    List<NewsPojo> newsList;
    GridviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        new newsfetch().execute();
        newsList=new ArrayList<>();
        gridView=(GridView)findViewById(R.id.newsgrid);
        adapter= new GridviewAdapter(getApplicationContext(),newsList);
        gridView.setAdapter(adapter);
    }

    class newsfetch extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            try
            {
                URL url = new URL("https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=53f79fdbd3bd4dd5a93300b7bf119417");
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
        protected void onPostExecute(String s) {
            Log.d( "onPostExecute: ",s);


            try
            {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("articles");

                NewsPojo obj;
                JSONObject jsonObject1;
                newsList.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    jsonObject1=jsonArray.getJSONObject(i);
                    obj=new NewsPojo();
                    obj.setAuthor(jsonObject1.getString("author"));
                    obj.setDescription(jsonObject1.getString("description"));
                    obj.setTitle(jsonObject1.getString("title"));
                    obj.setPublishedAt(jsonObject1.getString("publishedAt"));
                    obj.setUrlToImage(jsonObject1.getString("urlToImage"));
                    obj.setUrl(jsonObject1.getString("url"));
                    newsList.add(obj);
                }
                adapter.setData(newsList);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
