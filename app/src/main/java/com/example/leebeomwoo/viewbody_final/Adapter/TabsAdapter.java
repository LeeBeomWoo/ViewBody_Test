package com.example.leebeomwoo.viewbody_final.Adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.leebeomwoo.viewbody_final.Item.MainTabItem;

import java.util.ArrayList;


/**
 * Created by charles on 14/09/15.
 */
public class TabsAdapter
        extends FragmentPagerAdapter {

    private ArrayList<MainTabItem> items;
    private String sEction;

    public TabsAdapter(FragmentManager fm, ArrayList<MainTabItem> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }

    public String getsEction() {
        return sEction;
    }

    public void setsEction(String sEction) {
        this.sEction = sEction;
    }
}