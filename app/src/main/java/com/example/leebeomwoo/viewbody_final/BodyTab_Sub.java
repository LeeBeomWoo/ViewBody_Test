package com.example.leebeomwoo.viewbody_final;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Fragment.Lower_ExerFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Lower_MuscleFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.StretchingFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Upper_ExerFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Upper_MuscleFragment;
import com.example.leebeomwoo.viewbody_final.Item.MainTabItem;
import com.example.leebeomwoo.viewbody_final.Support.SlidingTabLayout;
import com.example.leebeomwoo.viewbody_final.Adapter.TabsAdapter;

import java.util.ArrayList;

import static android.graphics.Paint.Style.STROKE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BodyTab_Sub extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "0";
    private static final String TAG = "BodyTab_Sub";
    View view;
    TabsAdapter tabsAdapter;
    private ArrayList<MainTabItem> items;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static ViewPager viewPager;
    static SlidingTabLayout slidingTabLayout;
    public BodyTab_Sub() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param Id Parameter 1.
     * @param section Parameter 2.
     * @return A new instance of fragment BodyTab_Sub.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment BodyTab_Sub_start(String Id, String section) {
        BodyTab_Sub fragment = new BodyTab_Sub();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, Id);
        args.putString(ARG_PARAM2, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_body_tab_sub, container, false);
        pageSetup();
        setHasOptionsMenu(true);
        return view;
    }
    private void pageSetup(){
        slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.body_TabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.body_viewPager);
        items = new ArrayList<>();
        items.add(new MainTabItem("상체" + "\n" + "운동", mParam1, Upper_ExerFragment.class));
        items.add(new MainTabItem("상체" + "\n" + "정보", mParam1, Upper_MuscleFragment.class));
        items.add(new MainTabItem("하체" + "\n" + "운동", mParam1, Lower_ExerFragment.class));
        items.add(new MainTabItem("하체" + "\n" + "정보", mParam1, Lower_MuscleFragment.class));
        items.add(new MainTabItem("스트" + "\n" + "레칭", mParam1, StretchingFragment.class));
        tabsAdapter = new TabsAdapter(getChildFragmentManager(), items);
        slidingTabLayout.setCustomTabView(R.layout.customtab_1, R.id.tabIcon_1);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.body_toolbar));
    }
    public void setTabitemSelected(int i){
        if(viewPager == null){
            pageSetup();
        }
        viewPager.setCurrentItem(i, true);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        Log.d(TAG, "onAttach");
        if (context instanceof Activity){
            a=(Activity) context;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

}
