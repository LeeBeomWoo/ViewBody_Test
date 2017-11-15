package com.example.leebeomwoo.viewbody_final.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.leebeomwoo.viewbody_final.Adapter.ListRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Item.LikeItem;
import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Response.ResponseCbd;
import com.example.leebeomwoo.viewbody_final.Response.ResponseLd;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;
import com.example.leebeomwoo.viewbody_final.Support.EndlessRecyclerViewScrollListener;
import com.example.leebeomwoo.viewbody_final.Support.RecyclerviewClickEvent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upper_MuscleFragment extends android.support.v4.app.Fragment {

    private RecyclerView rv;
    ResponseLd responseLd;
    ResponseCbd responseCbd;
    private LikeItem lkItems;

    private List<ListDummyItem> ldItems = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    ListRecyclerViewAdapter bdadapter;
    RecyclerviewClickEvent clickEvent = new RecyclerviewClickEvent();
    String TAG = "Upper_MuscleFragment";
    EndlessRecyclerViewScrollListener scrollListener;
    Intent intent;
    public Upper_MuscleFragment(){}

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        rv = view.findViewById(R.id.detail_list);
        setHasOptionsMenu(true);
        rv.setHasFixedSize(true);
        getActivity().invalidateOptionsMenu();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listStart();
        if(ldItems != null) {
            Log.d("testitem", ldItems.toString());
        }
        rv.setLayoutManager(llm);
        return view;
    }
    private void getDate(List<ListDummyItem> item){
        ldItems = item;
    }
    private void listStart(){
        Call<ResponseLd> call = ConAdapter.getInstance().getResult_Ld("Upper_Muscle");
        call.enqueue(new Callback<ResponseLd>() {
            @Override
            public void onResponse(Call<ResponseLd> call, Response<ResponseLd> response) {
                responseLd = response.body();
                Log.d(TAG,"서버와의 연결이 잘됐어요~.");
                List<ListDummyItem> tempitem;
                tempitem = responseLd.getLdItem();
                Log.d("response_test",  responseLd.getLdItem().toString());
                getDate( responseLd.getLdItem());
                bdadapter = new ListRecyclerViewAdapter(getActivity(), tempitem, R.drawable.body_title_back);
                rv.setAdapter(bdadapter);
            }
            @Override
            public void onFailure(Call<ResponseLd> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
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

    public static Upper_MuscleFragment newInstance() {
        return new Upper_MuscleFragment();
    }
}
