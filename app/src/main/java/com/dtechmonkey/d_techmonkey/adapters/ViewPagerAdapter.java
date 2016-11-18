package com.dtechmonkey.d_techmonkey.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dtechmonkey.d_techmonkey.fragments.FragmentHome;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    /*private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> stringList=new ArrayList<>();*/
    private final String[] categories;

    public ViewPagerAdapter(FragmentManager manager, String[] categories) {
        super(manager);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentHome.getInstance(categories[position], position);
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return categories[position];
    }

    /*public void addFragment(Fragment fragment, String string) {
        fragmentList.add(fragment);
        stringList.add(string);
    }*/
}
