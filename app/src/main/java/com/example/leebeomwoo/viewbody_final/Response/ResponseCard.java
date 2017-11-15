package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.Item.CardItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LBW on 2016-06-14.
 */
public class ResponseCard {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("cdbItem")
    @Expose
    public List<CardItem> cdbItem = new ArrayList<>();

    @SerializedName("cdfItem")
    @Expose
    public List<CardItem> cdfItem = new ArrayList<>();
    /**
     *
     * @return
     * The liItem
     */
    public List<CardItem> getbCardItem() {
        return cdbItem;
    }

    /**
     *
     * @param cdbItem
     * The LowerItem
     */
    public void setbCardItem(List<CardItem> cdbItem) {
        this.cdbItem = cdbItem;
    }

    public ResponseCard withbLiItem(List<CardItem> cdbItem) {
        this.cdbItem = cdbItem;
        return this;
    }
    /**
     *
     * @param cdfItem
     * The LowerItem
     */
    public void setfCardItem(List<CardItem> cdfItem) {
        this.cdfItem = cdfItem;
    }

    public ResponseCard withfLiItem(List<CardItem> cdfItem) {
        this.cdbItem = cdbItem;
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

    public ResponseCard withResult(String result) {
        this.result = result;
        return this;
    }

}
