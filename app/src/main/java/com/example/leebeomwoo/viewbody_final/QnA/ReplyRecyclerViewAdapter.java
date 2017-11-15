package com.example.leebeomwoo.viewbody_final.QnA;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16. 9. 21.
 */

public class ReplyRecyclerViewAdapter extends RecyclerView.Adapter<ReplyRecyclerViewAdapter.ViewHolder>{

    List<QrpItem> qrpItems = new ArrayList<>();

    Context mContext;

    ReplyRecyclerViewAdapter(Context context, List<QrpItem> qrpItems){
        this.qrpItems = qrpItems;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtViewContent;
        public final TextView txtViewId;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewContent = (TextView) itemLayoutView.findViewById(R.id.reply_content);
            txtViewId = (TextView) itemLayoutView.findViewById(R.id.reply_id);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reply_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final QrpItem qrpItem = qrpItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        holder.txtViewId.setText(qrpItem.getQrp_Id());
        holder.txtViewContent.setText(qrpItem.getQrp_Content());
    }


    @Override
    public int getItemCount() {
        return (null != qrpItems ? qrpItems.size() : 0);
    }


}
