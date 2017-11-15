package com.example.leebeomwoo.viewbody_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import cn.gavinliu.android.lib.scale.ScaleLinearLayout;
import cn.gavinliu.android.lib.scale.ScaleRelativeLayout;
import cn.gavinliu.android.lib.scale.config.ScaleConfig;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView body_imgB, food_imgB, follow_imgB, home_imgB, qna_imgB, writer_imgB;
    ScaleRelativeLayout top, top_sub, bTn;
    ScaleLinearLayout bottom;
    String PROPNAME_SCREENLOCATION_LEFT = "START_POINT";
    String PROPNAME_SCREENLOCATION_TOP = "TOP_POINT";
    String PROPNAME_WIDTH = "DEVICE_WIDTH";
    String PROPNAME_HEIGHT = "DEVICE_HEIGHT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("message")) {
            Bundle bundle = getIntent().getExtras();
            int i = bundle.getInt("message");
        } else{
            SharedPreferences preferencesCompat = getSharedPreferences("a", MODE_PRIVATE);
            int tutorial = preferencesCompat.getInt("First", 0);
            if(tutorial == 0){
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this)) {
                } else {
                    Toast.makeText(this, "시스템 설정을 허가하기를 원치 않으시면 화면회전을 자동으로 설저앟여 주시기 바랍니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first);
        body_imgB = findViewById(R.id.body_first);
        food_imgB = findViewById(R.id.food_first);
        follow_imgB =  findViewById(R.id.follow_first);
        home_imgB = findViewById(R.id.home_first);
        qna_imgB =  findViewById(R.id.qna_first);
        writer_imgB = findViewById(R.id.writer_first);
        top = (ScaleRelativeLayout) findViewById(R.id.first_top);
        top_sub = (ScaleRelativeLayout) findViewById(R.id.first_top_sub);
        bTn = (ScaleRelativeLayout) findViewById(R.id.First_Btn);
        bottom = (ScaleLinearLayout) findViewById(R.id.first_bottom);
        body_imgB.setOnClickListener(this);
        food_imgB.setOnClickListener(this);
        follow_imgB.setOnClickListener(this);
        home_imgB.setOnClickListener(this);
        qna_imgB.setOnClickListener(this);
        writer_imgB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.body_first:
                nextPage(1, "body", view);
                break;
            case R.id.food_first:
                nextPage(3, "food", view);
                break;
            case R.id.follow_first:
                nextPage(2, "follow", view);
                break;
            case R.id.home_first:
                nextPage(0, "home", view);
                break;
            case R.id.qna_first:
                nextPage(5, "qna", view);
                break;
            case R.id.writer_first:
                nextPage(4, "writer", view);
                break;
        }
    }
    private void nextPage(int p, String s, View view){
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        Pair<View, String> p0 = Pair.create((View) home_imgB, "home");
        Pair<View, String> p1 = Pair.create((View) body_imgB, "body");
        Pair<View, String> p2 = Pair.create((View) follow_imgB, "follow");
        Pair<View, String> p3 = Pair.create((View)food_imgB, "food");
        Pair<View, String> p4 = Pair.create((View) writer_imgB, "writer");
        Pair<View, String> p5 = Pair.create((View)qna_imgB, "qna");
        intent.putExtra("transition_name", /* start values */ s);
        intent.putExtra("transition", /* start values */ captureValues(view));
        intent.putExtra("message", p);
        // Get the transition name from the string
        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(FirstActivity.this,
                        view,   // Starting view
                        ViewCompat.getTransitionName(view)    // The String
                );
        //Start the Intent
        startActivity(intent, options.toBundle());
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    private Bundle captureValues(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt(PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
        b.putInt(PROPNAME_SCREENLOCATION_TOP, screenLocation[1]);
        b.putInt(PROPNAME_WIDTH, view.getWidth());
        b.putInt(PROPNAME_HEIGHT, view.getHeight());
        return b;
    }
}
