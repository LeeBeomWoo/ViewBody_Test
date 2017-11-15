package com.example.leebeomwoo.viewbody_final.Item;


/**
 * Created by LBW on 2016-05-24.
 */

/** public String getBd_Category(){
 return bd_Category;
 }
 //  private String bd_word;
 //  this.bd_word = bd_word;
 public void setBd_Category(String bd_Category){
 this.bd_Category = bd_Category;
 }
 */
    public class LikeItem {

    private String source;
    private String table;
    private String result;
    private int count;

    public LikeItem(String result, String table, String source, int count){
        this.result = result;
        this.table = table;
        this.source = source;
        this.count = count;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getTable(){
        return table;
    }

    public void setTable(String table){
        this.table = table;
    }

    public int getFm_Section(){
        return count;
    }

    public void setFm_Section(int fm_section){
        this.count = fm_section;
    }
}


