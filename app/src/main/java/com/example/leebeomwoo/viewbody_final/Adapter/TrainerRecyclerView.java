package com.example.leebeomwoo.viewbody_final.Adapter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Item.CardItem;
import com.example.leebeomwoo.viewbody_final.Item.WriterItem;
import com.example.leebeomwoo.viewbody_final.ItemGroup.TrainerInfoFragment;
import com.example.leebeomwoo.viewbody_final.ItemViewActivity;
import com.example.leebeomwoo.viewbody_final.MainActivity;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeeBeomWoo on 2017-05-29.
 */

public class TrainerRecyclerView extends RecyclerView.Adapter<TrainerRecyclerView.ViewHolder>{
    List<CardItem> cardItems = new ArrayList<>();
    Context bContext;

    public TrainerRecyclerView(Context context, List<CardItem> cardItemList){
        this.cardItems = cardItemList;
        this.bContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mView;
        public final TextView category;
        public final TextView nickname;
        public final TextView writer_selfintrd;
        public final WebView faceimage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mView = (CardView) itemLayoutView.findViewById(R.id.card_Card);
            category = (TextView) itemLayoutView.findViewById(R.id.category_txtB);
            nickname = (TextView) itemLayoutView.findViewById(R.id.nickname_txtB);
            writer_selfintrd = (TextView) itemLayoutView.findViewById(R.id.writer_selfintrd);
            faceimage = (WebView) itemLayoutView.findViewById(R.id.faceimage);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final CardItem cardItem = cardItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.category.setText(cardItem.getCategory());
        viewHolder.nickname.setText(cardItem.getNickName());
        viewHolder.writer_selfintrd.setText(cardItem.getIntroduce());
        viewHolder.faceimage.loadUrl(ConAdapter.SERVER_URL + cardItem.getImageUrl()); //실제 구동시
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_2 = new Intent(bContext, ItemViewActivity.class);
                intent_2.putExtra("itemUrl", "trainer");
                intent_2.putExtra("tr_Id", cardItem.getLd_Id());
                intent_2.putExtra("section", cardItem.getCategory());
                intent_2.putExtra("fragment", 1);
                bContext.startActivity(intent_2);
            }
        });
    }
    @Override
    public int getItemCount() {

        return (null != cardItems ? cardItems.size() : 0);
    }
}
