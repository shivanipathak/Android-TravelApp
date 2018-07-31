package com.shivani.letsgo.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shivani Pathak on 7/27/2017.
 */

public class PlaceJsonparser {
    List<HashMap<String, String>> parse(JSONObject jobject) {
        JSONArray jplaces = null;
        try {
            jplaces = jobject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jplaces);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {

        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> place = null;

        for (int i = 0; i < placesCount; i++) {
            try {
                /** Call getPlace with place JSON object to parse the place */
                place = getPlace((JSONObject) jPlaces.get(i));
                placesList.add(place);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject jPlace){

    HashMap<String, String> place = new HashMap<String, String>();
    String placeName = "-NA-";
    String vicinity = "-NA-";
    String latitude = "";
    String longitude = "";


		try

        {
        if (!jPlace.isNull("name")) {
            placeName = jPlace.getString("name");
        }

         if (!jPlace.isNull("vicinity")) {
            vicinity = jPlace.getString("vicinity");
        }

        latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
        longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");

        place.put("place_name", placeName);
        place.put("vicinity", vicinity);
        place.put("lat", latitude);
        place.put("lng", longitude);
        }
    catch(Exception e)
    {
        e.printStackTrace();
    }
		return place;
}
}
