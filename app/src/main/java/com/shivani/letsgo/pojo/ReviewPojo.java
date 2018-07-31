package com.shivani.letsgo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shivani Pathak on 7/28/2017.
 */

public class ReviewPojo implements Parcelable{
            String author_name ;
            String author_url;
            String language;
            String profile_photo_url;
            String rating;
            String relative_time_description;
            String text ;
            String time;

    public ReviewPojo()
    {

    }


    protected ReviewPojo(Parcel in) {
        author_name = in.readString();
        author_url = in.readString();
        language = in.readString();
        profile_photo_url = in.readString();
        rating = in.readString();
        relative_time_description = in.readString();
        text = in.readString();
        time = in.readString();
    }

    public static final Creator<ReviewPojo> CREATOR = new Creator<ReviewPojo>() {
        @Override
        public ReviewPojo createFromParcel(Parcel in) {
            return new ReviewPojo(in);
        }

        @Override
        public ReviewPojo[] newArray(int size) {
            return new ReviewPojo[size];
        }
    };

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelative_time_description() {
        return relative_time_description;
    }

    public void setRelative_time_description(String relative_time_description) {
        this.relative_time_description = relative_time_description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author_name);
        dest.writeString(author_url);
        dest.writeString(language);
        dest.writeString(profile_photo_url);
        dest.writeString(rating);
        dest.writeString(relative_time_description);
        dest.writeString(text);
        dest.writeString(time);
    }
}
