package com.shivani.letsgo.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shivani.letsgo.R;
import com.shivani.letsgo.adapters.PageAdapterImage;
import com.shivani.letsgo.customview.CirclePageIndicator;
import com.shivani.letsgo.listeners.PageIndicator;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    ViewPager viewPager;
    PageAdapterImage imageadapter;
    CirclePageIndicator circlePageIndicator;
    Button signupbt,loginbt;
    long speed=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=(ViewPager)findViewById(R.id.pager);
        circlePageIndicator=(CirclePageIndicator)findViewById(R.id.indicatorCircle);
        imageadapter= new PageAdapterImage(getSupportFragmentManager(),3);
        viewPager.setAdapter(imageadapter);
        circlePageIndicator.setViewPager(viewPager);


        signupbt=(Button)findViewById(R.id.signupbt);
        loginbt=(Button)findViewById(R.id.loginbt);
        handler= new Handler();
        handler.postDelayed(runnable,speed);
        signupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignupLoginActivity.class);
                intent.putExtra("show","0");
                startActivity(intent);
                finish();
            }
        });
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignupLoginActivity.class);
                intent.putExtra("show","1");
                startActivity(intent);
                finish();
            }
        });

    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            animate();
        }
    };
    public void animate(){
        if (viewPager.getCurrentItem()==2)
        {
            viewPager.setCurrentItem(0);
        }
        else
        {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
        handler.postDelayed(runnable,speed);
    }
}
