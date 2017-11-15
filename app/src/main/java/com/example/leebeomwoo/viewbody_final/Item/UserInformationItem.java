package com.example.leebeomwoo.viewbody_final.Item;

/**
 * Created by LBW on 2016-08-04.
 */
public class UserInformationItem {
    private String Id;
    private String PassWord;
    private String Weight;
    private String Tall;
    private String Hope;


    public UserInformationItem(String Id, String PassWord, String Weight, String Tall, String Hope){
        this.Id = Id;
        this.PassWord = PassWord;
        this.Weight = Weight;
        this.Tall = Tall;
        this.Hope = Hope;
    }

    public String getId(){
        return Id;
    }

    public void setId(String Id){
        this.Id = Id;
    }

    public String getPassWord(){
        return PassWord;
    }

    public void setPassWord(String PassWord){
        this.PassWord = PassWord;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public String getTall() {
        return Tall;
    }

    public void setTall(String Tall) {
        this.Tall = Tall;
    }

    public String getHope() {
        return Hope;
    }

    public void setHope(String Hope) {
        this.Hope = Hope;
    }
}

