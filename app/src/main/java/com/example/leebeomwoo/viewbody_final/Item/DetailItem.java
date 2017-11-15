package com.example.leebeomwoo.viewbody_final.Item;

/**
 * Created by LeeBeomWoo on 2017-04-05.
 */

public class DetailItem {
    private String detail_ImageUrl;
    private String detail_Content;
    private String detail_Id;
    private String detail_Title;
    private int detail_Category;

    public DetailItem(String detail_Id, String detail_Title, String detail_Content, String detail_ImageUrl, int detail_Category){
        this.detail_Id = detail_Id;
        this.detail_Title = detail_Title;
        this.detail_Content = detail_Content;
        this.detail_ImageUrl = detail_ImageUrl;
        this.detail_Category = detail_Category;
    }

    public String getDetail_ImageUrl() {
        return detail_ImageUrl;
    }

    public void setDetail_ImageUrl(String detail_ImageUrl) {
        this.detail_ImageUrl = detail_ImageUrl;
    }

    public String getDetail_Content() {
        return detail_Content;
    }

    public void setDetail_Content(String detail_Content) {
        this.detail_Content = detail_Content;
    }

    public String getDetail_Id() {
        return detail_Id;
    }

    public void setDetail_Id(String detail_Id) {
        this.detail_Id = detail_Id;
    }

    public String getDetail_Title() {
        return detail_Title;
    }

    public void setDetail_Title(String detail_Title) {
        this.detail_Title = detail_Title;
    }

    public int getDetail_Category() {
        return detail_Category;
    }

    public void setDetail_Category(int detail_Category) {
        this.detail_Category = detail_Category;
    }
}
