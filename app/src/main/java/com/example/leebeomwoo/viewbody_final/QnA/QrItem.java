package com.example.leebeomwoo.viewbody_final.QnA;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16. 9. 21.
 */
public class QrItem {

    @SerializedName("q_Id")
    @Expose
    private String q_Id;

    @SerializedName("q_Title")
    @Expose
    private String q_Title;

    @SerializedName("q_Content")
    @Expose
    private String q_Content;

    @SerializedName("q_PageNum")
    @Expose
    private String q_PageNum;

    @SerializedName("qrp_Item")
    @Expose
    public List<QrpItem> qrp_Item = new ArrayList<QrpItem>();


    public String getQ_Title() {
        return q_Title;
    }

    public void setQ_Title(String q_Title) {
        this.q_Title = q_Title;
    }

    public String getQ_Content() {
        return q_Content;
    }

    public void setQ_Content(String q_Content) {
        this.q_Content = q_Content;
    }

    public String getQ_PageNum() {
        return q_PageNum;
    }

    public void setQ_PageNum(String q_PageNum) {
        this.q_PageNum = q_PageNum;
    }

    public String getQ_Id() {
        return q_Id;
    }

    public void setQ_Id(String q_Id) {
        this.q_Id = q_Id;
    }

    public List<QrpItem> getQrp_Item() {
        return qrp_Item;
    }

    public void setQrp_Item(List<QrpItem> qrp_Item) {
        this.qrp_Item = qrp_Item;
    }
}
