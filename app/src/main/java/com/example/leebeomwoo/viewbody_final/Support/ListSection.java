package com.example.leebeomwoo.viewbody_final.Support;

import android.util.Log;

import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.Response.ResponseLd;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeeBeomWoo on 2017-04-21.
 */

public class ListSection {
    private List<ListDummyItem> items;

    public List<ListDummyItem> SetItem(String section){
        Call<ResponseLd> call = ConAdapter.getInstance().getResult_Ld(section);
        call.enqueue(new Callback<ResponseLd>() {
            @Override
            public void onResponse(Call<ResponseLd> call, Response<ResponseLd> response) {
               ResponseLd responseLd = response.body();
                items = responseLd.getLdItem();
                Log.d("response", items.toString());
            }
            @Override
            public void onFailure(Call<ResponseLd> call, Throwable t) {
                Log.d("ListSection",t.getMessage());
            }
        });
        return items;
    }
}
