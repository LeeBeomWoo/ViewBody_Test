package com.example.leebeomwoo.viewbody_final.Item;

import java.util.List;

/**
 * Created by LeeBeomWoo on 2017-05-29.
 */

public class WriterItem {
    private String check;
    private String association;
    private String name;
    public WriterItem(String check, String association, String name){
        this.check = check;
        this.association = association;
        this.name = name;
    }
    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
