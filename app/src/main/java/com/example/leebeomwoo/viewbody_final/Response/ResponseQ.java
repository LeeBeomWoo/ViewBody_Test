package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.Item.QItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LBW on 2016-06-14.
 */
public class ResponseQ {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("qItem")
    @Expose
    private List<QItem> qItem = new ArrayList<QItem>();

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

    public ResponseQ withResult(String result) {
        this.result = result;
        return this;
    }

    /**
     *
     * @return
     * The fmItem
     */
    public List<QItem> getqItem() {
        return qItem;
    }

    /**
     *
     * @param qItem
     * The qItem
     */
    public void setqItem(List<QItem> qItem) {
        this.qItem = qItem;
    }

    public ResponseQ withQItem(List<QItem> qItem) {
        this.qItem = qItem;
        return this;
    }

}
