package com.shivani.letsgo.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.shivani.letsgo.pojo.database_mostvisitedPojo;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Shivani Pathak on 7/31/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "photosManager";
    private static final String PLACEID = "placeid";
    private static final String TITLE = "title";
    private static final String PHOTOREFERENCE = "photoreference";
    private static final String TABLENAME = "photos";
    private static final String COLUMNID = "id";
    ArrayList<database_mostvisitedPojo> details;

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "Create table " + TABLENAME + " ( " + COLUMNID + " integer primary key," + TITLE + " text," + PLACEID + " text," + PHOTOREFERENCE + " text " + ");";
        db.execSQL(CREATE_ITEM_TABLE);
        //Toast.makeText()
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLENAME);
        onCreate(db);
        Log.d("1111","crete");
    }

    public void addplace(String title, String placeid, String photoreference) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(PLACEID, placeid);
        values.put(PHOTOREFERENCE, photoreference);
        sqldb.insert(TABLENAME, null, values);
       // sqldb.close();
    }

    public ArrayList<database_mostvisitedPojo> getDetails() {
        SQLiteDatabase sqldb = this.getWritableDatabase();
       // String selectQuery = "SELECT * FROM " + TABLENAME;

        Cursor cursor = sqldb.rawQuery("select * from "+TABLENAME, null);
        if (cursor!=null) {

            database_mostvisitedPojo databasePOJO = null;
            details = new ArrayList<>();
            while (cursor.moveToNext()) {
                databasePOJO = new database_mostvisitedPojo();


                databasePOJO.setId(cursor.getString(0));
                databasePOJO.setTitle(cursor.getString(1));
                databasePOJO.setPlaceid(cursor.getString(2));
                databasePOJO.setPhotorefernce(cursor.getString(3));
                details.add(databasePOJO);
            }
        }
        return details;
    }
}
