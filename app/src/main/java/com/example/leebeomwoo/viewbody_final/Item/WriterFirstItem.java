package com.example.leebeomwoo.viewbody_final.Item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LeeBeomWoo on 2017-05-29.
 */

public class WriterFirstItem {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("ImageUrl")
    @Expose
    private String ImageUrl;
    @SerializedName("association")
    @Expose
    private String association;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Introduce")
    @Expose
    private String Introduce;
    @SerializedName("call")
    @Expose
    private String call;
    @SerializedName("awList")
    @Expose
    private List<WriterItem> awList;
    @SerializedName("liList")
    @Expose
    private List<WriterItem> liList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public List<WriterItem> getAwList() {
        return awList;
    }

    public void setAwList(List<WriterItem> awList) {
        this.awList = awList;
    }

    public List<WriterItem> getLiList() {
        return liList;
    }

    public void setLiList(List<WriterItem> liList) {
        this.liList = liList;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public WriterFirstItem(String ImageUrl, String association, String nickname, String email, String Introduce, String call, List<WriterItem> awList, List<WriterItem> liList){
        this.ImageUrl = ImageUrl;
        this.association = association;
        this.nickname = nickname;
        this.email = email;
        this.Introduce = Introduce;
        this.call = call;

        this.awList = awList;
        this.liList = liList;
    }
}
