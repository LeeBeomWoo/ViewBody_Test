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

import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;

/**
 * Created by LeeBeomWoo on 2017-06-21.
 */

public class RecyclerviewClickEvent implements View.OnTouchListener {
    private static final String TAG = "Popup";
    private Context context;
    private int drawable ;
    private ImageView imgViewIcon, titleimage;
    private ListDummyItem ldItem;
    ScaleRelativeLayout main;
    private CardView card;
    boolean expanded = false;

    private int color, rusult, result, fin;
    private float scale_img = 1;
    // These matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float dx; // postTranslate X distance
    private float dy; // postTranslate Y distance
    private float[] matrixValues = new float[9];
    float matrixX = 0; // X coordinate of matrix inside the ImageView
    float matrixY = 0; // Y coordinate of matrix inside the ImageView
    float width = 0; // width of drawable
    float height = 0; // height of drawable
    private GestureDetectorCompat mDetector;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    private long thisTime = 0;
    private long prevTime = 0;
    private boolean firstTap = true;
    protected static final long DOUBLE_CLICK_MAX_DELAY = 1000L;

    private final static String FURL = "<html><body><iframe width=\"1080\" height=\"720\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    @SuppressLint("ClickableViewAccessibility")
    public void Click(ListDummyItem ld_Item, Context context){

        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        this.context = context;
        ldItem = ld_Item;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
        }
        //read your lovely variable
        Log.d(TAG, ldItem.getLd_ImageUrl());
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_detail);
        ScrollView scroll = new ScrollView(context);
        dialog.getWindow().setLayout(metrics.widthPixels, metrics.heightPixels);
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        main = dialog.findViewById(R.id.detail_layout);
        TextView txtViewTitle = dialog.findViewById(R.id.detile_Title);
        scroll.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        card = dialog.findViewById(R.id.cardView);
        ViewGroup viewGroup = ((ViewGroup)card.getParent());
        viewGroup.removeView(card);
        scroll.addView(card);
        viewGroup.addView(scroll);
        imgViewIcon = dialog.findViewById(R.id.detile_Image);
        TextView video_title_1 = dialog.findViewById(R.id.video_title_1);
        TextView video_title_2 = dialog.findViewById(R.id.video_title_2);
        TextView video_title_3 = dialog.findViewById(R.id.video_title_3);
        WebView imgViewFace = dialog.findViewById(R.id.detile_face);
        WebView videoView_1 = dialog.findViewById(R.id.video_view_1);
        WebView videoView_2 = dialog.findViewById(R.id.video_view_2);
        TextView txtViewId = dialog.findViewById(R.id.detile_Id);
        Button button = dialog.findViewById(R.id.like_btn);
        WebView videoView_3 = dialog.findViewById(R.id.video_view_3);
        titleimage = dialog.findViewById(R.id.itemtitle_image);
        mDetector = new GestureDetectorCompat(context, new MyGestureListener());
        Picasso.with(context).load(titlecategory(ldItem.getLd_Category())).fit().into(titleimage);
        //titleimage.setImageDrawable(titlecategory(ldItem.getLd_Category()));
        String result = ldItem.getLd_ImageUrl().replaceAll("\\/","/");
        Picasso.with(context).load(ConAdapter.SERVER_URL + result).resize(width, height).into(imgViewIcon);
        txtViewTitle.setText(ldItem.getLd_Title());
        txtViewId.setText(ldItem.getLd_Id());
        imgViewIcon.setLayoutParams(new ScaleRelativeLayout.LayoutParams(ScaleRelativeLayout.LayoutParams.MATCH_PARENT,
                ScaleRelativeLayout.LayoutParams.WRAP_CONTENT));
        imgViewIcon.setFocusable(true);
        imgViewIcon.setOnTouchListener(this);
        imgViewIcon.setScaleType(ImageView.ScaleType.MATRIX);
        ScaleRelativeLayout.LayoutParams layout = new ScaleRelativeLayout.LayoutParams(ScaleRelativeLayout.LayoutParams.WRAP_CONTENT, ScaleRelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.addRule(ScaleRelativeLayout.BELOW, R.id.title_layout);
        imgViewIcon.setLayoutParams(layout);
        webviewSet(imgViewFace, null);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        dumpEvent(event);
        mDetector.onTouchEvent(event);
        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG");
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 2) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.getValues(matrixValues);
                    matrixX = matrixValues[2];
                    matrixY = matrixValues[5];
                    width = matrixValues[0] * ((view).getDrawable()
                            .getIntrinsicWidth());
                    height = matrixValues[4] * ((view).getDrawable()
                            .getIntrinsicHeight());

                    dx = event.getX() - start.x;
                    dy = event.getY() - start.y;

                    Log.d("bound", "dx=" + String.valueOf(dx) + "matrixX=" + String.valueOf(matrixX) + "width=" + String.valueOf(width));
                    //if image will go outside left bound

                    if (matrixX + dx < 0){
                        Log.d("left bound", String.valueOf(matrixX + dx));
                        dx = -matrixX;
                    }
                    //if image will go outside right bound
                    if(matrixX + dx + width > view.getWidth()){
                        Log.d("right bound", String.valueOf(matrixX + dx+ width)+ "*" + String.valueOf(view.getWidth()));
                        dx = view.getWidth() - matrixX - width;
                    }
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 2) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        ScaleRelativeLayout.LayoutParams layout = new ScaleRelativeLayout.LayoutParams(ScaleRelativeLayout.LayoutParams.WRAP_CONTENT, ScaleRelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.addRule(ScaleRelativeLayout.BELOW, R.id.title_layout);
        view.setLayoutParams(layout);
        view.setImageMatrix(matrix);
        return true;
    }
    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                                float distanceY) {
            Log.d(DEBUG_TAG, "onScroll-1: " + event1.toString() + "onScroll-2: " + event2.toString());
            return true;
        }

        @Override
        public void onShowPress(MotionEvent event) {
            Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
            return true;
        }
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
