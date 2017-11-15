package com.example.leebeomwoo.viewbody_final;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.leebeomwoo.viewbody_final.Fragment.Tutorial_1;
import com.example.leebeomwoo.viewbody_final.Fragment.Tutorial_2;
import com.example.leebeomwoo.viewbody_final.Fragment.Tutorial_3;
import com.example.leebeomwoo.viewbody_final.Fragment.Tutorial_4;
import com.example.leebeomwoo.viewbody_final.Fragment.Tutorial_5;
import com.example.leebeomwoo.viewbody_final.Fragment.Tutorial_6;

import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;

public class IntroActivity extends AppCompatActivity {
    ImageButton next, back;
    ViewPager viewPager;
    CheckBox checkedTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        next = (ImageButton) findViewById(R.id.next_tutorial_Btn);
        back = (ImageButton) findViewById(R.id.before_tutorial_Btn);
        viewPager = (ViewPager) findViewById(R.id.tutorial);
        checkedTextView = (CheckBox) findViewById(R.id.checkedTextView);

        viewPager.setAdapter(new PagerAdapterClass(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        PortSet();
                        next.setImageResource(R.drawable.arrows);
                        back.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        PortSet();
                        next.setImageResource(R.drawable.arrows);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        PortSet();
                        next.setImageResource(R.drawable.arrows);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        PortSet();
                        next.setImageResource(R.drawable.arrows);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        PortSet();
                        next.setImageResource(R.drawable.arrows);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        LandSet();
                        next.setImageResource(R.drawable.close);
                        back.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 5) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                } else {
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });
        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if(checkedTextView.isChecked()){
                    editor.putInt("First", 1);
                } else{
                    editor.putInt("First", 0);
                }
                editor.apply();
            }
        });
    }
    private void LandSet(){
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        next.setScaleY((float)0.5);
        back.setScaleY((float)0.5);
        next.setScaleX((float)0.5);
        back.setScaleX((float)0.5);
        ScaleRelativeLayout.LayoutParams layoutParams = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.landcheckSiz_item));
        layoutParams.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        checkedTextView.setLayoutParams(layoutParams);
    }
    private void PortSet(){
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private class PagerAdapterClass extends FragmentStatePagerAdapter
    {
        public PagerAdapterClass(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new Tutorial_1();
                case 1:
                    return new Tutorial_2();
                case 2:
                    return new Tutorial_3();
                case 3:
                    return new Tutorial_4();
                case 4:
                    return new Tutorial_5();
                case 5:
                    return new Tutorial_6();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 6;
        }
    }

}
