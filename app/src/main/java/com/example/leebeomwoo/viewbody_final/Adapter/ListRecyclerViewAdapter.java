package com.example.leebeomwoo.viewbody_final.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.leebeomwoo.viewbody_final.Support.RecyclerviewClickEvent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> implements Filterable {
    private List<ListDummyItem> ldItems = new ArrayList<>();
    private LikeItem lkItems;
    private Context bContext;
    ResponseLd responseLd;
    private final static String TAG = "ListRecyclerViewAdapter";
    private final static String FURL = "<html><body><iframe width=\"1080\" height=\"720\" src=\"";
    private final static String BURL = "\" frameborder=\"0\" allowfullscreen></iframe></html></body>";
    private final static String CHANGE = "https://www.youtube.com/embed";
    private final List<ListDummyItem> filteredUserList;
    private UserFilter userFilter;
    private RecyclerviewClickEvent clickEvent = new RecyclerviewClickEvent();
    private Intent intent;
    private int color, rusult, result, fin;
    public ListRecyclerViewAdapter(Context context, List<ListDummyItem> ldItemList, int color){
        this.ldItems = ldItemList;
        this.bContext = context;
        this.filteredUserList = new ArrayList<>();
        this.color = color;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CardView mView;
        final TextView txtViewTitle, txtViewId, video_title_1, video_title_2, video_title_3;
        final WebView imgViewFace, videoView_1, videoView_2, videoView_3;
        public final Button button;
        //final HelpWebView imgViewIcon;
        final ImageView categoryImage, imgViewIcon;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mView = itemLayoutView.findViewById(R.id.cardView);
            txtViewTitle = itemLayoutView.findViewById(R.id.detile_Title);
            imgViewIcon = itemLayoutView.findViewById(R.id.detile_Image);
            video_title_1 = itemLayoutView.findViewById(R.id.video_title_1);
            video_title_2 = itemLayoutView.findViewById(R.id.video_title_2);
            video_title_3 = itemLayoutView.findViewById(R.id.video_title_3);
            imgViewFace = itemLayoutView.findViewById(R.id.detile_face);
            videoView_1 = itemLayoutView.findViewById(R.id.video_view_1);
            videoView_2 = itemLayoutView.findViewById(R.id.video_view_2);
            txtViewId = itemLayoutView.findViewById(R.id.detile_Id);
            button = itemLayoutView.findViewById(R.id.like_btn);
            videoView_3 = itemLayoutView.findViewById(R.id.video_view_3);
            categoryImage = itemLayoutView.findViewById(R.id.itemtitle_image);
            categoryImage.setBackgroundResource(color);
            switch (color){
                case R.drawable.body_title_back:
                    txtViewTitle.setBackgroundResource(R.drawable.body_title_text_back);
                    break;
                case R.drawable.follow_title_back:
                    txtViewTitle.setBackgroundResource(R.drawable.follow_title_text_back);
                    break;
                case R.drawable.food_title_back:
                    txtViewTitle.setBackgroundResource(R.drawable.food_title_text_back);
                    break;
                case R.drawable.new_title_back:
                    txtViewTitle.setBackgroundResource(R.drawable.new_title_text_back);
                    break;
                case R.drawable.writer_title_back:
                    txtViewTitle.setBackgroundResource(R.drawable.writer_title_text_back);
                    break;
            }
            WebviewSet(imgViewFace);
           // WebviewSet(imgViewIcon);
            WebviewSet(videoView_1);
            WebviewSet(videoView_2);
            WebviewSet(videoView_3);
            //imgViewIcon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            //imgViewFace.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            imgViewIcon.setOnClickListener(this);
            imgViewFace.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("onClick", view.toString());
            if(view.getId() == button.getId()){
                Call<LikeItem> call = ConAdapter.getInstance().getResult_List("Like", getItem(getLayoutPosition()).getLd_Num(), "UserId");
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
            }else  if (view.getId() == imgViewIcon.getId()) {
                clickEvent.Click(ldItems.get(getLayoutPosition()), bContext);
            }else if (view.getId() == imgViewFace.getId()) {
                intent.putExtra("itemUrl", "trainer");
                intent.putExtra("tr_Id", getItem(getLayoutPosition()).getLd_Id());
                intent.putExtra("section", getItem(getLayoutPosition()).getLd_Section());
                //intent_2.putExtra("item_word", item_word);
                intent.putExtra("fragment", 1);
                bContext.startActivity(intent);
            }
        }
    }
    private void WebviewSet(WebView view){
        view.setFocusable(false);
        view.setWebViewClient(new WebViewClient());
        /** webview.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
         장점 이건 설정하면 웹뷰를 계속 띄워도 뻗지 않는다 .
         페이지 전환시 껌뻑임이 없다
         단점은 웹뷰 내용을 스크롤 할 때 느리게 스크롤 되는 단점이있다
         나는 계속 보니 어지러워서 토나올것 같더라

         webview.setLayerType(View.LAYER_TYPE_HARDWARE,null);
         장점은 스크롤이 괭장히 부드럽게 된다 .
         단점은 웹뷰 한 .. 10개 정도 .. 액티비티당 2개씩이라서 한 5개 정도 띄우면 뻗는다 버퍼에러 난다
         그리고 페이지 불러올때 껌뻑인다 .. 퍼즐 맞추듯이 맞춰 진다
         이거 할때는 android:hardwareAccelerated="true" 이것도 메니페스트 <application에 추가하자

         출처: http://writefoot.tistory.com/entry/웹뷰-껌뻑임-현상 [발로쓰는 블로그]
         */
        if(view.getId() != R.id.detile_Image && view.getId() != R.id.detile_face)
        if (Build.VERSION.SDK_INT >= 21) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }
        else {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ListDummyItem ldItem = ldItems.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        int height = bContext.getResources().getDisplayMetrics().heightPixels;
        int width = bContext.getResources().getDisplayMetrics().widthPixels;
        viewHolder.txtViewTitle.setText(ldItem.getLd_Title());
        //viewHolder.imgViewIcon.loadUrl(ConAdapter.SERVER_URL + ldItem.getLd_ImageUrl());
        String result = ldItem.getLd_ImageUrl().replaceAll("\\/","/");
        Picasso.with(bContext).load(ConAdapter.SERVER_URL + result).resize(width, height).into(viewHolder.imgViewIcon);
        //Picasso.with(bContext).load(ConAdapter.SERVER_URL + result).into(viewHolder.imgViewIcon);
        viewHolder.txtViewId.setText(ldItem.getLd_Id());
       // viewHolder.categoryImage.setImageResource(titlecategory(ldItem.getLd_Category()));
        Picasso.with(bContext).load(titlecategory(ldItem.getLd_Category())).fit().into(viewHolder.categoryImage);
        intent = new Intent(bContext, ItemViewActivity.class);
        if(ldItem.getLd_Video() != null) {
            String[] animalsArray = ldItem.getLd_Video().split(",");
            for(int i = 0; i < animalsArray.length; i++) {
                Log.d(TAG + "animalsArray_" + i, animalsArray[i]);
            }
            switch (animalsArray.length){
                case 2:
                    viewHolder.video_title_1.setVisibility(View.VISIBLE);
                    viewHolder.video_title_1.setText(animalsArray[0]);
                    String change = animalsArray[1].replace("https://youtu.be", CHANGE);
                    String URL1 = FURL + change + BURL;
                    Log.d(TAG, URL1);
                    viewHolder.videoView_1.setVisibility(View.VISIBLE);
                    viewHolder.videoView_1.loadData(URL1, "text/html", "utf-8");
                    break;
                case 4:
                    viewHolder.video_title_1.setVisibility(View.VISIBLE);
                    viewHolder.video_title_1.setText(animalsArray[0]);
                    change = animalsArray[1].replace("https://youtu.be", CHANGE);
                    URL1 = FURL + change + BURL;
                    Log.d(TAG, URL1);
                    viewHolder.videoView_1.setVisibility(View.VISIBLE);
                    viewHolder.videoView_1.loadData(URL1, "text/html", "utf-8");

                    viewHolder.video_title_2.setVisibility(View.VISIBLE);
                    viewHolder.video_title_2.setText(animalsArray[2]);
                    change = animalsArray[3].replace("https://youtu.be", CHANGE);
                    String URL2 = FURL + change + BURL;
                    Log.d(TAG, URL2);
                    viewHolder.videoView_2.setVisibility(View.VISIBLE);
                    viewHolder.videoView_2.loadData(URL2, "text/html", "utf-8");
                    break;
                case 6:
                    viewHolder.video_title_1.setVisibility(View.VISIBLE);
                    viewHolder.video_title_1.setText(animalsArray[0]);
                    change = animalsArray[1].replace("https://youtu.be", CHANGE);
                    URL1 = FURL + change + BURL;
                    Log.d(TAG, URL1);
                    viewHolder.videoView_1.setVisibility(View.VISIBLE);
                    viewHolder.videoView_1.loadData(URL1, "text/html", "utf-8");

                    viewHolder.video_title_2.setVisibility(View.VISIBLE);
                    viewHolder.video_title_2.setText(animalsArray[2]);
                    change = animalsArray[3].replace("https://youtu.be", CHANGE);
                    URL2 = FURL + change + BURL;
                    Log.d(TAG, URL2);
                    viewHolder.videoView_2.setVisibility(View.VISIBLE);
                    viewHolder.videoView_2.loadData(URL2, "text/html", "utf-8");

                    viewHolder.video_title_3.setVisibility(View.VISIBLE);
                    viewHolder.video_title_3.setText(animalsArray[4]);
                    change = animalsArray[5].replace("https://youtu.be", CHANGE);
                    String URL3 = FURL + change + BURL;
                    Log.d(TAG, URL3);
                    viewHolder.videoView_3.setVisibility(View.VISIBLE);
                    viewHolder.videoView_3.loadData(URL3, "text/html", "utf-8");
                    break;
            }

        }

    }
    private int titlecategory(int i){
        String s =String.valueOf(i);
        Log.d("카테고리 분류표", s);
        String[] sa = s.split("");
        Log.d("카테고리0", sa[1]);
        Log.d("카테고리1", sa[2]);
        Log.d("카테고리2", sa[3]);
        Log.d("카테고리3", sa[4]);
        if(Objects.equals(sa[1], "1")){//상체
            if(Objects.equals(sa[2], "0")){//전후면 상관없음
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upper;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                    }
                    result = rusult;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "1")){ // 전면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upper;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "2")) { // 후면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upper;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result =rusult;
                        Log.d("drawble rusult", String.valueOf(rusult));
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
                Log.d("drawble rusult", String.valueOf(rusult));
                Log.d("drawble result", String.valueOf(result));
                Log.d("drawble fin",String.valueOf(fin));
            }
        }else {//하체
            if(Objects.equals(sa[2], "0")){//전후면 상관없음
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.lower;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "1")){ // 전면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.lower;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.lower;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.front_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.front_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.front_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.front_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.front_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.front_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.burst;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.stormach;
                                break;
                        }
                        result = rusult;
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
            }else if(Objects.equals(sa[2], "2")){ // 후면
                switch (sa[3]) {
                    case "0": // 상관없음
                        result = R.drawable.upbar;
                        break;
                    case "1": // 골격
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result = rusult;
                        break;
                    case "2": // 근육
                        switch (sa[4]) {
                            case "0": // 상관없음
                                rusult = R.drawable.upper;
                                break;
                            case "1": // 상완
                                rusult = R.drawable.back_upper_arm;
                                break;
                            case "2": // 하완
                                rusult = R.drawable.back_lower_arm;
                                break;
                            case "3": // 어깨
                                rusult = R.drawable.back_shoulder;
                                break;
                            case "4": // 목
                                rusult = R.drawable.back_neck;
                                break;
                            case "5": // 허벅지
                                rusult = R.drawable.back_upper_leg;
                                break;
                            case "6": // 종아리
                                rusult = R.drawable.back_lower_leg;
                                break;
                            case "7": // 가슴
                                rusult = R.drawable.back_body;
                                break;
                            case "8": // 엉덩이
                                rusult = R.drawable.hip;
                                break;
                            case "9": // 복부
                                rusult = R.drawable.back_body;
                                break;
                        }
                        result = rusult;
                        break;
                    case "3": // 근력
                        result = R.drawable.muscleup;
                        break;
                    case "4": // 근지구력
                        result = R.drawable.musclelong;
                        break;
                    case "5": // 근파워
                        result = R.drawable.musclepower;
                        break;
                    case "6": // 심폐지구력
                        result = R.drawable.cardiopulmonary_endurance;
                        break;
                    case "7": // 유연성
                        result = R.drawable.stretching;
                        break;
                }
                fin = result;
            }
        }
        return fin;
    }
    private ListDummyItem getItem(int position){
        return ldItems.get(position);
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

        ListRecyclerViewAdapter adapter;

        private final List<ListDummyItem> originalList;

        private final List<ListDummyItem> filteredList;

        private UserFilter(ListRecyclerViewAdapter adapter, List<ListDummyItem> originalList) {
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
