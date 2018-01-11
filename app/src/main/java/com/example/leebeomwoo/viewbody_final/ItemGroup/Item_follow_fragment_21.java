package com.example.leebeomwoo.viewbody_final.ItemGroup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.leebeomwoo.viewbody_final.CameraUse.AutoFitTextureView;
import com.example.leebeomwoo.viewbody_final.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import cn.gavinliu.android.lib.scale.ScaleFrameLayout;
import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;


/**
 * Created by LeeBeomWoo on 2017-02-15.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Item_follow_fragment_21 extends Fragment implements FragmentCompat.OnRequestPermissionsResultCallback, View.OnClickListener{
    private static final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
    private static final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;
    private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
    private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();
    ImageButton play, record, load, camerachange, play_recordBtn;
    private final static String FURL = "<html><body><iframe width=\"1280\" height=\"720\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    private static final String TAG = "Item_follow_fragment_21";
    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";
    //ScaleRelativeLayout bTnLayout;
    //ScaleFrameLayout cameraLayout;
    ScaleRelativeLayout main, bTnLayout;
    ScaleFrameLayout cameraLayout;
    int page_num;
    ScaleRelativeLayout.LayoutParams LandButton, LandCamera, LandWebView, playlayout, recordlayout, switchlayout, loadlayout, play_recordlayout;
    Boolean play_record = true; //true 가 촬영모드, false 가 재생모드
    public static final String CAMERA_FRONT = "1";
    public static final String CAMERA_BACK = "0";
    public String change, temp, videoString;
    public Uri videopath;
    private String cameraId = CAMERA_FRONT;
    public VideoView videoView;
    SeekBar seekBar;
    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    static {
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    static {
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
    }
    /**
     * An {@link AutoFitTextureView} for camera preview.
     */
    public static AutoFitTextureView mTextureView;
    WebView webView;
    View view;

    /**
     * Button to record video
     */

    /**
     * A refernce to the opened {@link CameraDevice}.
     */
    private CameraDevice mCameraDevice;

    String tr_id, imageUrl, tr_password, URL;
    /**
     * A reference to the current {@link CameraCaptureSession} for
     * preview.
     */
    private CameraCaptureSession mPreviewSession;

    /**
     * {@link TextureView.SurfaceTextureListener} handles several lifecycle events on a
     * {@link TextureView}.
     */
    private TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                              int width, int height) {
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }

    };

    public Item_follow_fragment_21() {
    }

    /**
     * The {@link Size} of camera preview.
     */
    private Size mPreviewSize;

    /**
     * The {@link Size} of video recording.
     */
    private Size mVideoSize;

    /**
     * MediaRecorder
     */
    private static MediaRecorder mMediaRecorder;
    /**
     * Whether the app is recording video now
     */
    public boolean mIsRecordingVideo;

    /**
     * An additional thread for running tasks that shouldn't block the UI.
     */
    private HandlerThread mBackgroundThread;

    /**
     * A {@link Handler} for running tasks in the background.
     */
    private Handler mBackgroundHandler;

    /**
     * A {@link Semaphore} to prevent the app from exiting before closing the camera.
     */
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    /**
     * {@link CameraDevice.StateCallback} is called when {@link CameraDevice} changes its status.
     */
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            startPreview();
            mCameraOpenCloseLock.release();
            if (null != mTextureView) {
                configureTransform(mTextureView.getWidth(), mTextureView.getHeight());
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
            Activity activity = getActivity();
            if (null != activity) {
                activity.finish();
            }
        }

    };
    private Integer mSensorOrientation;
    private String mNextVideoAbsolutePath;
    private CaptureRequest.Builder mPreviewBuilder;
    public static Item_follow_fragment_21 newInstance(String tr_id, String imageUrl, int section) {
        Item_follow_fragment_21 item_follow_fragment_21 = new Item_follow_fragment_21();
        Bundle bundle = new Bundle();
        bundle.putString("tr_id", tr_id);
        bundle.putString("imageUrl", imageUrl);
        bundle.putInt("section", section);
        item_follow_fragment_21.setArguments(bundle);
        Log.d("프래그먼트 호출:", imageUrl);
        return item_follow_fragment_21;
    }

    /**
     * In this sample, we choose a video size with 3x4 aspect ratio. Also, we don't use sizes
     * larger than 1080p, since MediaRecorder cannot handle such a high-resolution video.
     * 이 샘플에서는 가로 세로 비율이 3x4 인 비디오 크기를 선택합니다. 또한 크기를 사용하지 않습니다.
     * MediaRecorder가 이러한 고해상도 비디오를 처리 할 수 없기 때문에 1080p보다 큽니다.
     * @param choices The list of available sizes
     * @return The video size
     */
    private static Size chooseVideoSize(Size[] choices) {
        for (Size size : choices) {
            if (size.getWidth() == size.getHeight() * 16 / 10 && size.getWidth() <= 1080) {
                return size;
            }
        }
        Log.e(TAG, "Couldn't find any suitable video size");
        return choices[choices.length - 1];
    }

    /**
     * Given {@code choices} of {@code Size}s supported by a camera, chooses the smallest one whose
     * width and height are at least as large as the respective requested values, and whose aspect
     * ratio matches with the specified value.
     *
     * 녹화가 가능한 사이즈들 중에서 프리뷰로 보여줄 수 있는 사이즈를 선택한다.
     * @param choices     The list of sizes that the camera supports for the intended output class
     * @param width       The minimum desired width
     * @param height      The minimum desired height
     * @param aspectRatio The aspect ratio
     * @return The optimal {@code Size}, or an arbitrary one if none were big enough
     */
    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<Size>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }

        // Pick the smallest of those, assuming we found any
        if (bigEnough.size() > 0) {
            return Collections.max(bigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setRetainInstance(true);
        if(getArguments() !=null){
            tr_id = getArguments().getString("tr_Id");
            imageUrl = getArguments().getString("itemUrl");
            page_num = getArguments().getInt("page_num");
            temp = getArguments().getString("video");
            change = temp.replace("https://youtu.be", CHANGE);
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG, "onAttachFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            PortrainSet();
            if (mTextureView != null && mTextureView.isAvailable()) {
                configureTransform(mTextureView.getWidth(), mTextureView.getHeight());
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            LandSet();
            if (mTextureView != null && mTextureView.isAvailable()) {
                configureTransform(mTextureView.getHeight(), mTextureView.getWidth());
            }
        }
    }
    private void LandSet(){
        LandWebView = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LandButton = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.btnlayoutSiz_item), ViewGroup.LayoutParams.MATCH_PARENT);
        LandCamera = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        playlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        switchlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        play_recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        loadlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.imageBtnsize_item));
        ScaleRelativeLayout.LayoutParams seek = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        //LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        bTnLayout.setLayoutParams(LandButton);
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
        switchlayout.addRule(ScaleRelativeLayout.ABOVE, R.id.record_Btn);
        switchlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        camerachange.setLayoutParams(switchlayout);
        LandCamera.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        cameraLayout.setLayoutParams(LandCamera);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        LandWebView.addRule(ScaleRelativeLayout.BELOW, R.id.alpha_control);
        LandWebView.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        webView.setLayoutParams(LandWebView);
        seek.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        seek.addRule(ScaleRelativeLayout.END_OF, R.id.button_layout);
        seekBar.setLayoutParams(seek);
        seekBar.setProgress(50);
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setZ((float)2);
        webView.setAlpha((float)0.5);
        webView.setZ((float)2);
        cameraLayout.setZ((float)0);
        mTextureView.setZ((float)0);
    }
    private void PortrainSet(){
        LandWebView = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.videoviewSiz_item));
        LandButton = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LandCamera = new ScaleRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        playlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item));
        recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item));
        switchlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item));
        play_recordlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item));
        loadlayout = new ScaleRelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item), getResources().getDimensionPixelSize(R.dimen.portlaneimageBtnsize_item));
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        LandWebView.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        webView.setLayoutParams(LandWebView);
        LandButton.addRule(ScaleRelativeLayout.ABOVE, R.id.web_movie);
        LandButton.addRule(ScaleRelativeLayout.ALIGN_PARENT_END);
        bTnLayout.setLayoutParams(LandButton);
        playlayout.addRule(ScaleRelativeLayout.ALIGN_PARENT_BOTTOM);
        playlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        play.setLayoutParams(playlayout);
        recordlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        recordlayout.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        record.setLayoutParams(recordlayout);
        loadlayout.addRule(ScaleRelativeLayout.ABOVE, R.id.play_Btn);
        loadlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        load.setLayoutParams(loadlayout);
        play_recordlayout.addRule(ScaleRelativeLayout.CENTER_VERTICAL);
        play_recordlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        play_recordBtn.setLayoutParams(play_recordlayout);
        switchlayout.addRule(ScaleRelativeLayout.BELOW, R.id.record_Btn);
        switchlayout.setMargins(getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item), getResources().getDimensionPixelSize(R.dimen.imageBtnmargine_item));
        camerachange.setLayoutParams(switchlayout);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_START);
        LandCamera.addRule(ScaleRelativeLayout.ALIGN_PARENT_TOP);
        LandCamera.addRule(ScaleRelativeLayout.START_OF, R.id.button_layout);
        LandCamera.addRule(ScaleRelativeLayout.ABOVE, R.id.web_movie);
        cameraLayout.setLayoutParams(LandCamera);
        seekBar.setVisibility(View.GONE);
        webView.setAlpha((float)1);
    }
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        if (!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions();
        }
        view = inflater.inflate(R.layout.fragment_follow_portrain_itemview, container, false);
        viewSet();
        onConfigurationChanged(getActivity().getResources().getConfiguration());
        startBackgroundThread();
        if(getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
            webView.setAlpha((float)1);
        }else {
            openCamera(mTextureView.getHeight(), mTextureView.getWidth());
            seekBar.setProgress(50);
            webView.setAlpha((float)0.5);
        }
        startPreview();
        seekBar.setMax(100);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.setWebViewClient(new WebViewClient());
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setSupportMultipleWindows(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        URL = FURL + change + BURL;
        Log.d(TAG, "temp : " + temp + "," + "tr_id : " + tr_id );
        webView.loadData(URL, "text/html", "charset=utf-8");
        seekBar.setOnSeekBarChangeListener(alphaChangListener);
        return view;
    }
    private void viewSet(){
        mTextureView = (AutoFitTextureView) view.findViewById(R.id.AutoView);
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
        main = (ScaleRelativeLayout) view.findViewById(R.id.item_mainLayout);
        record.setOnClickListener(this);
        load.setOnClickListener(this);
        play.setOnClickListener(this);
        play_recordBtn.setOnClickListener(this);
        camerachange.setOnClickListener(this);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play);
            }
        });
    }
    private void ButtonImageSetUp(){
        if(videoView.isPlaying()){
            play.setImageResource(R.drawable.pause);
        }else{
            play.setImageResource(R.drawable.play);
        }
        if(mIsRecordingVideo){
            record.setImageResource(R.drawable.stop);
        }else {
            record.setImageResource(R.drawable.record);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult");
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

    private void videoviewSetup(Uri path){
        videoView.setVideoURI(path);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        ButtonImageSetUp();
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
                                double a = progress/100.0;
                                float b = (float)a;
                                webView.setAlpha(b);
                            }
                        });
                    }
            }).start();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if(webView != null){
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(webView != null){
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        startBackgroundThread();
        reopenCamera();
        webView.resumeTimers();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        closeCamera();
        stopBackgroundThread();
        super.onPause();
        webView.pauseTimers();
    }
    /**
     * Starts a background thread and its {@link Handler}.
     */
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    public void switchCamera() {
        if (cameraId.equals(CAMERA_FRONT)) {
            cameraId = CAMERA_BACK;
            closeCamera();
            reopenCamera();

        } else if (cameraId.equals(CAMERA_BACK)) {
            cameraId = CAMERA_FRONT;
            closeCamera();
            reopenCamera();
        }
    }

    public void reopenCamera() {
        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }
    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        if(mBackgroundThread !=null) {
            mBackgroundThread.quitSafely();
            try {
                mBackgroundThread.join();
                mBackgroundThread = null;
                mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets whether you should show UI with rationale for requesting permissions.
     *
     * @param permissions The permissions your app wants to request.
     * @return Whether you can show permission rationale UI.
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Requests permissions needed for recording video.
     */
    private void requestVideoPermissions() {
        if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {
            new ConfirmationDialog().show(getActivity().getSupportFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(getActivity(), VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        if (requestCode == REQUEST_VIDEO_PERMISSIONS) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        ErrorDialog.newInstance(getString(R.string.permission_request_camera))
                                .show(getChildFragmentManager(), FRAGMENT_DIALOG);
                        break;
                    }
                }
            } else {
                ErrorDialog.newInstance(getString(R.string.permission_request_camera))
                        .show(getChildFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tries to open a {@link CameraDevice}. The result is listened by `mStateCallback`.
     */
    private void openCamera(int width, int height) {
        if (!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions();
            return;
        }
        final Activity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return;
        }
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        try {
            Log.d(TAG, "tryAcquire");
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }

            // Choose the sizes for camera preview and video recording
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics
                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            mVideoSize = chooseVideoSize(map.getOutputSizes(MediaRecorder.class));
            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                    width, height, mVideoSize);
            configureTransform(width, height);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            manager.openCamera(cameraId, mStateCallback, null);
        } catch (CameraAccessException e) {
            Toast.makeText(activity, "Cannot access the camera.", Toast.LENGTH_SHORT).show();
            activity.finish();
        } catch (NullPointerException e) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            ErrorDialog.newInstance(getString(R.string.camera_error))
                    .show(getActivity().getSupportFragmentManager(), FRAGMENT_DIALOG);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.");
        }
    }

    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            closePreviewSession();
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mMediaRecorder) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.");
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    /**
     * Start the camera preview.
     */
    private void startPreview() {
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            return;
        }
        try {
            closePreviewSession();
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            Surface previewSurface = new Surface(texture);
            mPreviewBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {

                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    mPreviewSession = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Activity activity = getActivity();
                    if (null != activity) {
                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the camera preview. {@link #startPreview()} needs to be called in advance.
     */
    private void updatePreview() {
        if (null == mCameraDevice) {
            return;
        }
        try {
            setUpCaptureRequestBuilder(mPreviewBuilder);
            HandlerThread thread = new HandlerThread("CameraPreview");
            thread.start();
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, mBackgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    }

    /**
     * Configures the necessary {@link Matrix} transformation to `mTextureView`.
     * This method should not to be called until the camera preview size is determined in
     * openCamera, or until the size of `mTextureView` is fixed.
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    private void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = getActivity();
        if (null == mTextureView || null == mPreviewSize || null == activity) {
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
        RectF landRect = new RectF(0, 0, mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Log.d("bufferRect :", String.valueOf(mPreviewSize.getWidth()) + "*" + String.valueOf(mPreviewSize.getHeight()));
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
            matrix.postScale(scale, scale * 2, deviceRect.centerX(), deviceRect.centerY());
            Log.d("postScale :", String.valueOf(scale * 2) + ":" + String.valueOf(centerX) + ":" + String.valueOf(centerY));
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
            Log.d("postScale :", String.valueOf(scale * 2) + ":" + String.valueOf(centerX) + ":" + String.valueOf(centerY));
        }
        mTextureView.setTransform(matrix);
        Log.d("mTextureView :", String.valueOf(mTextureView.getWidth()) + "*" + String.valueOf(mTextureView.getHeight()));
    }

    private void setUpMediaRecorder() throws IOException {
        final Activity activity = getActivity();
        if (null == activity) {
            return;
        }
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        if (mNextVideoAbsolutePath == null || mNextVideoAbsolutePath.isEmpty()) {
            mNextVideoAbsolutePath = getVideoFilePath(getActivity());
        }
        mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed = " + e.toString());
        }
        mMediaRecorder.start();
        mIsRecordingVideo = true;
    }
    @NonNull
    private String getVideoFilePath(Context context) {
        final File dir = context.getExternalFilesDir( null);
        return (dir == null ? "" : (Environment.getExternalStorageDirectory() + "/" +Environment.DIRECTORY_MOVIES + "/"))
                + "ViewBody_" +System.currentTimeMillis() + ".mp4";
    }

    public void startRecordingVideo() {
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            return;
        }
        try {
            closePreviewSession();
            setUpMediaRecorder();
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            List<Surface> surfaces = new ArrayList<>();

            // Set up Surface for the camera preview
            Surface previewSurface = new Surface(texture);
            surfaces.add(previewSurface);
            mPreviewBuilder.addTarget(previewSurface);

            // Set up Surface for the MediaRecorder
            Surface recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            mPreviewBuilder.addTarget(recorderSurface);

            // Start a capture session
            // Once the session starts, we can update the UI and start recording
            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {

                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    mPreviewSession = cameraCaptureSession;
                    updatePreview();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // UI
                            Log.d("Video_", "Start");
                            record.setImageResource(R.drawable.stop);
                            //mIsRecordingVideo = true;

                            // Start recording
                            //mMediaRecorder.start();
                        }
                    });
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Activity activity = getActivity();
                    if (null != activity) {
                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException | IOException e) {
            e.printStackTrace();
        }

    }

    private void closePreviewSession() {
        if (mPreviewSession != null) {
            mPreviewSession.close();
            mPreviewSession = null;
        }
    }

    public void stopRecordingVideo() {
        // UI
        mIsRecordingVideo = false;
        // Stop recording
        record.setImageResource(R.drawable.record);
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        // CameraHelper.getOutputMediaFile(2);

        Activity activity = getActivity();
        if (null != activity) {
            Toast.makeText(activity, "Video saved: " + mNextVideoAbsolutePath,
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Video saved: " + mNextVideoAbsolutePath);
        }
        Log.d("Video_", "Stop");
        mNextVideoAbsolutePath = null;
        startPreview();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_Btn://녹화
                Log.d(TAG, "record_Btn thouch");
                if (mTextureView.getVisibility() == View.INVISIBLE) {
                    mTextureView.setVisibility(View.VISIBLE);
                }
                if (mTextureView != null) {
                    if (mIsRecordingVideo) {
                        stopRecordingVideo();
                    } else {
                        videoView.setVisibility(View.INVISIBLE);
                        mTextureView.setVisibility(View.VISIBLE);
                        startRecordingVideo();
                    }
                }
                break;
            case R.id.play_Btn://재생
                Log.d(TAG, "play_Btn thouch");
                if(videoView.isPlaying()){
                    videoView.pause();
                    play.setImageResource(R.drawable.play);
                }else {
                    videoView.start();
                    play.setImageResource(R.drawable.pause);
                    mTextureView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.load_Btn://파일불러오기
                videoString = null;
                videopath = null;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        +  File.separator + Environment.DIRECTORY_MOVIES + File.separator);
                intent.setDataAndType(uri, "video/mp4");
                startActivityForResult(Intent.createChooser(intent, "Select Video"), 2);
                break;
            case R.id.play_record://파일과 카메라간 변환
                if(play_record){
                    if(mIsRecordingVideo){
                        stopRecordingVideo();
                    }
                    closeCamera();
                    mTextureView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    play_record = false;
                } else {
                    if(videoView.isPlaying()){
                        videoView.stopPlayback();
                    }
                    if(getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
                        openCamera(mTextureView.getWidth(), mTextureView.getHeight());
                    }else {
                        openCamera(mTextureView.getHeight(), mTextureView.getWidth());
                    }
                    startPreview();
                    mTextureView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.INVISIBLE);
                    play_record = true;
                }
                ButtonImageSetUp();
                break;
            case R.id.viewChange_Btn://전후면 카메라변환
                Log.d(TAG, "viewChange_Btn thouch");
                switchCamera();
                break;
        }
    }

    /**
     * Compares two {@code Size}s based on their areas.
     */
    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    })
                    .create();
        }

    }

    public static class ConfirmationDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_request)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(VIDEO_PERMISSIONS,
                                    REQUEST_VIDEO_PERMISSIONS);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parent.getActivity().finish();
                                }
                            })
                    .create();
        }

    }
}