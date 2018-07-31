package com.shivani.letsgo.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.adapters.LoginSignupPageAdapter;
import com.shivani.letsgo.fragments.LoginFragment;
import com.shivani.letsgo.fragments.SignupFragment;

public class SignupLoginActivity extends AppCompatActivity {
    LoginSignupPageAdapter adapter;


    ViewPager loginsignupviewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);


        String signupmsg="";
        try{
            Bundle extras=getIntent().getExtras();
            signupmsg= extras.getString("show");
        }catch (Exception e){ e.printStackTrace();}



       adapter=new LoginSignupPageAdapter(getSupportFragmentManager(),2);

        loginsignupviewer=(ViewPager)findViewById(R.id.loginsignupviewer);
        loginsignupviewer.setAdapter(adapter);

        if(signupmsg.length()> 0)

        {
            if (signupmsg.equals("0"))
            {
                loginsignupviewer.setCurrentItem(0);
            }
            else if (signupmsg.equals("1"))
            {
                loginsignupviewer.setCurrentItem(1);
            }
        }
    }
    public void showTab(int i){
        loginsignupviewer.setCurrentItem(i);
    }
}
