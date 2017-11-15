package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.QnA.QrpItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16. 9. 21.
 */

public class ResponseQrp {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("qrp_Item")
    @Expose
    private List<QrpItem> qrp_Item = new ArrayList<QrpItem>();

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

    public ResponseQrp withResult(String result) {
        this.result = result;
        return this;
    }

    /**
     *
     * @return
     * The fmItem
     */
    public List<QrpItem> getQrp_Item() {
        return qrp_Item;
    }

    /**
     *
     * @param qrp_Item
     * The qItem
     */
    public void setQrp_Item(List<QrpItem> qrp_Item) {
        this.qrp_Item = qrp_Item;
    }

    public ResponseQrp withQItem(List<QrpItem> qrp_Item) {
        this.qrp_Item = qrp_Item;
        return this;
    }
}
