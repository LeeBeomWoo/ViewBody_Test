package com.example.leebeomwoo.viewbody_final;

/**
 * Created by LeeBeomWoo on 2017-04-12.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leebeomwoo.viewbody_final.Adapter.TabsAdapter;
import com.example.leebeomwoo.viewbody_final.Fragment.Follow_BrethFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Follow_CoreFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Follow_MuscleUpFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Follow_StretchingFragment;
import com.example.leebeomwoo.viewbody_final.Item.MainTabItem;
import com.example.leebeomwoo.viewbody_final.Support.SlidingTabLayout;

import java.util.ArrayList;

/**
     * A simple {@link Fragment} subclass.
     * Activities that contain this fragment must implement the
     * to handle interaction events.
     * create an instance of this fragment.
     */
    public class FollowTab_Sub extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "0";
        View view;
        private ArrayList<MainTabItem> items;
        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
        static ViewPager viewPager;
        public FollowTab_Sub() {
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
        public static Fragment FollowTab_Sub_start(String Id, String section) {
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
            view = inflater.inflate(R.layout.fragment_follow_tab_sub, container, false);
            pageSetup();
            setHasOptionsMenu(true);
            return view;
        }
        private void pageSetup(){
            items = new ArrayList<>();
            items.add(new MainTabItem("코어운동" + "\n" + "따라하기", mParam1, Follow_CoreFragment.class));
            items.add(new MainTabItem("유산소운동" + "\n" + "따라하기", mParam1, Follow_BrethFragment.class));
            items.add(new MainTabItem("근력운동" + "\n" + "따라하기", mParam1, Follow_MuscleUpFragment.class));
            items.add(new MainTabItem("스트레칭" + "\n" + "따라하기", mParam1, Follow_StretchingFragment.class));

            final SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.follow_TabLayout);
            viewPager = (ViewPager) view.findViewById(R.id.follow_viewPager);

            slidingTabLayout.setCustomTabView(R.layout.customtab_2, R.id.tabIcon_2);
            viewPager.setAdapter(new TabsAdapter(getChildFragmentManager(), items));
            slidingTabLayout.setViewPager(viewPager);
            slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.followtoolbar));
        }
    public void setTabitemSelected(int i){
        if(viewPager == null){
            pageSetup();
        }
        viewPager.setCurrentItem(i, true);
    }
    }


