package com.example.leebeomwoo.viewbody_final.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Item.LikeItem;
import com.example.leebeomwoo.viewbody_final.ItemViewActivity;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> implements Filterable {


    final List<LikeItem> likeItems;
    Context mContext;
    private final List<LikeItem> userList;
    private final List<LikeItem> filteredUserList;
    private UserFilter userFilter;
    int view_category;

    public MovieRecyclerViewAdapter(Context context, List<LikeItem> likeItemList){
        this.likeItems = likeItemList;
        this.mContext = context;
        this.userList = new ArrayList<>();
        this.filteredUserList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mView;
        public final TextView txtViewTitle;
        public final WebView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mView = (CardView) itemLayoutView.findViewById(R.id.cardView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.follow_Id);
            imgViewIcon = (WebView) itemLayoutView.findViewById(R.id.list_movie);
            imgViewIcon.setFocusable(false);
            imgViewIcon.getSettings().setJavaScriptEnabled(true);
            imgViewIcon.getSettings().setDomStorageEnabled(true);
            imgViewIcon.getSettings().setUseWideViewPort(true);
            imgViewIcon.getSettings().setLoadWithOverviewMode(true);
            if (Build.VERSION.SDK_INT >= 19) {
                imgViewIcon.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            else {
                imgViewIcon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_follow, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final LikeItem likeItem = likeItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ItemViewActivity.class);
                String viewurl = likeItem.getSource();
                // String tr_id = likeItem.get();
                view_category = likeItem.getFm_Section();
                int q= 3;
                //intent.putExtra("item_word", item_word);
                intent.putExtra("page_num", q);
                intent.putExtra("itemUrl", viewurl);
                // intent.putExtra("trId", tr_id);
                intent.putExtra("section", view_category);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {

        return (null != likeItems ? likeItems.size() : 0);
    }
    // inner class to hold a reference to each item of RecyclerView
    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, userList);
        return userFilter;
    }

    private static class UserFilter extends Filter {

        MovieRecyclerViewAdapter adapter;

        private final List<LikeItem> originalList;

        private final List<LikeItem> filteredList;

        private UserFilter(MovieRecyclerViewAdapter adapter, List<LikeItem> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final LikeItem user : originalList) {
                    if (user.getResult().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredUserList.clear();
            adapter.filteredUserList.addAll((ArrayList<LikeItem>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
