package com.example.leebeomwoo.viewbody_final.QnA;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 16. 9. 21.
 */
public class QrpItem {


    @SerializedName("qrp_Id")
    @Expose
    private String qrp_Id;


    @SerializedName("qrp_Content")
    @Expose
    private String qrp_Content;

    public String getQrp_Id() {
        return qrp_Id;
    }

    public void setQrp_Id(String qrp_Id) {
        this.qrp_Id = qrp_Id;
    }

    public String getQrp_Content() {
        return qrp_Content;
    }

    public void setQrp_Content(String qrp_Content) {
        this.qrp_Content = qrp_Content;
    }

}
