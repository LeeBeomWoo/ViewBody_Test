package com.example.leebeomwoo.viewbody_final;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Adapter.LicenseListAdapter;
import com.example.leebeomwoo.viewbody_final.Adapter.StableArrayAdapter;
import com.example.leebeomwoo.viewbody_final.Adapter.TabsAdapter;
import com.example.leebeomwoo.viewbody_final.Fragment.QnAFragment;
import com.example.leebeomwoo.viewbody_final.Item.LicenseItem;
import com.example.leebeomwoo.viewbody_final.Item.MainTabItem;
import com.example.leebeomwoo.viewbody_final.Support.CenteringTabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import cn.gavinliu.android.lib.scale.ScaleLinearLayout;
import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;
import cn.gavinliu.android.lib.scale.config.ScaleConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    DrawerLayout mDrawerLayout;
    SearchView mSearchView;
    MenuItem myActionMenuItem, menu_more;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.7f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.4f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    TextView license_source, license_Title, mTitle;
    ViewPager viewPager;
    ScaleLinearLayout mTitleContainer;
    PopupWindow mPopupWindow;
    Context context;
    ImageButton cancel_menuBtn, account_menuBtn, body_menuBtn, follow_menuBtn, food_menuBtn, qna_menuBtn, writer_menuBtn, licenseBtn, back, menu, menuHomeBtn;
    BodyTab_Sub bodyTab_sub;
    FollowTab_Sub followTab_sub;
    FoodTab_Sub foodTab_sub;
    WriterTab_Sub writerTab_sub;
    ListView menu_list;
    ScrollView menu_Scroll;
    LinearLayout btn_View, main, top;
    CheckBox checkedTextView;
    String[] body, follow, food, trainer, license;
    String title_temp, transition;
    int i = 0;
    int a, b, result, main_img;
    CollapsingToolbarLayout toolbarLayout;
    Toolbar toolbar;
    AppBarLayout mAppBarLayout;
    ImageView toolbar_space, collapse_space, main_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        setContentView(R.layout.activity_main_page);
        bindActivity();
        mAppBarLayout.addOnOffsetChangedListener(this);
        toolbar.inflateMenu(R.menu.menu_main);
        supportPostponeEnterTransition();
        if(getIntent().hasExtra("message")) {
            Bundle bundle = getIntent().getExtras();
            i = bundle.getInt("message");
            transition = bundle.getString("transition_name");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            collapse_space.setTransitionName(transition);
        }
        Picasso.with(this)
                .load(imageSet(i))
                .fit()
                .noFade()
                .centerCrop()
                .into(collapse_space, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }
                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });

        context = this;
        // Get access to the custom title view
        // Display icon in the toolbar
        // toolbar.inflateMenu(R.menu.menu_main);
        // mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        ArrayList<MainTabItem> mainMenuItems = new ArrayList<>();
        mainMenuItems.add(new MainTabItem("홈", null, Home_Tab.class));
        mainMenuItems.add(new MainTabItem("몸과 운동", null, BodyTab_Sub.class));
        mainMenuItems.add(new MainTabItem("동영상 따라하기", null, FollowTab_Sub.class));
        mainMenuItems.add(new MainTabItem("음식과 영양", null, FoodTab_Sub.class));
        mainMenuItems.add(new MainTabItem("트레이너와 영양사", null, WriterTab_Sub.class));
        mainMenuItems.add(new MainTabItem("묻고 답하기", null, QnAFragment.class));
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), mainMenuItems));
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(final int position) {
                toolbarLayout.setExpandedTitleColor(getResources().getColor(tabColor(position)[0]));
                toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(tabColor(position)[1]));
                mTitle.setText(tabtitle(position));
                toolbar_space.setImageDrawable(getResources().getDrawable(imageSet(position)));
                collapse_space.setImageDrawable(getResources().getDrawable(imageSet(position)));
                main_back.setImageDrawable(getResources().getDrawable(main_set(position)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setCurrentItem(i, true);
        collapse_space.setOnClickListener(this);
        toolbar_space.setOnClickListener(this);
    }
    private int imageSet(int f){
        switch (f){
            case 0:
                result = R.drawable.menu_new_ico;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    collapse_space.setTransitionName("home");
                }
                break;
            case 1:
                result = R.drawable.menu_body_ico;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    collapse_space.setTransitionName("body");
                }
                break;
            case 2:
                result = R.drawable.menu_follow_ico;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    collapse_space.setTransitionName("follow");
                }
                break;
            case 3:
                result = R.drawable.menu_food_ico;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    collapse_space.setTransitionName("food");
                }
                break;
            case 4:
                result = R.drawable.menu_writer_ico;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    collapse_space.setTransitionName("writer");
                }
                break;
            case 5:
                result = R.drawable.menu_qna_ico;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    collapse_space.setTransitionName("qna");
                }
                break;
        }
        return result;
    }

    private int main_set(int w){
        switch (w){
            case 0:
                main_img = R.drawable.test_1;
                toolbar_space.setBackground(getResources().getDrawable(R.drawable.circle_toolbar));
                collapse_space.setBackground(getResources().getDrawable(R.drawable.circle_new));
                break;
            case 1:
                main_img = R.drawable.test_2;
                toolbar_space.setBackground(getResources().getDrawable(R.drawable.circle_toolbar));
                collapse_space.setBackground(getResources().getDrawable(R.drawable.circle_body));
                break;
            case 2:
                main_img = R.drawable.test_1;
                toolbar_space.setBackground(getResources().getDrawable(R.drawable.circle_toolbar));
                collapse_space.setBackground(getResources().getDrawable(R.drawable.circle_follow));
                break;
            case 3:
                main_img = R.drawable.test_2;
                toolbar_space.setBackground(getResources().getDrawable(R.drawable.circle_toolbar));
                collapse_space.setBackground(getResources().getDrawable(R.drawable.circle_food));
                break;
            case 4:
                main_img = R.drawable.test_1;
                toolbar_space.setBackground(getResources().getDrawable(R.drawable.circle_toolbar));
                collapse_space.setBackground(getResources().getDrawable(R.drawable.circle_writer));
                break;
            case 5:
                main_img = R.drawable.test_2;
                toolbar_space.setBackground(getResources().getDrawable(R.drawable.circle_toolbar));
                collapse_space.setBackground(getResources().getDrawable(R.drawable.circle_qna));
                break;
        }
        return main_img;
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        if (offset == 0)
        {
            // Collapsed
            toolbar.setBackgroundColor(getResources().getColor(R.color.nocolor));
        }
        else
        {
            // Not collapsed
            toolbar.setBackgroundColor(getResources().getColor(tabColor(viewPager.getCurrentItem())[0]));
        }
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
        handleAlphaOnImage(percentage);
    }
    private void bindActivity() {
        mAppBarLayout = findViewById(R.id.appbar);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbar =  findViewById(R.id.toolbar);
        mTitle =  findViewById(R.id.toolbar_textview_title);
        mTitleContainer = findViewById(R.id.main_linearlayout_title);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.main_viewPager);
        toolbar_space = findViewById(R.id.tool_space);
        collapse_space = findViewById(R.id.expand_space);
        main_back = findViewById(R.id.main_imageview_placeholder);
    }
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }
    private void handleAlphaOnImage(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(toolbar_space, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);

                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(toolbar_space, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }
    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
    private String tabtitle(int a){
        switch (a){
            case 0:
                title_temp = "홈";
                break;
            case 1:
                title_temp = "몸과 운동";
                break;
            case 2:
                title_temp = "동영상 따라하기";
                break;
            case 3:
                title_temp = "영양과 음식";
                break;
            case 4:
                title_temp = "트레이너와 영양사";
                break;
            case 5:
                title_temp = "묻고답하기";
                break;
            case 6:
                title_temp = "홈";
                break;
            case 7:
                title_temp = "몸과"+ "\n" + "운동";
                break;
            case 8:
                title_temp = "동영상"+ "\n" + "따라하기";
                break;
            case 9:
                title_temp = "영양과"+ "\n" + "음식";
                break;
            case 10:
                title_temp = "트레이너와" +"\n" + "영양사";
                break;
            case 11:
                title_temp = "묻고" + "\n" + "답하기";
                break;
        }
        return title_temp;
    }
    private int[] tabColor(int i){
        switch (i){
            case 0:
                a = (R.color.newtoolbar);
                b = (R.color.newsubtabcolor);
                break;
            case 1:
                a =(R.color.body_toolbar);
                b = (R.color.bodysubtabcolor);
                break;
            case 2:
                a =(R.color.followtoolbar);
                b = (R.color.followsubtabcolor);
                break;
            case 3:
                a =(R.color.foodtoolbar);
                b = (R.color.foodsubtabcolor);
                break;
            case 4:
                a =(R.color.writertoolbar);
                b = (R.color.writersubtabcolor);
                break;
            case 5:
                a =(R.color.qnatoolbar);
                b = (R.color.qnasubtabcolor);
                break;
        }
        int[] c = {a, b};
        return c;
    }
    public void popupDisplay()
    {
        try {
            View popupView = getLayoutInflater().inflate(R.layout.menu, (ViewGroup) findViewById(R.id.menu_main));
            /**
             * LayoutParams WRAP_CONTENT를 주면 inflate된 View의 사이즈 만큼의
             * PopupWinidow를 생성한다.
             *
             mPopupWindow = new PopupWindow(popupView,
             RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
             */
            ScaleRelativeLayout.LayoutParams params = new ScaleRelativeLayout.LayoutParams(1000, ScaleRelativeLayout.LayoutParams.MATCH_PARENT);
            params.setLayoutDirection(ScaleRelativeLayout.LAYOUT_DIRECTION_LTR);
            popupView.setLayoutParams(params);
            cancel_menuBtn = popupView.findViewById(R.id.cancel_menuBtn);
            account_menuBtn = popupView.findViewById(R.id.account_menuBtn);
            body_menuBtn = popupView.findViewById(R.id.body_menuBtn);
            follow_menuBtn = popupView.findViewById(R.id.follow_menuBtn);
            food_menuBtn = popupView.findViewById(R.id.food_menuBtn);
            qna_menuBtn = popupView.findViewById(R.id.qna_Btn);
            writer_menuBtn = popupView.findViewById(R.id.writer_menuBtn);
            menu_list = popupView.findViewById(R.id.menu_list);
            menu_Scroll = popupView.findViewById(R.id.menu_Scroll);
            btn_View = popupView.findViewById(R.id.btn_View);
            main = popupView.findViewById(R.id.menu_main);
            top = popupView.findViewById(R.id.menu_top);
            menuHomeBtn = popupView.findViewById(R.id.menuBtn_home);
            //licenseBtn = popupView.findViewById(R.id.license_Btn);
            license_source = popupView.findViewById(R.id.sourceTxt);
            license_Title = popupView.findViewById(R.id.titleTxt);
            checkedTextView = popupView.findViewById(R.id.menuchecked);

            mPopupWindow = new PopupWindow(popupView, 700, ScaleRelativeLayout.LayoutParams.MATCH_PARENT, true);

            cancel_menuBtn.setOnClickListener(this);
            account_menuBtn.setOnClickListener(this);
            body_menuBtn.setOnClickListener(this);
            follow_menuBtn.setOnClickListener(this);
            food_menuBtn.setOnClickListener(this);
            qna_menuBtn.setOnClickListener(this);
            writer_menuBtn.setOnClickListener(this);
            // licenseBtn.setOnClickListener(this);
            checkedTextView.setOnClickListener(this);
            SharedPreferences preferencesCompat = getSharedPreferences("a", MODE_PRIVATE);
            int tutorial = preferencesCompat.getInt("First", 0);
            if (tutorial == 0) {
                checkedTextView.setChecked(true);
            } else {
                checkedTextView.setChecked(false);
            }
            menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    switch (item) {
                        case "상체 운동":
                            bodyTab_sub = new BodyTab_Sub();
                            bodyTab_sub.setTabitemSelected(0);
                            mPopupWindow.dismiss();
                            break;
                        case "상체 정보":
                            bodyTab_sub = new BodyTab_Sub();
                            bodyTab_sub.setTabitemSelected(1);
                            mPopupWindow.dismiss();
                            break;
                        case "하체 운동":
                            bodyTab_sub = new BodyTab_Sub();
                            bodyTab_sub.setTabitemSelected(2);
                            mPopupWindow.dismiss();
                            break;
                        case "하체 정보":
                            bodyTab_sub = new BodyTab_Sub();
                            bodyTab_sub.setTabitemSelected(3);
                            mPopupWindow.dismiss();
                            break;
                        case "스트레칭":
                            bodyTab_sub = new BodyTab_Sub();
                            bodyTab_sub.setTabitemSelected(4);
                            mPopupWindow.dismiss();
                            break;
                        case "코어 운동":
                            followTab_sub = new FollowTab_Sub();
                            followTab_sub.setTabitemSelected(0);
                            mPopupWindow.dismiss();
                            break;
                        case "유산소운동":
                            followTab_sub = new FollowTab_Sub();
                            followTab_sub.setTabitemSelected(1);
                            mPopupWindow.dismiss();
                            break;
                        case "근력운동":
                            followTab_sub = new FollowTab_Sub();
                            followTab_sub.setTabitemSelected(2);
                            mPopupWindow.dismiss();
                            break;
                        case "스트레칭 따라하기":
                            followTab_sub = new FollowTab_Sub();
                            followTab_sub.setTabitemSelected(3);
                            mPopupWindow.dismiss();
                            break;
                        case "체지방감소":
                            foodTab_sub = new FoodTab_Sub();
                            foodTab_sub.setTabitemSelected(0);
                            mPopupWindow.dismiss();
                            break;
                        case "근력강화":
                            foodTab_sub = new FoodTab_Sub();
                            foodTab_sub.setTabitemSelected(1);
                            mPopupWindow.dismiss();
                            break;
                        case "근육량증대":
                            foodTab_sub = new FoodTab_Sub();
                            foodTab_sub.setTabitemSelected(2);
                            mPopupWindow.dismiss();
                            break;
                        case "몸매관리":
                            foodTab_sub = new FoodTab_Sub();
                            foodTab_sub.setTabitemSelected(3);
                            mPopupWindow.dismiss();
                            break;
                        case "대사증후군":
                            foodTab_sub = new FoodTab_Sub();
                            foodTab_sub.setTabitemSelected(4);
                            mPopupWindow.dismiss();
                            break;
                        case "트레이너":
                            writerTab_sub = new WriterTab_Sub();
                            writerTab_sub.setTabitemSelected(0);
                            mPopupWindow.dismiss();
                            break;
                        case "영양사":
                            writerTab_sub = new WriterTab_Sub();
                            writerTab_sub.setTabitemSelected(1);
                            mPopupWindow.dismiss();
                            break;

                    }
                }

            });
            /**
             * @View anchor : anchor View를 기준으로 바로 아래 왼쪽에 표시.
             * @예외 : 하지만 anchor View가 화면에 가장 하단 View라면 시스템이
             * 자동으로 위쪽으로 표시되게 한다.
             * xoff, yoff : anchor View를 기준으로 PopupWindow가 xoff는 x좌표,
             * yoff는 y좌표 만큼 이동된 위치에 표시되게 한다.
             * @int xoff : -숫자(화면 왼쪽으로 이동), +숫자(화면 오른쪽으로 이동)
             * @int yoff : -숫자(화면 위쪽으로 이동), +숫자(화면 아래쪽으로 이동)
             * achor View 를 덮는 것도 가능.
             * 화면바깥 좌우, 위아래로 이동 가능. (짤린 상태로 표시됨)
             * mPopupWindow.showAsDropDown(btn_Popup, 50, 50);
             */
            mPopupWindow.setAnimationStyle(R.style.menuchange); // 애니메이션 설정(-1:설정, 0:설정안함)

            /**
             * showAtLocation(parent, gravity, x, y)
             * @praent : PopupWindow가 생성될 parent View 지정
             * View v = (View) findViewById(R.id.btn_click)의 형태로 parent 생성
             * @gravity : parent View의 Gravity 속성 지정 Popupwindow 위치에 영향을 줌.
             * @x : PopupWindow를 (-x, +x) 만큼 좌,우 이동된 위치에 생성
             * @y : PopupWindow를 (-y, +y) 만큼 상,하 이동된 위치에 생성
             * mPopupWindow.showAtLocation(popupView, Gravity.NO_GRAVITY, 0, 0);
             * */
            /**
             * update() 메서드를 통해 PopupWindow의 좌우 사이즈, x좌표, y좌표
             * anchor View까지 재설정 해줄수 있습니다.
             * mPopupWindow.update(anchor, xoff, yoff, width, height)(width, height);
             */
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.showAtLocation(popupView, Gravity.END, 0, 0);
            Log.d("popup", "display");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        myActionMenuItem = menu.findItem( R.id.action_search);
        menu_more = menu.findItem(R.id.action_menu);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                //UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                if( ! mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void menu_listSet(String[] values){
        final ArrayList<String> list = new ArrayList<String>();
        //Log.d("menu_listSet list:", ArrayList.(list));
        Log.d("menu_listSet values:", Arrays.toString(values));
        for (int i = 0; i < values.length; ++i) {
                list.add(values[i]);
            Log.d("menu_listSet list:", values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                R.layout.menulistitem, list);
        menu_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //foodTab_sub.changePage(2);
    }

    private void license_listSet(){
        final ArrayList<LicenseItem> list = new ArrayList<LicenseItem>();
        String[] title = {"Square", "Retrofit2"};
        String[] source = {"Copyright 2016 Square, Inc.\n" + "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" + "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" + "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.",
                "Copyright 2013 Square, Inc.\n" + "\n" + "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                        "you may not use this file except in compliance with the License.\n" +
                        " You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0\n"+ "\n" +
                        "Unless required by applicable law or agreed to in writing, software\n" +
                        "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                        "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                        "See the License for the specific language governing permissions and\n" +
                        "limitations under the License."};
        for (int i = 0; i < title.length; ++i) {
            list.add(new LicenseItem(title[i], source[i]));
        }
        final LicenseListAdapter adapter = new LicenseListAdapter(this,
                R.layout.license, list);
        menu_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //foodTab_sub.changePage(2);
    }

    @Override
    public void onClick(View v) {

        body = new String[]{"상체 운동", "상체 정보", "하체 운동", "하체 정보", "스트레칭"};
        follow = new String[]{"코어 운동", "유산소운동", "근력운동", "스트레칭 따라하기"};
        food = new String[]{"체지방감소", "근력강화", "근육량증대", "몸매관리", "대사증후군"};
        trainer = new String[]{"트레이너", "영양사"};
        license = new String[]{};
        Intent home = new Intent(MainActivity.this, FirstActivity.class);
        switch (v.getId()) {
            case R.id.cancel_menuBtn:
                mPopupWindow.dismiss();
                break;
            case R.id.account_menuBtn:
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.body_menuBtn:
                viewPager.setCurrentItem(1, true);
                menu_listSet(body);
                body_menuBtn.setImageResource(R.drawable.menu_body_sel);
                follow_menuBtn.setImageResource(R.drawable.menu_follow);
                food_menuBtn.setImageResource(R.drawable.menu_food_sel);
                writer_menuBtn.setImageResource(R.drawable.menu_writer);
                body_menuBtn.setBackgroundResource(R.color.menubackcolor);
                follow_menuBtn.setBackgroundResource(R.color.nocolor);
                food_menuBtn.setBackgroundResource(R.color.nocolor);
                writer_menuBtn.setBackgroundResource(R.color.nocolor);
                // licenseBtn.setBackgroundResource(R.color.nocolor);
                break;
            case R.id.follow_menuBtn:
                viewPager.setCurrentItem(2, true);
                menu_listSet(follow);
                follow_menuBtn.setImageResource(R.drawable.menu_follow_sel);
                body_menuBtn.setImageResource(R.drawable.menu_body);
                food_menuBtn.setImageResource(R.drawable.menu_food);
                writer_menuBtn.setImageResource(R.drawable.menu_writer);
                follow_menuBtn.setBackgroundResource(R.color.menubackcolor);
                body_menuBtn.setBackgroundResource(R.color.nocolor);
                food_menuBtn.setBackgroundResource(R.color.nocolor);
                writer_menuBtn.setBackgroundResource(R.color.nocolor);
                // licenseBtn.setBackgroundResource(R.color.nocolor);
                //foodTab_sub.changePage(1);
                break;
            case R.id.food_menuBtn:
                viewPager.setCurrentItem(3, true);
                menu_listSet(food);
                food_menuBtn.setImageResource(R.drawable.menu_food_sel);
                body_menuBtn.setImageResource(R.drawable.menu_body);
                follow_menuBtn.setImageResource(R.drawable.menu_follow);
                writer_menuBtn.setImageResource(R.drawable.menu_writer);
                food_menuBtn.setBackgroundResource(R.color.menubackcolor);
                body_menuBtn.setBackgroundResource(R.color.nocolor);
                follow_menuBtn.setBackgroundResource(R.color.nocolor);
                writer_menuBtn.setBackgroundResource(R.color.nocolor);
                // licenseBtn.setBackgroundResource(R.color.nocolor);
                break;
            case R.id.qna_Btn:
                Intent qintent = new Intent(MainActivity.this, QnaActivity.class);
                btn_View.setBackgroundResource(R.color.qnatoolbar);
                startActivity(qintent);
                break;
            case R.id.writer_menuBtn:
                viewPager.setCurrentItem(4, true);
                menu_listSet(trainer);
                writer_menuBtn.setImageResource(R.drawable.menu_writer_sel);
                body_menuBtn.setImageResource(R.drawable.menu_body);
                follow_menuBtn.setImageResource(R.drawable.menu_follow);
                food_menuBtn.setImageResource(R.drawable.menu_food);
                writer_menuBtn.setBackgroundResource(R.color.menubackcolor);
                body_menuBtn.setBackgroundResource(R.color.nocolor);
                follow_menuBtn.setBackgroundResource(R.color.nocolor);
                food_menuBtn.setBackgroundResource(R.color.nocolor);
                //licenseBtn.setBackgroundResource(R.color.nocolor);
                // foodTab_sub.changePage(4);
                break;
            /**
             case R.id.license_Btn:
             license_listSet();
             licenseBtn.setBackgroundResource(R.color.menubackcolor);
             body_menuBtn.setBackgroundResource(R.color.nocolor);
             follow_menuBtn.setBackgroundResource(R.color.nocolor);
             food_menuBtn.setBackgroundResource(R.color.nocolor);
             writer_menuBtn.setBackgroundResource(R.color.nocolor);
             break;*/
            case R.id.menuchecked:
                SharedPreferences pref = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (checkedTextView.isChecked()) {
                    editor.putInt("First", 0);
                } else {
                    editor.putInt("First", 1);
                }
                editor.apply();
                break;
            case R.id.menuBtn_home:
                startActivity(home);
                break;
            case R.id.expand_space:
                startActivity(home);
                break;
            case R.id.tool_space:
                startActivity(home);
                break;
        }
    }

    public void popupshow(MenuItem item) {
        popupDisplay();
        Log.d("MenuItemClick", String.valueOf(item.getItemId()));
    }
}
/** 상체 : 1, 하체 : 2
 * 상관 없음 : 0, 전면 : 1, 후면 : 2
 * 상관 없음 : 0, 골격 : 1, 근육 : 2, 근력 : 3, 근지구력 : 4, 근파워 : 5, 심폐지구력 : 6, 유연성 : 7
 * 상관 없음 : 0, 상완 : 1, 하완 : 2,  어깨 : 3, 목 : 4, 허벅지 : 5, 종아리 : 6, 가슴 : 7, 엉덩이 : 8, 복부 : 9
 */