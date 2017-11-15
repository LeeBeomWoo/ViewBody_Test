package com.example.leebeomwoo.viewbody_final.FaceFake;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by LeeBeomWoo on 2017-02-14.
 */

public class FaceOverlayView extends View {

    private Bitmap mBitmap;
    private SparseArray<FaceDetector.Face> mFaces;

    public FaceOverlayView(Context context) {
        this(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setBitmap( Bitmap bitmap ) {
        mBitmap = bitmap;
    }
}