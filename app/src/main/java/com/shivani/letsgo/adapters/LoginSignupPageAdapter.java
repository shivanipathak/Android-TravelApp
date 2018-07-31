package com.shivani.letsgo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shivani.letsgo.fragments.LoginFragment;
import com.shivani.letsgo.fragments.SignupFragment;

/**
 * Created by Shivani Pathak on 7/15/2017.
 */

public class LoginSignupPageAdapter extends FragmentStatePagerAdapter {

    int numtabs;
    public LoginSignupPageAdapter(FragmentManager fm,int numtabs) {
        super(fm);
        this.numtabs=numtabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position)
        {
            case 0:
                fragment=new SignupFragment();
                break;
            case 1:
                fragment=new LoginFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numtabs;
    }
}
