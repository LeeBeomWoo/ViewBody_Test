package com.example.leebeomwoo.viewbody_final.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.leebeomwoo.viewbody_final.R;

/**
 * Created by Lee on 2017-07-04.
 */

public class Tutorial_4 extends Fragment
{
    public Tutorial_4()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ImageView layout = (ImageView) inflater.inflate(R.layout.tutorial_4, container, false);
        return layout;
    }
}
