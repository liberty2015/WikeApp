package com.liberty.wikepro.view.widget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<? extends Fragment> fragments;
    private String [] titles;

    public ViewPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    public ViewPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments,String[] titles){
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles!=null&&titles.length>0){
            return titles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
