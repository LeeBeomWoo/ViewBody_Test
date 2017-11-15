package com.example.leebeomwoo.viewbody_final;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leebeomwoo.viewbody_final.Adapter.ListRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Adapter.TrainerRecyclerView;
import com.example.leebeomwoo.viewbody_final.Item.CardItem;
import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.Response.ResponseCard;
import com.example.leebeomwoo.viewbody_final.Response.ResponseLd;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;
import com.example.leebeomwoo.viewbody_final.Support.ListSection;
import com.example.leebeomwoo.viewbody_final.Support.RecyclerviewClickEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeeBeomWoo on 2017-04-21.
 */

public class Home_Tab extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "0";
        private static final String TAG = "Home_Tab";
        ResponseLd responseLd;
        ResponseCard responseCd;
        RecyclerviewClickEvent clickEvent;
        private RecyclerView nw, pw, ns, ps;
        private ListRecyclerViewAdapter nsa, psa;
        private TrainerRecyclerView nwa, pwa;
        private List<ListDummyItem> nsldItems, psldItems;
        private List<CardItem> nwldItems, pwldItems;

        View view;
        ListSection listSection;
        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
        public Home_Tab() {
                // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param Id Parameter 1.
         * @param section Parameter 2.
         * @return A new instance of fragment BodyTab_Sub.
         */
        // TODO: Rename and change types and number of parameters
        public static Fragment Home_Tab_start(String Id, String section) {
            BodyTab_Sub fragment = new BodyTab_Sub();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, Id);
            args.putString(ARG_PARAM2, section);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_home_tab, container, false);
            nw = (RecyclerView) view.findViewById(R.id.new_list);
            pw = (RecyclerView) view.findViewById(R.id.popular_list);
            ns = (RecyclerView) view.findViewById(R.id.new_writer_list);
            ps = (RecyclerView) view.findViewById(R.id.popular_writer_list);
            setHasOptionsMenu(true);
            getActivity().invalidateOptionsMenu();
            listSetup(nw);
            listSetup(pw);
            listSetup(ns);
            listSetup(ps);
            return view;
        }
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Activity a;
            Log.d(TAG, "onAttach");
            if (context instanceof Activity){
                a=(Activity) context;
            }
        }
        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, "onResume");
        }
    private void listStart(final RecyclerView rv){
        Call<ResponseLd> call;
        Call<ResponseCard> cardcall;
        switch (rv.getId()){
            case R.id.new_list:
                call = ConAdapter.getInstance().getResult_Ld("New_Source");
                call.enqueue(new Callback<ResponseLd>() {
                    @Override
                    public void onResponse(Call<ResponseLd> call, Response<ResponseLd> response) {
                        if(response.isSuccessful()) {
                            responseLd = response.body();
                            Log.d(TAG + "_1", "서버와의 연결이 잘됐어요~.");
                            Log.d(TAG + "_1", responseLd.toString());
                            nsldItems = responseLd.getLdItem();
                            nsa = new ListRecyclerViewAdapter(getActivity(), nsldItems, R.drawable.new_title_back);
                            Log.d("response", nsldItems.toString());
                            rv.setAdapter(nsa);
                        }else{
                            Log.d(TAG+ "_2", response.errorBody().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseLd> call, Throwable t) {
                        Log.d(TAG+ "_3",t.getMessage());
                    }
                });
                break;
            case R.id.popular_list:
                call = ConAdapter.getInstance().getResult_Ld("Poppular");
                call.enqueue(new Callback<ResponseLd>() {
                    @Override
                    public void onResponse(Call<ResponseLd> call, Response<ResponseLd> response) {
                        if(response.isSuccessful()) {
                            responseLd = response.body();
                            Log.d(TAG + "_1", "서버와의 연결이 잘됐어요~.");
                            Log.d(TAG + "_1", responseLd.toString());
                            psldItems = responseLd.getLdItem();
                            psa = new ListRecyclerViewAdapter(getActivity(), psldItems, R.drawable.new_title_back);
                            Log.d("response", psldItems.toString());
                            rv.setAdapter(psa);
                        }else{
                            Log.d(TAG+ "_2", response.errorBody().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseLd> call, Throwable t) {
                        Log.d(TAG+ "_3",t.getMessage());
                    }
                });
                break;
            case R.id.new_writer_list:
                cardcall = ConAdapter.getInstance().getResult_Card("New_Writer");
                cardcall.enqueue(new Callback<ResponseCard>() {
                    @Override
                    public void onResponse(Call<ResponseCard> call, Response<ResponseCard> response) {
                        if(response.isSuccessful()) {
                            responseCd = response.body();
                            Log.d(TAG + "_1", "서버와의 연결이 잘됐어요~.");
                            Log.d(TAG + "_1", responseCd.toString());
                            nwldItems = responseCd.getbCardItem();
                            nwa = new TrainerRecyclerView(getActivity(), nwldItems);
                            Log.d("response", nwldItems.toString());
                            rv.setAdapter(nwa);
                        }else{
                            Log.d(TAG+ "_2", response.errorBody().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseCard> call, Throwable t) {
                        Log.d(TAG+ "_3",t.getMessage());
                    }
                });
                break;
            case R.id.popular_writer_list:
                cardcall = ConAdapter.getInstance().getResult_Card("Top_Writer");
                cardcall.enqueue(new Callback<ResponseCard>() {
                    @Override
                    public void onResponse(Call<ResponseCard> call, Response<ResponseCard> response) {
                        if(response.isSuccessful()) {
                            responseCd = response.body();
                            Log.d(TAG + "_1", "서버와의 연결이 잘됐어요~.");
                            Log.d(TAG + "_1", responseCd.toString());
                            pwldItems = responseCd.getbCardItem();
                            pwa = new TrainerRecyclerView(getActivity(), pwldItems);
                            Log.d("response", pwldItems.toString());
                            rv.setAdapter(pwa);
                        }else{
                            Log.d(TAG+ "_2", response.errorBody().toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseCard> call, Throwable t) {
                        Log.d(TAG+ "_3",t.getMessage());
                    }
                });
                break;
        }

    }
    private void listSetup(RecyclerView rv){
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(llm);
        listStart(rv);
    }
}


