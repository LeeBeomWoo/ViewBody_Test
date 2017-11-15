package com.example.leebeomwoo.viewbody_final.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Fragment.Account_Fragment;
import com.example.leebeomwoo.viewbody_final.Item.CardItem;
import com.example.leebeomwoo.viewbody_final.Item.WriterFirstItem;
import com.example.leebeomwoo.viewbody_final.Item.WriterItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCardRecyclerViewAdapter extends RecyclerView.Adapter<MyCardRecyclerViewAdapter.ViewHolder> {

    List<WriterItem> cardItems = new ArrayList<>();
    Context bContext;

    public MyCardRecyclerViewAdapter(Context context, List<WriterItem> cardItemList){
        this.cardItems = cardItemList;
        this.bContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mView;
        public final TextView association;
        public final TextView name;
        public final ImageView check;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mView = (CardView) itemLayoutView.findViewById(R.id.card_Card);
            association = (TextView) itemLayoutView.findViewById(R.id.association_txtB);
            name = (TextView) itemLayoutView.findViewById(R.id.name_txtB);
            check = (ImageView) itemLayoutView.findViewById(R.id.check_B);
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
        final WriterItem cardItem = cardItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.association.setText(cardItem.getAssociation());
        viewHolder.name.setText(cardItem.getName());
        //  viewHolder.imgViewIcon.loadUrl(ConAdapter.SERVER_URL + bdItem.getBd_ImageUrl()); 실제 구동시

        if(cardItem.getCheck() == "ok") {
            viewHolder.check.setImageResource(R.drawable.checked);
        }else {
            viewHolder.check.setImageResource(R.drawable.arrows1);
        }
    }
    @Override
    public int getItemCount() {

        return (null != cardItems ? cardItems.size() : 0);
    }

}
