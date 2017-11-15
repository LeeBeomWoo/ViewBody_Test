package com.example.leebeomwoo.viewbody_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.leebeomwoo.viewbody_final.Adapter.StableArrayAdapter;
import com.example.leebeomwoo.viewbody_final.ItemGroup.Item_follow_fragment;
import com.example.leebeomwoo.viewbody_final.ItemGroup.Item_follow_fragment_21;
import com.example.leebeomwoo.viewbody_final.ItemGroup.TrainerInfoFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by LBW on 2016-06-30.
 */
public class ItemViewActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public static final int REQUEST_CAMERA = 1;
    private static String TAG = "ItemView";
    public static void setAutoOrientationEnabled(Context context, boolean enabled)
    {
        Settings.System.putInt( context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }
    // FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5;
    public String tr_id, item_word, section, video, videoPath;
    int category, page_num;
    public Context context = this;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_item);
        Log.d(TAG, "onCreate");
        Intent intent = getIntent();
        item_word = intent.getStringExtra("itemUrl");
        category = intent.getIntExtra("fragment", 10);
        page_num = intent.getIntExtra("page_num", 0);
        video = intent.getStringExtra("video");
        tr_id = intent.getStringExtra("tr_Id");
        section = intent.getStringExtra("section");
        Log.d(TAG, "item_word : " + item_word + "," + "video : " + video + "," + "tr_id : " + tr_id + "," + "section : " + section );
        // item_word = intent.getStringExtra("item_word");
            // Now later we can lookup the fragment by tag
        if (saveInstanceState == null) {
            if (Build.VERSION.SDK_INT >= 21 ) {
                Item_follow_fragment_21 follow = new Item_follow_fragment_21();
                follow.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, follow, "your_fragment_21").commit();
            } else {
                Item_follow_fragment follow = new Item_follow_fragment();
                follow.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, follow, "your_fragment").commit();
            }
        }else {
            if (Build.VERSION.SDK_INT >= 21 ) {
                Item_follow_fragment_21 follow = (Item_follow_fragment_21) getSupportFragmentManager().findFragmentByTag("your_fragment_21");
            } else {
                Item_follow_fragment follow = (Item_follow_fragment) getSupportFragmentManager().findFragmentByTag("your_fragment");
            }
        }
        if (android.provider.Settings.System.getInt(getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0) != 1){
            setAutoOrientationEnabled(this, true);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d(TAG, "onResumeFragments");
    }

    private void pageSel(int sec){
        Log.d(TAG, "pagesel");
        Bundle fbundle = new Bundle();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (sec){
            case 1:
                TrainerInfoFragment timetreck = new TrainerInfoFragment();
                fbundle.putString("tr_Id", tr_id);
                fbundle.putString("section", section);
                timetreck.setArguments(fbundle);
                transaction.replace(R.id.fragment_container, timetreck);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= 21 ) {
                    Item_follow_fragment_21 follow = new Item_follow_fragment_21();
                    follow.setRetainInstance(true);
                    fbundle.putString("tr_Id", tr_id);
                    fbundle.putString("section", section);
                    fbundle.putInt("page_num", page_num);
                    fbundle.putString("video", video);
                    fbundle.putInt("category", category);
                    follow.setArguments(fbundle);
                    transaction.replace(R.id.fragment_container, follow);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Item_follow_fragment follow = new Item_follow_fragment();
                    follow.setRetainInstance(true);
                    fbundle.putString("tr_Id", tr_id);
                    fbundle.putString("section", section);
                    fbundle.putInt("page_num", page_num);
                    fbundle.putInt("category", category);
                    fbundle.putString("video", video);
                    follow.setArguments(fbundle);
                    transaction.replace(R.id.fragment_container, follow);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
        }
    }
    /**
    @Override
    public void onClick(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.fab :
                animateFAB();
                break;
            case R.id.fab1:
                switch (category) {
                    case 1:
                        TrainerInfoFragment timetreck = new TrainerInfoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("tr_Id", tr_id);
                        bundle.putString("section", section);
                        transaction.replace(R.id.fragment_container, timetreck);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 2:
                        if (Build.VERSION.SDK_INT >= 21 ) {
                            Item_follow_fragment_21 follow = new Item_follow_fragment_21();
                            Bundle fbundle = new Bundle();
                            fbundle.putString("tr_Id", tr_id);
                            fbundle.putString("section", section);
                            fbundle.putInt("page_num", page_num);
                            transaction.replace(R.id.fragment_container, follow);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {
                            Item_follow_fragment follow = new Item_follow_fragment();
                            Bundle fbundle = new Bundle();
                            fbundle.putString("tr_Id", tr_id);
                            fbundle.putString("section", section);
                            fbundle.putInt("page_num", page_num);
                            transaction.replace(R.id.fragment_container, follow);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        break;
                    case 3:
                        if (Build.VERSION.SDK_INT >= 21) {
                            // Stop recording and finish
                            if (pausing) {
                                Follow_fragment.onResume();
                                pausing = false;
                            } else {
                                Follow_fragment.onPause();
                                pausing = true;
                            }
                        } else {
                            if (pausing) {
                                finalFollow_fragment.onResume();
                                pausing = false;
                            } else {
                                finalFollow_fragment.onPause();
                                pausing = true;
                            }
                            finalFollow_fragment.mediaRecorder.start();
                        }
                        fab1.setImageResource(R.drawable.playbutton);
                        fab.startAnimation(rotate_backward);
                        fab1.startAnimation(fab_close);
                        fab2.startAnimation(fab_close);
                        fab3.startAnimation(fab_close);
                        fab4.startAnimation(fab_close);
                        fab5.startAnimation(fab_close);
                        fab.setClickable(false);
                        fab1.setClickable(false);
                        fab2.setClickable(false);
                        fab3.setClickable(false);
                        fab4.setClickable(false);
                        fab5.setClickable(false);
                        isFabOpen = false;
                        break;
                }
                break;
            case R.id.fab2 :
                switch (category) {
                    case 1:
                        Intent intent_1 = new Intent(ItemViewActivity.this, MainActivity.class);
                        q = 2;
                        //intent_1.putExtra("item_word", item_word);
                        intent_1.putExtra("fragment", q);
                        startActivity(intent_1);
                        break;
                    case 2:
                        Intent intent_2 = new Intent(ItemViewActivity.this, MainActivity.class);
                        q = 2;
                        //intent_2.putExtra("item_word", item_word);
                        intent_2.putExtra("fragment", q);
                        startActivity(intent_2);
                        break;
                    case 3:
                        if (Build.VERSION.SDK_INT >= 21) {
                            // Stop recording and finish
                            if (Follow_fragment.mIsRecordingVideo) {
                                Follow_fragment.stopRecordingVideo();
                            } else {
                                Follow_fragment.startRecordingVideo();
                            }
                        } else {
                            if (recording){
                                finalFollow_fragment.mediaRecorder.stop();
                                finalFollow_fragment.mediaRecorder.release();
                                finalFollow_fragment.mediaRecorder.reset();
                                recording = false;
                            } else{
                                finalFollow_fragment.mediaRecorder.start();
                                recording = true;
                                fab1.setImageResource(R.drawable.record);
                            }
                            finalFollow_fragment.mediaRecorder.start();
                        }
                        fab1.setImageResource(R.drawable.record);
                }
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab4.startAnimation(fab_close);
                fab5.startAnimation(fab_close);
                fab.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                fab4.setClickable(false);
                fab5.setClickable(false);
                isFabOpen = false;
                break;
            case R.id.fab3 :
                switch (category) {
                    case 1:
                        Intent intent_1 = new Intent(ItemViewActivity.this, MainActivity.class);
                        q = 3;
                        //intent_1.putExtra("item_word", item_word);
                        intent_1.putExtra("fragment", q);
                        startActivity(intent_1);
                        break;
                    case 2:
                        Intent intent_2 = new Intent(ItemViewActivity.this, MainActivity.class);
                        q = 3;
                        //intent_2.putExtra("item_word", item_word);
                        intent_2.putExtra("fragment", q);
                        startActivity(intent_2);
                        break;
                    case 3:
                        if (Build.VERSION.SDK_INT >= 21) {
                            Follow_fragment.switchCamera();
                        } else {
                            setCameraDisplayOrientation(ItemViewActivity.this, currentCameraId, finalFollow_fragment.mCamera);
                            Thread t_kit = new Thread() {
                                public void run() {
                                    //myViewer is the SurfaceView object which uses
                                    //the camera
                                    finalFollow_fragment.flipit();
                                }
                            };
                            t_kit.start();
                        }
                        break;
                }
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab4.startAnimation(fab_close);
                fab5.startAnimation(fab_close);
                fab.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                fab4.setClickable(false);
                fab5.setClickable(false);
                isFabOpen = false;
                break;
            case R.id.fab4 :
                timetreck timetreck = new timetreck();
                FragmentManager fragmentManager_1 = getFragmentManager();
                FragmentTransaction fragmentTransaction_1 = fragmentManager_1.beginTransaction();
                fragmentTransaction_1.replace(R.id.fragment, timetreck).commit();
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab4.startAnimation(fab_close);
                fab5.startAnimation(fab_close);
                fab.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                fab4.setClickable(false);
                fab5.setClickable(false);
                isFabOpen = false;
                break;
            case R.id.fab5 :
                TrainerInfoFragment fragment3 = new TrainerInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tr_id", tr_id);
                fragment3.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, fragment3).commit();
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab4.startAnimation(fab_close);
                fab5.startAnimation(fab_close);
                fab.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                fab4.setClickable(false);
                fab5.setClickable(false);
                isFabOpen = false;
                break;
        }
    }
    **/
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    @Override
    public void onBackPressed() {
        // Otherwise defer to system default behavior.
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    /**
    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab5.startAnimation(fab_close);
            fab.setClickable(true);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            fab5.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab5.startAnimation(fab_open);
            fab.setClickable(true);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            fab5.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }

    public void fabset(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab5 = (FloatingActionButton) findViewById(R.id.fab5);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.iteammainfabclose);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.iteammainfabopen);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);
    }**/
}
