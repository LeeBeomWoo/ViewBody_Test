package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.Item.ListDummyItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16. 8. 25.
 */

public class ResponseCbd {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("cbdItem")
    @Expose
    public List<ListDummyItem> cbdItem = new ArrayList<ListDummyItem>();

    /**
     *2
     * @return
     * The liItem
     */
    public List<ListDummyItem> getCbdItem() {
        return cbdItem;
    }

    /**
     *
     * @param cbdItem
     * The LowerItem
     */
    public void setCbdItem(List<ListDummyItem> cbdItem) {
        this.cbdItem = cbdItem;
    }

    public ResponseCbd withLiItem(List<ListDummyItem> cbdItem) {
        this.cbdItem = cbdItem;
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

    public ResponseCbd withResult(String result) {
        this.result = result;
        return this;
    }
}
