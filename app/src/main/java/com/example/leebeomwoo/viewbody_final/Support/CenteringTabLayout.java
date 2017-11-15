package com.example.leebeomwoo.viewbody_final.Support;

import android.content.Context;
import android.support.design.widget.TabLayout;
import cn.gavinliu.android.lib.scale.ScaleFrameLayout;
import cn.gavinliu.android.lib.scale.ScaleLinearLayout;
import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LeeBeomWoo on 2017-06-13.
 */

public class CenteringTabLayout extends TabLayout {
    public CenteringTabLayout(Context context) {
        super(context);
    }

    public CenteringTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenteringTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View firstTab = ((ViewGroup)getChildAt(0)).getChildAt(0);
        View lastTab = ((ViewGroup)getChildAt(0)).getChildAt(((ViewGroup)getChildAt(0)).getChildCount()-1);
        ViewCompat.setPaddingRelative(getChildAt(0), (getWidth()/2) - (firstTab.getWidth()/2),0,(getWidth()/2) - (lastTab.getWidth()/2),0);
    }

}