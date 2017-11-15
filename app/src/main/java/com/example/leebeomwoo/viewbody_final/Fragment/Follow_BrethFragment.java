package com.example.leebeomwoo.viewbody_final.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.leebeomwoo.viewbody_final.Adapter.FollowListRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Adapter.ListRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Response.ResponseCbd;
import com.example.leebeomwoo.viewbody_final.Response.ResponseLd;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;
import com.example.leebeomwoo.viewbody_final.Support.RecyclerviewClickEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Follow_BrethFragment extends android.support.v4.app.Fragment {

    private RecyclerView rv;
    ResponseLd responseLd;
    ResponseCbd responseCbd;

    RecyclerviewClickEvent clickEvent = new RecyclerviewClickEvent();
    private List<ListDummyItem> ldItems;
    @SuppressLint("StaticFieldLeak")
    FollowListRecyclerViewAdapter bdadapter;

    String TAG = "Follow_BrethFragment";
    public Follow_BrethFragment(){}

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_follow_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.follow_recycler);
        setHasOptionsMenu(true);
        rv.setHasFixedSize(true);
        getActivity().invalidateOptionsMenu();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        listStart();
        return view;
    }

    public void datachanged(String category) {
        Call<ResponseCbd> call = ConAdapter.getInstance().CATEGORY_BODY(category);
        call.enqueue(new Callback<ResponseCbd>() {
            @Override
            public void onResponse(Call<ResponseCbd> call, Response<ResponseCbd> response) {
                responseCbd = response.body();
                Log.d("response changed", response.body().toString());
                ldItems = responseCbd.getCbdItem();
                bdadapter.setLkItems(ldItems);
            }
            @Override
            public void onFailure(Call<ResponseCbd> call, Throwable t) {
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void listStart(){
        Call<ResponseLd> call = ConAdapter.getInstance().getResult_Ld("Follow_breth");
        call.enqueue(new Callback<ResponseLd>() {
            @Override
            public void onResponse(Call<ResponseLd> call, Response<ResponseLd> response) {
                responseLd = response.body();
                Log.d(TAG,"서버와의 연결이 잘됐어요~.");
                ldItems = responseLd.getLdItem();
                bdadapter = new FollowListRecyclerViewAdapter(getActivity(), ldItems, R.drawable.follow_title_back);
                rv.setAdapter(bdadapter);
                Log.d("response", ldItems.toString());
            }
            @Override
            public void onFailure(Call<ResponseLd> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    private void secondlistStart() {
        Call<ResponseLd> call = ConAdapter.getInstance().getResult_Ld("Follow_Breth");
        call.enqueue(new Callback<ResponseLd>() {
            @Override
            public void onResponse(Call<ResponseLd> call, Response<ResponseLd> response) {
                responseLd = response.body();
                Log.d(TAG,"서버와의 연결이 잘됐어요~.");
                ldItems = responseLd.getLdItem();
                Log.d("response", ldItems.toString());
            }
            @Override
            public void onFailure(Call<ResponseLd> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
        bdadapter = new FollowListRecyclerViewAdapter(getActivity(), ldItems,  R.drawable.follow_title_back);
        rv.setAdapter(bdadapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }


    public static Follow_BrethFragment newInstance() {
        return new Follow_BrethFragment();
    }
}
