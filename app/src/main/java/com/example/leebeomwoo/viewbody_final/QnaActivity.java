package com.example.leebeomwoo.viewbody_final;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.leebeomwoo.viewbody_final.QnA.ReadFragment;
import com.example.leebeomwoo.viewbody_final.QnA.WriteFragment;


public class QnaActivity extends AppCompatActivity implements WriteFragment.OnFragmentInteractionListener{
Fragment read, write;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        Log.d("QnaActivity", "run");
        intent = getIntent();
        String pagenum = intent.getStringExtra("pagenum");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(pagenum != null){
                Log.d("QnaActivity", "write");
                read = new ReadFragment(pagenum);
                getSupportFragmentManager().beginTransaction().add(R.id.qna_fragment_container,read).commit();

            } else {
                write = new WriteFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.qna_fragment_container, write).commit();
            }
        }

    }
    public void onFragmentInteraction(String pagenum) {
        // 구현
        read = new ReadFragment(pagenum);
        // 존재하면 2 pane 레이아웃에 있는 상태이므로 바로 업데이트
        getSupportFragmentManager().beginTransaction().replace(R.id.qna_fragment_container,read).commit();
    }

}
