package com.example.leebeomwoo.viewbody_final.Support;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leebeomwoo.viewbody_final.GestureImageView.GestureImageView;
import com.example.leebeomwoo.viewbody_final.GestureImageView.GestureImageViewListener;
import com.example.leebeomwoo.viewbody_final.GestureImageView.GestureImageViewTouchListener;
import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import cn.gavinliu.android.lib.scale.ScaleLinearLayout;
import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;

/**
 * Created by LeeBeomWoo on 2017-06-21.
 */

public class RecyclerviewClickEvent {
    private static final String TAG = "Popup";
    private Context pcontext;
    private GestureImageView imgViewIcon;
    private ListDummyItem ldItem;

    private int colo, rusult, result, fin;
    // Remember some things for zooming
    private final static String FURL = "<html><body><iframe width=\"1080\" height=\"720\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    @SuppressLint("ClickableViewAccessibility")
    public void Click(ListDummyItem ld_Item, Context context, int color){
        this.colo = color;
        this.pcontext = context;
        int height = pcontext.getResources().getDisplayMetrics().heightPixels;
        int width = pcontext.getResources().getDisplayMetrics().widthPixels;
        ldItem = ld_Item;
        WindowManager wm = (WindowManager) pcontext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
        }
        //read your lovely variable
        Log.d(TAG, ldItem.getLd_ImageUrl());
        Dialog dialog = new Dialog(pcontext);
        dialog.setContentView(R.layout.fragment_detail);
        dialog.getWindow().setLayout(metrics.widthPixels, metrics.heightPixels);
        imgViewIcon = new GestureImageView(pcontext);

