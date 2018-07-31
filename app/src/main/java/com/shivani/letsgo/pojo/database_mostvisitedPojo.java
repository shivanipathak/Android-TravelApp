package com.shivani.letsgo.pojo;

/**
 * Created by Shivani Pathak on 7/31/2017.
 */

public class database_mostvisitedPojo
{
    String favourite,location,wishlist;
    String img,id,placeid,title,photorefernce;
    public String getImg()
    {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getFavourite()
    {
        return favourite;
    }

    public void setFavourite(String favourite)
    {
        this.favourite = favourite;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getWishlist()
    {
        return wishlist;
    }

    public void setWishlist(String wishlist)
    {
        this.wishlist = wishlist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotorefernce() {
        return photorefernce;
    }

    public void setPhotorefernce(String photorefernce) {
        this.photorefernce = photorefernce;
    }

}
