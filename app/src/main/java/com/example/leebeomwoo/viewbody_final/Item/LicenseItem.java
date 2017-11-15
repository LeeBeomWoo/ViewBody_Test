package com.example.leebeomwoo.viewbody_final.Item;

/**
 * Created by Lee on 2017-07-03.
 */

public class LicenseItem {
    private String title;
    private String source;

    public LicenseItem(String title, String source) {
        this.title = title;
        this.source = source;
    }
    public void setTitle(String title) {
        this.title= title;
    }
    public String getTitle() {
        return title;
    }
    public void setSource(String source) {
        this.source= source;
    }
    public String getSource() {
        return source;
    }
}
