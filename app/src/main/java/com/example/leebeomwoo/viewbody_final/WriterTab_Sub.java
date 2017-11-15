package com.example.leebeomwoo.viewbody_final;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leebeomwoo.viewbody_final.Adapter.TabsAdapter;
import com.example.leebeomwoo.viewbody_final.Fragment.Upper_ExerFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Upper_MuscleFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.WriterListFragment;
import com.example.leebeomwoo.viewbody_final.Fragment.Writer_Fragment;
import com.example.leebeomwoo.viewbody_final.Item.MainTabItem;
import com.example.leebeomwoo.viewbody_final.Support.SlidingTabLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WriterTab_Sub extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "0";
    private static final String TAG = "WriterTab_Sub";
    View view;
    static ViewPager viewPager;
    private ArrayList<MainTabItem> items;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WriterTab_Sub() {
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
    public static Fragment WriterTab_Sub_start(String Id, String section) {
        WriterTab_Sub fragment = new WriterTab_Sub();
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
    private void pageSetup(){
        items = new ArrayList<>();
        items.add(new MainTabItem("영양사", mParam1, WriterListFragment.class));
        items.add(new MainTabItem("트레이너", mParam1, WriterListFragment.class));

        final SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.writer_TabLayout);
        slidingTabLayout.setCustomTabView(R.layout.customtab_4, R.id.tabIcon_4);
        viewPager = (ViewPager) view.findViewById(R.id.writer_viewPager);

        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager(), items));
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.writertoolbar));
    }
    public void setTabitemSelected(int i){
        if(viewPager == null){
            pageSetup();
        }
        viewPager.setCurrentItem(i, true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_writer_tab_sub, container, false);
        pageSetup();
        setHasOptionsMenu(true);
        return view;
    }

    public void changePage(int p){
        viewPager.setCurrentItem(p, true);
    }
}
