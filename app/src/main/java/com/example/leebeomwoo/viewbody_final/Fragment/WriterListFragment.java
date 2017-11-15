package com.example.leebeomwoo.viewbody_final.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.leebeomwoo.viewbody_final.Adapter.MyCardRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Adapter.TrainerRecyclerView;
import com.example.leebeomwoo.viewbody_final.Item.CardItem;
import com.example.leebeomwoo.viewbody_final.Item.LikeItem;
import com.example.leebeomwoo.viewbody_final.Item.WriterItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Response.ResponseCard;
import com.example.leebeomwoo.viewbody_final.Response.ResponseFm;
import com.example.leebeomwoo.viewbody_final.Adapter.MovieRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriterListFragment extends android.support.v4.app.Fragment {
    ResponseCard responseCd;
    TrainerRecyclerView adapter;
    private static final String TAG = "WriterListFragment";
    List<CardItem> trlikeItems, fdlikeItems;
    private View view;
    RecyclerView trrv, fdrv;
    public WriterListFragment(){}
    FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_list, container, false);
        trrv = (RecyclerView)view.findViewById(R.id.card_list);
        setHasOptionsMenu(true);
        trrv.setHasFixedSize(true);
        getActivity().invalidateOptionsMenu();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        trrv.setLayoutManager(llm);
        Call<ResponseCard> call = ConAdapter.getInstance().getResult_Card("Person");
        call.enqueue(new Callback<ResponseCard>() {
            @Override
            public void onResponse(Call<ResponseCard> call, Response<ResponseCard> response) {
                responseCd = response.body();
                Log.d(TAG, responseCd.toString());
                Toast toast = Toast.makeText(getContext(), responseCd.getResult(), Toast.LENGTH_SHORT);
                toast.show();
                trlikeItems = responseCd.getbCardItem();
                adapter = new TrainerRecyclerView(getActivity(), trlikeItems);
                trrv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseCard> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"서버와의 연결이 안됬습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
    }


    public static WriterListFragment newInstance() {
    return new WriterListFragment();
    }
}
