package com.example.leebeomwoo.viewbody_final.Item;

import android.graphics.Color;
import android.support.v4.app.Fragment;

/**
 * Created by LeeBeomWoo on 2017-03-27.
 */

public class MainTabItem {
    private String title, id;
    private Class fragmentClass;

    public MainTabItem(String title, String Id, Class fragmentClass) {
        this.title = title;
        this.id = Id;
        this.fragmentClass = fragmentClass;
    }

    public Fragment getFragment(){
        try {
            return (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
