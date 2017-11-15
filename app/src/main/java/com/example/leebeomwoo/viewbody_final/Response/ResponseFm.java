package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.Item.LikeItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LBW on 2016-06-14.
 */
public class ResponseFm {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("likeItem")
    @Expose
    private List<LikeItem> likeItem = new ArrayList<LikeItem>();

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    public ResponseFm withResult(String result) {
        this.result = result;
        return this;
    }

    /**
     *
     * @return
     * The likeItem
     */
    public List<LikeItem> getLikeItem() {
        return likeItem;
    }

    /**
     *
     * @param likeItem
     * The LikeItem
     */
    public void setLikeItem(List<LikeItem> likeItem) {
        this.likeItem = likeItem;
    }

    public ResponseFm withFmItem(List<LikeItem> likeItem) {
        this.likeItem = likeItem;
        return this;
    }

}
