package com.example.leebeomwoo.viewbody_final.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Item.LikeItem;
import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.example.leebeomwoo.viewbody_final.ItemViewActivity;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Response.ResponseLd;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowListRecyclerViewAdapter extends RecyclerView.Adapter<FollowListRecyclerViewAdapter.ViewHolder> implements Filterable{
    private List<ListDummyItem> ldItems = new ArrayList<>();
    private LikeItem lkItems;
    Context bContext;
    ResponseLd responseLd;
    private final static String TAG = "FollowViewAdapter";
    private final static String FURL = "<html><body><iframe width=\"960\" height=\"600\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    private final List<ListDummyItem> filteredUserList;
    private UserFilter userFilter;
    Drawable drawable;
    private String callClass, URL, change;
    Intent intent;
    int color;
    public FollowListRecyclerViewAdapter(Context context, List<ListDummyItem> ldItemList, int color){
        this.ldItems = ldItemList;
        this.bContext = context;
        this.filteredUserList = new ArrayList<>();
        this.color = color;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mView;
        public final TextView txtViewTitle, txtViewId;
        public final WebView imgView;
        public final Button likebutton, followbuttonn;
        public final ImageView categoryImage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mView = (CardView) itemLayoutView.findViewById(R.id.followCard);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.follow_Title);
            imgView = (WebView) itemLayoutView.findViewById(R.id.list_movie);
            txtViewId = (TextView) itemLayoutView.findViewById(R.id.follow_Id);
            likebutton = (Button) itemLayoutView.findViewById(R.id.follow_Like);
            followbuttonn = (Button) itemLayoutView.findViewById(R.id.follow_Btn);
            categoryImage = (ImageView) itemLayoutView.findViewById(R.id.followtitle_image);
            categoryImage.setBackgroundResource(R.drawable.follow_title_back);
            txtViewTitle.setBackgroundResource(R.drawable.follow_title_text_back);
            imgView.setFocusable(false);
            imgView.setWebViewClient(new WebViewClient());
            WebviewSet(imgView);

            if (Build.VERSION.SDK_INT >= 19) {
                imgView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            else {
                imgView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }

    private void WebviewSet (WebView view){
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_follow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ListDummyItem ldItem = ldItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.txtViewTitle.setText(ldItem.getLd_Title());
        viewHolder.txtViewId.setText(ldItem.getLd_Id());
        viewHolder.categoryImage.setImageDrawable(titlecategory(ldItem.getLd_Category()));
        intent = new Intent(bContext, ItemViewActivity.class);
        if(ldItem.getLd_Video() != null) {
            change = ldItem.getLd_Video().replace("https://youtu.be", CHANGE);
            URL = FURL + change + BURL;
            Log.d(TAG, URL);
            viewHolder.imgView.loadData(URL, "text/html", "utf-8");
        }
        viewHolder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //좋아요 클릭했을 시 계정이 있는 지 확인 후 계정별로 하나의 게시물에 한번만 좋아요가 눌려지게 하고 기존에 눌렀던 적이 있다면 해당 좋아요를 취소하는 걸로 코딩
                   Call<LikeItem> call = ConAdapter.getInstance().getResult_List("Like", ldItem.getLd_Num(), "UserId");
                   call.enqueue(new Callback<LikeItem>() {
                       @Override
                       public void onResponse(Call<LikeItem> call, Response<LikeItem> response) {
                           lkItems = response.body();
                           Log.d(TAG, "서버와의 연결이 잘됐어요~.");
                           Log.d("response", lkItems.toString());
                       }

                       @Override
                       public void onFailure(Call<LikeItem> call, Throwable t) {
                           Log.d(TAG, t.getMessage());
                       }
                   });
            }
        });
        viewHolder.followbuttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("itemUrl", ldItem.getLd_ImageUrl());
                intent.putExtra("tr_Id", ldItem.getLd_Id());
                intent.putExtra("section", ldItem.getLd_Section());
                intent.putExtra("page_num", ldItem.getLd_Num());
                intent.putExtra("video", ldItem.getLd_Video());
                intent.putExtra("fragment", 2);
                bContext.startActivity(intent);
                Log.d(TAG, ldItem.toString());
            }
        });
    }
    private void titlecategory(){
        titlecategory(0);
    }
    private Drawable titlecategory( int i){
        switch (i){
            case 0://근육
                drawable = bContext.getResources().getDrawable(R.drawable.logomain);
                break;
            case 1://골격
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 2://근지구력
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 3://근파워
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 4://머슬업
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 5://허리
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 6://상완
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 7://하완
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 8://복부
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 9://가슴
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 10://어깨
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 11://목
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 12://허벅지
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 13://종아리
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 14://엉덩이
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 15://상체
                drawable = bContext.getResources().getDrawable(R.drawable.upper);
                break;
            case 16://하체
                drawable = bContext.getResources().getDrawable(R.drawable.lower);
                break;
            case 17://몸통
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 18://심폐지구력
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
            case 19://미정
                drawable = bContext.getResources().getDrawable(R.drawable.foodlogo);
                break;
        }
        return drawable;
    }
    @Override
    public int getItemCount() {

        return (null != ldItems ? ldItems.size() : 0);
    }

    public void setLkItems(List<ListDummyItem> bdItems1) {
        ldItems.clear();
        this.ldItems = bdItems1;
    }
    // inner class to hold a reference to each item of RecyclerView
    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, ldItems);
        return userFilter;
    }


    private class UserFilter extends Filter {

        FollowListRecyclerViewAdapter adapter;

        private final List<ListDummyItem> originalList;

        private final List<ListDummyItem> filteredList;

        private UserFilter(FollowListRecyclerViewAdapter adapter, List<ListDummyItem> originalList) {
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

                for (final ListDummyItem user : originalList) {
                    if (user.getLd_Title().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<ListDummyItem>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
