package com.example.leebeomwoo.viewbody_final.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.leebeomwoo.viewbody_final.Item.QItem;
import com.example.leebeomwoo.viewbody_final.QnaActivity;
import com.example.leebeomwoo.viewbody_final.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyQnARecyclerViewAdapter extends RecyclerView.Adapter<MyQnARecyclerViewAdapter.ViewHolder> implements Filterable {

    final List<QItem> qItems;
    Context qContext;
    private final List<QItem> userList;
    private final List<QItem> filteredUserList;
    private MyQnARecyclerViewAdapter.UserFilter userFilter;

    public MyQnARecyclerViewAdapter(Context context, List<QItem> qItemList){
        this.qItems = qItemList;
        this.qContext = context;
        this.userList = new ArrayList<>();
        this.filteredUserList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mView;
        public final TextView txtViewTitle;
        public final TextView txtViewContent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mView = (CardView) itemLayoutView.findViewById(R.id.qna_Card);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.qna_title);
            txtViewContent = (TextView) itemLayoutView.findViewById(R.id.qna_content);
        }
    }
    @Override
    public MyQnARecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_qna, parent, false);
        return new MyQnARecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyQnARecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        final QItem qItem = qItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.txtViewTitle.setText(qItem.getQ_Title());
        viewHolder.txtViewContent.setText(qItem.getQ_Content());
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, QnaActivity.class);
                String q = qItem.getQ_ConectCode();
                intent.putExtra("pagenum", q);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {

        return (null != qItems ? qItems.size() : 0);
    }
    // inner class to hold a reference to each item of RecyclerView
    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new MyQnARecyclerViewAdapter.UserFilter(this, userList);
        return userFilter;
    }

    private static class UserFilter extends Filter {

        MyQnARecyclerViewAdapter adapter;

        private final List<QItem> originalList;

        private final List<QItem> filteredList;

        private UserFilter(MyQnARecyclerViewAdapter adapter, List<QItem> originalList) {
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

                for (final QItem user : originalList) {
                    if (user.getQ_Title().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<QItem>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
