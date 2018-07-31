package com.shivani.letsgo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shivani.letsgo.fragments.ImageFragment;

/**
 * Created by Shivani Pathak on 7/15/2017.
 */

public class PageAdapterImage extends FragmentStatePagerAdapter {

    int NumofTabs;
    public PageAdapterImage(FragmentManager fm,int NumofTabs)
    {
        super(fm);
        this.NumofTabs=NumofTabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment= null;
        switch (position)
        {
            case 0:
                fragment=ImageFragment.newInstance("0","");
                break;
            case 1:
                fragment=ImageFragment.newInstance("1","");
                break;
            case 2:
                fragment=ImageFragment.newInstance("2","");
                break;
        }

        return fragment;
    }

    @Override
    public int getCount()
    {
        return NumofTabs;
    }
}
