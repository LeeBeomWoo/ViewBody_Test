package com.example.leebeomwoo.viewbody_final.Item;

/**
 * Created by LeeBeomWoo on 2017-03-27.
 */

    public class CardItem {
        private String ImageUrl;
        private String ld_Id;
        private String nickName;
        private String Category;
        private String Introduce;


        public CardItem(String Id, String ImageUrl, String nickName, String Category, String introduce){
            this.ld_Id = Id;
            this.ImageUrl = ImageUrl;
            this.nickName = nickName;
            this.Category = Category;
            this.Introduce = introduce;
        }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getLd_Id() {
        return ld_Id;
    }

    public void setLd_Id(String ld_Id) {
        this.ld_Id = ld_Id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

}
