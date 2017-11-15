package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LBW on 2016-06-14.
 */
public class ResponseLd {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("ldItem")
    @Expose
    public List<ListDummyItem> ldItem = new ArrayList<ListDummyItem>();

    /**
     *
     * @return
     * The liItem
     */
    public List<ListDummyItem> getLdItem() {
        return ldItem;
    }

    /**
     *
     * @param ldItem
     * The LowerItem
     */
    public void setLdItem(List<ListDummyItem> ldItem) {
        this.ldItem = ldItem;
    }

    public ResponseLd withLiItem(List<ListDummyItem> ldItem) {
        this.ldItem = ldItem;
        return this;
    }

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

    public ResponseLd withResult(String result) {
        this.result = result;
        return this;
    }

}
