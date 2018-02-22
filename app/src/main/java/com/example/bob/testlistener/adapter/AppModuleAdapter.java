package com.example.bob.testlistener.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bob.testlistener.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 2017/10/31.
 */

public class AppModuleAdapter extends FragmentPagerAdapter {

    public List<BaseFragment> APP_MODULE = new ArrayList<>();

    public AppModuleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return APP_MODULE.get(position);
    }

    @Override
    public int getCount() {
        return APP_MODULE.size();
    }
}
