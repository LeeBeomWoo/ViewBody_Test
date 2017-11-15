package com.example.leebeomwoo.viewbody_final.ItemGroup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Item.CardItem;
import com.example.leebeomwoo.viewbody_final.Item.WriterItem;
import com.example.leebeomwoo.viewbody_final.R;

import java.util.List;

/**
 * Created by LeeBW on 2016-07-14.
 */
public class Trainer_award_Adapter extends RecyclerView.Adapter<Trainer_award_Adapter.ViewHolder>{
    private List<WriterItem> awards;
    private Context mContext;

    public Trainer_award_Adapter(Context context, List<WriterItem> awardList){
        this.awards = awardList;
        this.mContext = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtViewAssociation;
        public final TextView txtViewName;
        public final ImageView txtViewCheck;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewAssociation = (TextView) itemLayoutView.findViewById(R.id.association_txtB);
            txtViewName = (TextView) itemLayoutView.findViewById(R.id.name_txtB);
            txtViewCheck = (ImageView) itemLayoutView.findViewById(R.id.check_B);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainer_source, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final WriterItem award = awards.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.txtViewAssociation.setText(award.getAssociation());
        viewHolder.txtViewName.setText(award.getName());
        if(award.getCheck() != "ok") {
            viewHolder.txtViewCheck.setImageResource(R.drawable.checked);
        } else {
            viewHolder.txtViewCheck.setImageResource(R.drawable.arrows1);
        }
    }
    @Override
    public int getItemCount() {

        return (null != awards ? awards.size() : 0);
    }
}
