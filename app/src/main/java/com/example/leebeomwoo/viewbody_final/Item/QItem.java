package com.example.leebeomwoo.viewbody_final.Item;

/**
 * Created by LBW on 2016-08-04.
 */

public class QItem {
    private String q_Content;
    private String q_Id;
    private String q_Title;
    private String q_ConectCode;

    public QItem(String q_Id, String q_Title, String q_Content, String q_ConectCode){
        this.q_Id = q_Id;
        this.q_Title = q_Title;
        this.q_Content = q_Content;
        this.q_ConectCode = q_ConectCode;
    }


    public String getQ_Id(){
        return q_Id;
    }

    public void setQ_Id(String q_Id){
        this.q_Id = q_Id;
    }

    public String getQ_Title(){
        return q_Title;
    }

    public void setQ_Title(String q_Title){
        this.q_Title = q_Title;
    }

    public String getQ_Content(){
        return q_Content;
    }

    public void setQ_Content(String q_Content){
        this.q_Content = q_Content;
    }

    public String getQ_ConectCode(){
        return q_ConectCode;
    }

    public void setQ_ConectCode(String q_ConectCode){
        this.q_ConectCode = q_ConectCode;
    }

}

