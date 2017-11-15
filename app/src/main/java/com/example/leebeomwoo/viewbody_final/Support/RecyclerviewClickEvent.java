package com.example.leebeomwoo.viewbody_final.Support;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.FloatMath;
import android.util.Log;
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

import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.squareup.picasso.Picasso;

import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;

/**
 * Created by LeeBeomWoo on 2017-06-21.
 */

public class RecyclerviewClickEvent implements View.OnTouchListener {
    private static final String TAG = "Popup";
    private Context context;
    private Drawable drawable ;
    private ImageView imgViewIcon;
    private ListDummyItem ldItem;
    boolean end;

    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;

    private final static String FURL = "<html><body><iframe width=\"1080\" height=\"720\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    @SuppressLint("ClickableViewAccessibility")
    public void Click(ListDummyItem ld_Item, Context context){
        this.context = context;
        ldItem = ld_Item;
        //read your lovely variable
        Log.d(TAG, ldItem.getLd_ImageUrl());
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_detail);
        ScrollView scroll = new ScrollView(context);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ScaleRelativeLayout main = dialog.findViewById(R.id.detail_layout);
        TextView txtViewTitle = (TextView) dialog.findViewById(R.id.detile_Title);
        scroll.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        CardView card = dialog.findViewById(R.id.cardView);
        imgViewIcon = (ImageView) dialog.findViewById(R.id.detile_Image);
        TextView video_title_1 = (TextView) dialog.findViewById(R.id.video_title_1);
        TextView video_title_2 = (TextView) dialog.findViewById(R.id.video_title_2);
        TextView video_title_3 = (TextView) dialog.findViewById(R.id.video_title_3);
        WebView imgViewFace = (WebView) dialog.findViewById(R.id.detile_face);
        WebView videoView_1 = (WebView) dialog.findViewById(R.id.video_view_1);
        WebView videoView_2 = (WebView) dialog.findViewById(R.id.video_view_2);
        TextView txtViewId = (TextView) dialog.findViewById(R.id.detile_Id);
        Button button = (Button) dialog.findViewById(R.id.like_btn);
        WebView videoView_3 = (WebView) dialog.findViewById(R.id.video_view_3);
        ImageView titleimage = (ImageView) dialog.findViewById(R.id.itemtitle_image);
        ViewGroup viewGroup = ((ViewGroup)card.getParent());
        viewGroup.removeView(card);
        scroll.addView(card);
        viewGroup.addView(scroll);
        titleimage.setImageDrawable(titlecategory(ldItem.getLd_Category()));
        String result = ldItem.getLd_ImageUrl().replaceAll("\\/","/");
        Picasso.with(context).load(ConAdapter.SERVER_URL + result).into(imgViewIcon);
        txtViewTitle.setText(ldItem.getLd_Title());
        txtViewId.setText(ldItem.getLd_Id());
        imgViewIcon.setLayoutParams(new ScaleRelativeLayout.LayoutParams(ScaleRelativeLayout.LayoutParams.MATCH_PARENT,
                ScaleRelativeLayout.LayoutParams.WRAP_CONTENT));
        imgViewIcon.setFocusable(true);
        imgViewIcon.setOnTouchListener(this);
        imgViewIcon.setScaleType(ImageView.ScaleType.MATRIX);
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
                    webviewSet(videoView_2, animalsArray[5]);
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
    private Drawable titlecategory(int i){
        switch (i){
            case 31://근육
                drawable = context.getResources().getDrawable(R.drawable.muscleup_body);
                break;
            case 32://골격
                drawable = context.getResources().getDrawable(R.drawable.bone);
                break;
            case 33://근지구력
                drawable = context.getResources().getDrawable(R.drawable.muscletime);
                break;
            case 34://근파워
                drawable = context.getResources().getDrawable(R.drawable.musclepower);
                break;
            case 35://머슬업
                drawable = context.getResources().getDrawable(R.drawable.muscleup_body);
                break;
            case 42://허리
                drawable = context.getResources().getDrawable(R.drawable.spine);
                break;
            case 11://전면상완
                drawable = context.getResources().getDrawable(R.drawable.front_upper_arm);
                break;
            case 21://후면상완
                drawable = context.getResources().getDrawable(R.drawable.back_upper_arm);
                break;
            case 12://전면하완
                drawable = context.getResources().getDrawable(R.drawable.front_lower_arm);
                break;
            case 22://후면하완
                drawable = context.getResources().getDrawable(R.drawable.back_lower_arm);
                break;
            case 37://복부
                drawable = context.getResources().getDrawable(R.drawable.stormach);
                break;
            case 38://가슴
                drawable = context.getResources().getDrawable(R.drawable.burst);
                break;
            case 13://전면어깨
                drawable = context.getResources().getDrawable(R.drawable.front_shoulder);
                break;
            case 23://후면어깨
                drawable = context.getResources().getDrawable(R.drawable.back_shoulder);
                break;
            case 14://전면목
                drawable = context.getResources().getDrawable(R.drawable.front_neck);
                break;
            case 24://후면목
                drawable = context.getResources().getDrawable(R.drawable.back_neck);
                break;
            case 15://전면허벅지
                drawable = context.getResources().getDrawable(R.drawable.front_upper_leg);
                break;
            case 25://후면허벅지
                drawable = context.getResources().getDrawable(R.drawable.back_upper_leg);
                break;
            case 16://전면종아리
                drawable = context.getResources().getDrawable(R.drawable.front_lower_leg);
                break;
            case 26://후면종아리
                drawable = context.getResources().getDrawable(R.drawable.back_lower_leg);
                break;
            case 36://엉덩이
                drawable = context.getResources().getDrawable(R.drawable.hip);
                break;
            case 39://상체
                drawable = context.getResources().getDrawable(R.drawable.upper);
                break;
            case 40://하체
                drawable = context.getResources().getDrawable(R.drawable.lower);
                break;
            case 17://전면몸통
                drawable = context.getResources().getDrawable(R.drawable.stormach);
                break;
            case 27://후면몸통
                drawable = context.getResources().getDrawable(R.drawable.back_body);
                break;
            case 41://심폐지구력
                drawable = context.getResources().getDrawable(R.drawable.cardiopulmonary_endurance);
                break;
            case 19://미정
                drawable = context.getResources().getDrawable(R.drawable.foodlogo);
                break;
        }
        return drawable;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        dumpEvent(event);

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
                if (oldDist > 10f) {
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
                    // ...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
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

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
