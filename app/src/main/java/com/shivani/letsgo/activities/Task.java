package com.shivani.letsgo.activities;

import android.os.AsyncTask;

import com.shivani.letsgo.pojo.database_mostvisitedPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivani Pathak on 7/18/2017.
 */

public class Task extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... params)
    {
        StringBuilder builder=null;
        try
        {
            URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC-Qzdx2YISLNU6gEJ27YRiQ&m" +
                    "axResults=10&type=video&key=AIzaSyAPRNR2okwnZaFVdrFvq7WagZ8vVamQOpY");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            InputStream stream = conn.getInputStream();
            int r;
            builder= new StringBuilder();;
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
        return builder.toString();
    }

    @Override
    protected void onPostExecute(String s)
    {

        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            List<database_mostvisitedPojo> jsonList=new ArrayList<>();
            JSONObject jsonObject1;
            database_mostvisitedPojo list_viewpojo;
            for (int i=0;i<jsonArray.length();i++)
            {
                jsonObject1=jsonArray.getJSONObject(i);
                list_viewpojo=new database_mostvisitedPojo();

               // list_viewpojo.setImg(jsonObject1.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url"));
                jsonList.add(list_viewpojo);
            }

        }catch (Exception e){ e.printStackTrace();}

        super.onPostExecute(s);
    }
}
