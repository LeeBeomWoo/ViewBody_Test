package com.example.leebeomwoo.viewbody_final.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Adapter.MyCardRecyclerViewAdapter;
import com.example.leebeomwoo.viewbody_final.Item.WriterFirstItem;
import com.example.leebeomwoo.viewbody_final.Item.WriterItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeeBeomWoo on 2017-05-29.
 */

public class Writer_Fragment extends Fragment {
    private static String TAG = "Writer_Fragment";
    TextView trainer_association, trainer_nicname, trainer_call, trainer_email;
    WebView trainer_facewebView;
    RecyclerView lirv, awrv;
    WriterFirstItem ldItems;
    List<WriterItem> liItem, awItem;
    int i;
    MyCardRecyclerViewAdapter lirecyclerViewAdapter, awrecyclerAdapter;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trainer_info, container, false);
        trainer_association = (TextView) view.findViewById(R.id.trainer_association_txt);
        trainer_nicname = (TextView) view.findViewById(R.id.trainer_nicname_txt);
        trainer_call = (TextView) view.findViewById(R.id.trainer_call_txt);
        trainer_email = (TextView) view.findViewById(R.id.trainer_email_txt);
        trainer_facewebView = (WebView) view.findViewById(R.id.trainer_facewebView);

        lirv = (RecyclerView) view.findViewById(R.id.license_list);
        setHasOptionsMenu(true);
        lirv.setHasFixedSize(true);
        getActivity().invalidateOptionsMenu();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lirv.setLayoutManager(llm);
        awrv = (RecyclerView) view.findViewById(R.id.award_list);
        setHasOptionsMenu(true);
        awrv.setHasFixedSize(true);
        getActivity().invalidateOptionsMenu();
        LinearLayoutManager allm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allm.setInitialPrefetchItemCount(300);
        awrv.setLayoutManager(allm);
        Call<WriterFirstItem> call = ConAdapter.getInstance().getResult_Writer();
        call.enqueue(new Callback<WriterFirstItem>() {
            @Override
            public void onResponse(Call<WriterFirstItem> call, Response<WriterFirstItem> response) {
                ldItems = response.body();
                Log.d(TAG,"서버와의 연결이 잘됐어요~.");
                Log.d("response", ldItems.toString());
                liItem = ldItems.getLiList();
                trainer_association.setText(ldItems.getAssociation());
                trainer_call.setText(ldItems.getCall());
                trainer_facewebView.loadUrl(ldItems.getImageUrl());
                trainer_email.setText(ldItems.getEmail());
                trainer_nicname.setText(ldItems.getNickname());
                lirecyclerViewAdapter = new MyCardRecyclerViewAdapter(getActivity(), liItem);
                lirv.setAdapter(lirecyclerViewAdapter);
                awItem = ldItems.getAwList();
                awrecyclerAdapter = new MyCardRecyclerViewAdapter(getActivity(), awItem);
                awrv.setAdapter(awrecyclerAdapter);
            }
            @Override
            public void onFailure(Call<WriterFirstItem> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

        return  view;
    }
}