        TextView txtViewTitle = dialog.findViewById(R.id.detile_Title);
        CardView card = dialog.findViewById(R.id.cardView);
        imgViewIcon.setId(R.id.detile_Image);
        ScaleLinearLayout main = dialog.findViewById(R.id.detail_layout);
        TextView video_title_1 = dialog.findViewById(R.id.video_title_1);
        TextView video_title_2 = dialog.findViewById(R.id.video_title_2);
        TextView video_title_3 = dialog.findViewById(R.id.video_title_3);
        WebView imgViewFace = dialog.findViewById(R.id.detile_face);
        WebView videoView_1 = dialog.findViewById(R.id.video_view_1);
        WebView videoView_2 = dialog.findViewById(R.id.video_view_2);
        TextView txtViewId = dialog.findViewById(R.id.detile_Id);
        Button button = dialog.findViewById(R.id.like_btn);
        WebView videoView_3 = dialog.findViewById(R.id.video_view_3);
        ImageView titleimage = dialog.findViewById(R.id.itemtitle_image);
        main.addView(imgViewIcon,1);
        titleimage.setBackgroundResource(colo);
        ScaleLinearLayout.LayoutParams layout = new ScaleLinearLayout.LayoutParams(ScaleLinearLayout.LayoutParams.MATCH_PARENT, ScaleLinearLayout.LayoutParams.MATCH_PARENT);
        //layout.addRule(ScaleRelativeLayout.BELOW, R.id.title_layout);
        imgViewIcon.setLayoutParams(layout);
        switch (colo){
            case R.drawable.body_title_back:
                txtViewTitle.setBackgroundResource(R.drawable.body_title_text_back);
                break;
            case R.drawable.follow_title_back:
                txtViewTitle.setBackgroundResource(R.drawable.follow_title_text_back);
                break;
            case R.drawable.food_title_back:
                txtViewTitle.setBackgroundResource(R.drawable.food_title_text_back);
                break;
            case R.drawable.new_title_back:
                txtViewTitle.setBackgroundResource(R.drawable.new_title_text_back);
                break;
            case R.drawable.writer_title_back:
                txtViewTitle.setBackgroundResource(R.drawable.writer_title_text_back);
                break;
        }
        if(ldItem.getLd_Video() != null) {
            String[] animalsArray = ldItem.getLd_Video().split(",");
            for(int i = 0; i < animalsArray.length; i++) {
                Log.d(TAG + "animalsArray_" + i, animalsArray[i]);
            }
            switch (animalsArray.length){
                case 2:
                    video_title_1.setVisibility(View.VISIBLE);
                    video_title_1.setText(animalsArray[0]);
                    webviewSet(videoView_1, animalsArray[1]);
                    break;
                case 4:
                    video_title_1.setVisibility(View.VISIBLE);
                    video_title_1.setText(animalsArray[0]);
                    webviewSet(videoView_1, animalsArray[1]);
                    webviewSet(videoView_2, animalsArray[3]);
                    break;
                case 6:
                    video_title_1.setVisibility(View.VISIBLE);
                    video_title_1.setText(animalsArray[0]);

                    video_title_2.setVisibility(View.VISIBLE);
                    video_title_2.setText(animalsArray[2]);

                    video_title_3.setVisibility(View.VISIBLE);
                    video_title_3.setText(animalsArray[4]);
                    webviewSet(videoView_1, animalsArray[1]);
                    webviewSet(videoView_2, animalsArray[3]);
                    webviewSet(videoView_3, animalsArray[5]);
                    break;
            }

        }
        Picasso.with(context).load(titlecategory(ldItem.getLd_Category())).fit().into(titleimage);
        String result = ldItem.getLd_ImageUrl().replaceAll("\\/","/");
        Picasso.with(context).load(ConAdapter.SERVER_URL + result).resize(width, height).into(imgViewIcon);
        txtViewTitle.setText(ldItem.getLd_Title());
        txtViewId.setText(ldItem.getLd_Id());
        imgViewIcon.setFocusable(true);
        imgViewIcon.setGestureImageViewListener(new GestureImageViewListener() {
            @Override
            public void onTouch(float x, float y) {
                imgViewIcon.setStartingScale(imgViewIcon.getScale());
            }

            @Override
            public void onScale(float scale) {
                imgViewIcon.setStartingScale(scale);
            }

            @Override
            public void onPosition(float x, float y) {
                imgViewIcon.setPosition(x,y);
            }
        });
        imgViewIcon.setMaxScale(3);
        imgViewIcon.setMinScale(1);
        webviewSet(imgViewFace, null);
        dialog.show();
    }
    private void webviewSet(WebView webview, String source){
        WebSettings settings = webview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webview.setVisibility(View.VISIBLE);
        webview.setVisibility(View.VISIBLE);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (Build.VERSION.SDK_INT >= 19) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if(source != null) {
            String change = source.replace("https://youtu.be", CHANGE);
            String URL3 = FURL + change + BURL;
            webview.loadData(URL3, "text/html", "utf-8");
        }
    }
    private int titlecategory(int i){
        String s =String.valueOf(i);
        Log.d("카테고리 분류표", s);
        String[] sa = s.split("");
        Log.d("카테고리0", sa[1]);
        Log.d("카테고리1", sa[2]);
        Log.d("카테고리2", sa[3]);
        Log.d("카테고리3", sa[4]);
        if(Objects.equals(sa[1], "1")){//상체
            if(Objects.equals(sa[2], "0")){//전후면 상관없음
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upper;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "1")){ // 전면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upper;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "2")) { // 후면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upper;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result =rusult;
                        Log.d("drawble rusult", String.valueOf(rusult));
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
                Log.d("drawble rusult", String.valueOf(rusult));
                Log.d("drawble result", String.valueOf(result));
                Log.d("drawble fin",String.valueOf(fin));
            }
        }else {//하체
            if(Objects.equals(sa[2], "0")){//전후면 상관없음
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.lower;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "1")){ // 전면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.lower;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "2")){ // 후면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upbar;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result = rusult;
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
            }
        }
        return fin;
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (Math.abs(x) + Math.abs(y));
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
