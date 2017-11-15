package com.example.leebeomwoo.viewbody_final.QnA;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 16. 9. 21.
 */
public class QwItem {

    @SerializedName("pagenum")
    @Expose
    String pagenum;

    @SerializedName("result")
    @Expose
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }
}
