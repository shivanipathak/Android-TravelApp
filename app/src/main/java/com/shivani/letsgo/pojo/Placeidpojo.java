package com.shivani.letsgo.pojo;

import java.io.Serializable;

/**
 * Created by Shivani Pathak on 7/28/2017.
 */

public class Placeidpojo implements Serializable{
    public String place;
    public String placeid;
    public Placeidpojo(String placeid,String place)
    {
        this.place=place;
        this.placeid=placeid;
    }


}
