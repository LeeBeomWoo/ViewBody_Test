package com.example.leebeomwoo.viewbody_final.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.leebeomwoo.viewbody_final.Item.UserInformationItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeeBeomWoo on 2017-04-27.
 */

public class Account_Fragment extends Fragment {
    Button kakao, google, facebook, join, login, out;
    EditText id, pw;
    UserInformationItem item;
    String user_id, user_pass;
    String TAG = "Account_Fragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user_id = getArguments().getString("user_id");
            user_pass = getArguments().getString("user_pass");
        }
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_layout, container, false);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreateView");
        getActivity().invalidateOptionsMenu();
        kakao = (Button) view.findViewById(R.id.kakao_Btn);
        google = (Button) view.findViewById(R.id.googleaccount_Btn);
        facebook = (Button) view.findViewById(R.id.facebook_Btn);
        join = (Button) view.findViewById(R.id.join_btn);
        login = (Button) view.findViewById(R.id.login_btn);
        out = (Button) view.findViewById(R.id.out_btn);
        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카카오 계정연동방식
            }
        });google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //구글계정 연동방식
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //페이스북 연동방식
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 페이지로 이동
                Fragment fragment = new Join_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.account_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디와 비밀번호를 입력 한 후 로그인

                if(id.getText() != null && pw.getText() != null){

                    Call<UserInformationItem> call = ConAdapter.getInstance().joinpush(item);
                    call.enqueue(new Callback<UserInformationItem>(){
                        @Override
                        public void onResponse(Call<UserInformationItem> call, Response<UserInformationItem> response) {
                            item = response.body();
                        }

                        @Override
                        public void onFailure(Call<UserInformationItem> call, Throwable t) {
                            Log.d(TAG, t.toString());
                        }
                    });
                }
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원탈퇴하기
                Call<UserInformationItem> call = ConAdapter.getInstance().joinpush(item);
                call.enqueue(new Callback<UserInformationItem>(){
                    @Override
                    public void onResponse(Call<UserInformationItem> call, Response<UserInformationItem> response) {
                        item = response.body();
                    }

                    @Override
                    public void onFailure(Call<UserInformationItem> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
            }
        });
        return view;
    }
}
