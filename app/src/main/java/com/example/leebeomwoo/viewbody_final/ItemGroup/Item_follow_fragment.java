package com.example.leebeomwoo.viewbody_final.ItemGroup;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;


import com.example.leebeomwoo.viewbody_final.CameraUse.AutoFitTextureView;
import com.example.leebeomwoo.viewbody_final.CameraUse.CameraHelper;
import com.example.leebeomwoo.viewbody_final.ItemViewActivity;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.VideoViewCustom;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import cn.gavinliu.android.lib.scale.ScaleFrameLayout;
import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Created by LeeBeomWoo on 2017-02-13.
 */

public class Item_follow_fragment extends Fragment implements Camera.PreviewCallback {
    public Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private File mOutputFile;
    public VideoView videoView;
    private boolean isRecording = false;
    private static final String TAG = "Item_follow_fragment";
    private final static String FURL = "<html><body><iframe width=\"1280\" height=\"720\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    ScaleRelativeLayout bTnLayout;
    Boolean record_plag = false; // true = 녹화중, false = 정지
    Boolean play_plag = false; //true = 재생, false = 정지
    Boolean play_record = true; //true = 촬영, false = 재생
    ImageButton play, record, load, camerachange, play_recordBtn;
    public WebView webView;
    private final int SELECT_MOVIE = 2;
    public MediaRecorder mediaRecorder;
    public SurfaceHolder surfaceHolder;
    int page_num, witch;
    public static AutoFitTextureView textureView;
    public String tr_id, imageUrl, tr_password, URL, section, change, temp, videoString;
    public Uri videopath;
    public int videoPosition;
    ScaleFrameLayout cameraLayout;
    ScaleRelativeLayout.LayoutParams LandButton, LandCamera, LandWebView, playlayout, recordlayout, switchlayout, loadlayout, play_recordlayout;
    View view;
    SeekBar seekBar;
    ItemViewActivity activity = (ItemViewActivity)getActivity();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            tr_id = getArguments().getString("tr_Id");
            imageUrl = getArguments().getString("itemUrl");
            page_num = getArguments().getInt("page_num");
            temp = getArguments().getString("video");
            Log.d(TAG, "imageUrl : " + "temp : " + temp + "," + "tr_id : " + tr_id + "," + "section : " + section );
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    private void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = getActivity();
        if (null == textureView || null == activity) {
            return;
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        RectF deviceRect = new RectF(0, 0, width, height);
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        Log.d("viewRect :", String.valueOf(viewWidth) + "*" + String.valueOf(viewHeight));
        float centerX = deviceRect.centerX();
        float centerY = deviceRect.centerY();
        Log.d("center :", String.valueOf(centerX) + "*" + String.valueOf(centerY));
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            Log.d("beforecenter :", String.valueOf(deviceRect.centerX()) + "*" + String.valueOf(deviceRect.centerY()));
            // deviceRect.offset(centerX - deviceRect.centerX(), centerY - deviceRect.centerY());
            Log.d("aftercenter :", String.valueOf(deviceRect.centerX()) + "*" + String.valueOf(deviceRect.centerX()));
            matrix.setRectToRect(viewRect, deviceRect, Matrix.ScaleToFit.CENTER);
            float scale = Math.max(
                    (float) viewHeight / height,
                    (float) viewWidth / width);
            Log.d("scale :", String.valueOf(scale));
            matrix.postScale(scale, scale*2 , deviceRect.centerX(), deviceRect.centerY());
            Log.d("postScale :", String.valueOf(scale*2) + ":" + String.valueOf(centerX) + ":" + String.valueOf(centerY));
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        textureView.setTransform(matrix);
        Log.d("mTextureView :", String.valueOf(textureView.getWidth()) + "*" + String.valueOf(textureView.getHeight()));
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int rotation = display.getRotation();
        switch (rotation){
            case Surface.ROTATION_0: // 원래 모양
                PortrainSet();
                if (textureView != null && textureView.isAvailable()) {
                    configureTransform(textureView.getWidth(), textureView.getHeight());
                }
                Log.d("ROTATION_0 Device", String.valueOf(width) + " x " + String.valueOf(height));
                break;
            case Surface.ROTATION_90: // 왼쪽이 위
                Log.d("ROTATION_90 Device", String.valueOf(width) + " x " + String.valueOf(height));
                LandSet();
                seekBar.setVisibility(View.VISIBLE);
                if (textureView != null && textureView.isAvailable()) {
                    configureTransform(textureView.getHeight(), textureView.getWidth());
                }
                break;

            case Surface.ROTATION_180: // 뒤집은 모양
                Log.d("ROTATION_180 Device", String.valueOf(width) + " x " + String.valueOf(height));
                PortrainSet();
                if (textureView != null && textureView.isAvailable()) {
                    configureTransform(textureView.getWidth(), textureView.getHeight());
                }
                break;
            case Surface.ROTATION_270: // 오른쪽이 위
                Log.d("ROTATION_270 Device", String.valueOf(width) + " x " + String.valueOf(height));
                LandSet();
                seekBar.setVisibility(View.VISIBLE);
                if (textureView != null && textureView.isAvailable()) {
                    configureTransform(textureView.getHeight(), textureView.getWidth());
                }
                break;
        }
    }
    private void LandSet(){
        LandWebView = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LandButton = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.btnlayoutSiz_item), ViewGroup.LayoutParams.MATCH_PARENT);
        LandCamera = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ScaleRelativeLayout.LayoutParams cameraView = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        playlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        switchlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        play_recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        loadlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        ScaleRelativeLayout.LayoutParams seek = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_LEFT);
        bTnLayout.setLayoutParams(LandButton);
        cameraView.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        cameraView.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        cameraView.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        textureView.setLayoutParams(cameraView);
        playlayout.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        playlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        play.setLayoutParams(playlayout);
        recordlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        recordlayout.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        record.setLayoutParams(recordlayout);
        loadlayout.addRule(ScaleRelativeLayout.BELOW, R.id.play_Btn);
        loadlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        load.setLayoutParams(loadlayout);
        play_recordlayout.addRule(ScaleRelativeLayout.CENTER_VERTICAL);
        play_recordlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        play_recordBtn.setLayoutParams(play_recordlayout);
        play_recordBtn.setVisibility(View.VISIBLE);
        switchlayout.addRule(ScaleRelativeLayout.ABOVE, R.id.record_Btn);
        switchlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        camerachange.setLayoutParams(switchlayout);
        LandCamera.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        cameraLayout.setLayoutParams(LandCamera);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        LandWebView.addRule(ScaleRelativeLayout.BELOW, R.id.alpha_control);
        LandWebView.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        webView.setLayoutParams(LandWebView);
        seek.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        seek.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        seekBar.setLayoutParams(seek);
        seekBar.setProgress(50);
        seekBar.bringToFront();
        webView.setAlpha((float)0.5);
    }
    private void PortrainSet(){
        LandWebView = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.videoviewSiz_item));
        LandButton = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.btnlayoutSiz_item));
        LandCamera = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.videoviewSiz_item));
        playlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        switchlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        play_recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        loadlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        ScaleRelativeLayout.LayoutParams cameraView = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_LEFT);
        bTnLayout.setLayoutParams(LandButton);
        playlayout.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        playlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        play.setLayoutParams(playlayout);
        recordlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        recordlayout.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        record.setLayoutParams(recordlayout);
        loadlayout.addRule(ScaleRelativeLayout.END_OF, R.id.play_Btn);
        loadlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        load.setLayoutParams(loadlayout);
        play_recordlayout.addRule(ScaleRelativeLayout.CENTER_HORIZONTAL);
        play_recordlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        play_recordBtn.setLayoutParams(play_recordlayout);
        play_recordBtn.setVisibility(View.GONE);
        switchlayout.addRule(ScaleRelativeLayout.START_OF, R.id.record_Btn);
        switchlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        camerachange.setLayoutParams(switchlayout);
        textureView.setLayoutParams(cameraView);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        webView.setLayoutParams(LandWebView);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        LandCamera.addRule(ScaleRelativeLayout.BELOW, R.id.button_layout);
        LandCamera.addRule(ScaleRelativeLayout.ABOVE, R.id.web_movie);
        cameraLayout.setLayoutParams(LandCamera);
        seekBar.setVisibility(View.GONE);
        webView.setAlpha((float)1);
    }
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_follow_portrain_itemview, container, false);
            viewSet();
            final WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            double d = 0.5;
            float f = (float)d;
            webView.setAlpha(f);
            change = temp.replace("https://youtu.be", CHANGE);
            URL = FURL + change + BURL;
            Log.d(TAG, URL);
        webView.loadData(URL, "text/html", "charset=utf-8");
            seekBar.setOnSeekBarChangeListener(alphaChangListener);
        mCamera.startPreview();

        change = temp.replace("https://youtu.be", CHANGE);
        URL = FURL + change + BURL;

            webView.loadData(URL, "text/html", "charset=utf-8");
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(videoPosition > 0) {
                    videoView.seekTo(videoPosition);
                    videoView.start();
                } else {
                    videoView.seekTo(100);
                }
            }
        });
        if(videoString != null){
            videoView.setVisibility(View.VISIBLE);
            textureView.setVisibility(View.GONE);
            videoView.start();
        }
        if(getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            play_recordBtn.setVisibility(View.GONE);
            textureView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.VISIBLE);
            play_recordBtn.setClickable(false);
            webView.setAlpha((float)0.9);
        } else {
            webView.setAlpha((float)0.5);
        }
        seekBar.setOnSeekBarChangeListener(alphaChangListener);
        return view;
    }

    private void viewSet(){
        textureView = (AutoFitTextureView) view.findViewById(R.id.AutoView);
        webView = (WebView) view.findViewById(R.id.web_movie);
        record = (ImageButton) view.findViewById(R.id.record_Btn);
        play = (ImageButton) view.findViewById(R.id.play_Btn);
        load = (ImageButton) view.findViewById(R.id.load_Btn);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        play_recordBtn = (ImageButton) view.findViewById(R.id.play_record);
        camerachange = (ImageButton) view.findViewById(R.id.viewChange_Btn);
        seekBar = (SeekBar) view.findViewById(R.id.alpha_control);
        bTnLayout = (ScaleRelativeLayout) view.findViewById(R.id.button_layout);
        cameraLayout = (ScaleFrameLayout) view.findViewById(R.id.video_layout);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textureView.getVisibility() == View.GONE){
                    textureView.setVisibility(View.VISIBLE);
                }
                if(textureView != null){
                    if(record_plag){
                        releaseMediaRecorder();
                        record.setBackgroundResource(R.drawable.record);
                    } else{
                        mMediaRecorder.start();
                        videoView.setVisibility(View.GONE);
                        cameraLayout.setVisibility(View.VISIBLE);
                        record.setBackgroundResource(R.drawable.stop);
                    }
                }
            }
        });
        camerachange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipit();
            }
        });
        play_recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play_record){
                    if(record_plag){
                        mMediaRecorder.stop();
                    }
                    mCamera.release();
                    textureView.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                } else {
                    if(videoView.isPlaying()){
                        videoView.stopPlayback();
                    }
                    mCamera.startPreview();
                    textureView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);}
                    mCamera.startPreview();
                }
            });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "load click");
                videoString = null;
                videopath = null;
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), SELECT_MOVIE);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    play.setImageResource(R.drawable.play);
                }else {
                    videoView.start();
                    play.setImageResource(R.drawable.pause);
                    textureView.setVisibility(View.GONE);
                    cameraLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        camerachange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flipit();
            }
        });
    }

    private void ButtonImageSetUp(){
        if(videoView.isPlaying()){
            play.setImageResource(R.drawable.pause);
        }else{
            play.setImageResource(R.drawable.play);
        }
        if(record_plag){
            record.setImageResource(R.drawable.stop);
        }else {
            record.setImageResource(R.drawable.record);
        }
    }

    private TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                              int width, int height) {        }


        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            mCamera.release();
        }

    };

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        mCamera.startPreview();
        ButtonImageSetUp();
    }

    private void videoviewSetup(Uri path){
        videoView.setVideoURI(path);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //"this" is a SurfaceView which implements SurfaceHolder.Callback,
        //as found in the code examples
        if (textureView.isAvailable()) {
            prepareVideoRecorder();
            new MediaPrepareTask().execute(null, null, null);
        } else {
            textureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
        webView.resumeTimers();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode", String.valueOf(requestCode));
        Log.d("resultCode", String.valueOf(resultCode));
        //if (resultCode != RESULT_OK)
        if (requestCode == 2 && data != null) {
            Uri mVideoURI = data.getData();
            videopath = mVideoURI;
            videoString = videopath.toString();
            Log.d("onActivityResult", mVideoURI.toString());
            Log.d("Result videoString", videoString);
            //Log.d("getRealPathFromURI", getRealPathFromURI(getContext(), mVideoURI));
            videoviewSetup(mVideoURI);
        }
    }

    private SeekBar.OnSeekBarChangeListener alphaChangListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            // 메시지 큐에 저장될 메시지의 내용;
                            webView.setAlpha(progress);
                        }
                    });
                }
            }).start();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if(webView != null){
                webView.pauseTimers();
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(webView != null){
                webView.resumeTimers();
            }
        }
    };
    /**
     * The capture button controls all user interaction. When recording, the button click
     * stops recording, releases {@link MediaRecorder} and {@link Camera}. When not recording,
     * it prepares the {@link MediaRecorder} and starts recording.
     *
     * @param view the view generating the event.
     */
    public void onCaptureClick(View view) {
        if (isRecording) {
            // BEGIN_INCLUDE(stop_release_media_recorder)
            releaseMediaRecorder();
            releaseCamera();
            // stop recording and release camera
            try {
                mMediaRecorder.stop();  // stop the recording
            } catch (RuntimeException e) {
                // RuntimeException is thrown when stop() is called immediately after start().
                // In this case the output file is not properly constructed ans should be deleted.
                Log.d(TAG, "RuntimeException: stop() is called immediately after start()");
                //noinspection ResultOfMethodCallIgnored
                mOutputFile.delete();
            }
            releaseMediaRecorder(); // release the MediaRecorder object
            mCamera.lock();         // take camera access back from MediaRecorder
            // inform the user that recording has stopped
            isRecording = false;
            releaseCamera();
            // END_INCLUDE(stop_release_media_recorder)

        } else {
            // BEGIN_INCLUDE(prepare_start_media_recorder)
            prepareVideoRecorder();
            new MediaPrepareTask().execute(null, null, null);
            // END_INCLUDE(prepare_start_media_recorder)
        }
    }
    public void flipit() {
        //myCamera is the Camera object
        if (Camera.getNumberOfCameras()>=2) {
            mCamera.stopPreview();
            mCamera.release();
            //"which" is just an integer flag
            switch (witch) {
                case 0:
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    witch = 1;
                    break;
                case 1:
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    witch = 0;
                    break;
            }
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                //"this" is a SurfaceView which implements SurfaceHolder.Callback,
                //as found in the code examples
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
            } catch (IOException exception) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // if we are using MediaRecorder, release it first
        releaseMediaRecorder();
        // release the camera immediately on pause event
        releaseCamera();
        webView.pauseTimers();
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            // clear recorder configuration
            mMediaRecorder.reset();
            // release the recorder object
            mMediaRecorder.release();
            mMediaRecorder = null;
            // Lock camera for later use i.e taking it back from MediaRecorder.
            // MediaRecorder doesn't need it anymore and we will release it if the activity pauses.
            mCamera.lock();
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            // release the camera for other applications
            mCamera.release();
            mCamera = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean prepareVideoRecorder(){

        // BEGIN_INCLUDE (configure_preview)
        mCamera = CameraHelper.getDefaultCameraInstance();

        // We need to make sure that our preview and recording video size are supported by the
        // camera. Query camera to find all the sizes and choose the optimal size given the
        // dimensions of our preview surface.
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> mSupportedVideoSizes = parameters.getSupportedVideoSizes();
        Camera.Size optimalSize = CameraHelper.getOptimalVideoSize(mSupportedVideoSizes,
                mSupportedPreviewSizes, textureView.getWidth(), textureView.getHeight());

        // Use the same size for recording profile.
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        profile.videoFrameWidth = optimalSize.width;
        profile.videoFrameHeight = optimalSize.height;

        // likewise for the camera object itself.
        parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);
        mCamera.setParameters(parameters);
        try {
            // Requires API level 11+, For backward compatibility use {@link setPreviewDisplay}
            // with {@link SurfaceView}
            mCamera.setPreviewTexture(textureView.getSurfaceTexture());
        } catch (IOException e) {
            Log.e(TAG, "Surface texture is unavailable or unsuitable" + e.getMessage());
            return false;
        }
        // END_INCLUDE (configure_preview)


        // BEGIN_INCLUDE (configure_media_recorder)
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC );
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(profile);

        // Step 4: Set output file
        mOutputFile = CameraHelper.getOutputMediaFile(CameraHelper.MEDIA_TYPE_VIDEO);
        if (mOutputFile == null) {
            return false;
        }
        mMediaRecorder.setOutputFile(mOutputFile.getPath());
        // END_INCLUDE (configure_media_recorder)

        // Step 5: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }

    /**
     * Asynchronous task for preparing the {@link MediaRecorder} since it's a long blocking
     * operation.
     */
    class MediaPrepareTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();

                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                getActivity().finish();
            }
            // inform the user that recording has started

        }
    }
}