package com.shivani.letsgo.pojo;

import java.util.ArrayList;

/**
 * Created by Shivani Pathak on 7/28/2017.
 */

public class PlaceDescriptionpojo {
    String address;
    String icon;
    String id;
    String phoneno;
    ArrayList<String> photos;
    ArrayList<String> weekdaytime;
    ArrayList<ReviewPojo> reviews;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<String> getWeekdaytime() {
        return weekdaytime;
    }

    public void setWeekdaytime(ArrayList<String> weekdaytime) {
        this.weekdaytime = weekdaytime;
    }

    public ArrayList<ReviewPojo> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<ReviewPojo> reviews) {
        this.reviews = reviews;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }



}
