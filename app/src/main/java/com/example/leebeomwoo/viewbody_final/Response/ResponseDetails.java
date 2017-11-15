package com.example.leebeomwoo.viewbody_final.Response;

import com.example.leebeomwoo.viewbody_final.Item.DetailItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeeBeomWoo on 2017-04-05.
 */

public class ResponseDetails {
        @SerializedName("result")
    @Expose
    private String result;
        @SerializedName("ldItem")
        @Expose
        public List<DetailItem> ldItem = new ArrayList<DetailItem>();

        /**
         *
         * @return
         * The liItem
         */
        public List<DetailItem> getDtailItem() {
            return ldItem;
        }

        /**
         *
         * @param ldItem
         * The LowerItem
         */
        public void setBdItem(List<DetailItem> ldItem) {
            this.ldItem = ldItem;
        }

        public ResponseDetails withLiItem(List<DetailItem> ldItem) {
            this.ldItem = ldItem;
            return this;
        }

        /**
         *
         * @return
         * The result
         */
        public String getResult() {
            return result;
        }

        /**
         *
         * @param result
         * The result
         */
        public void setResult(String result) {
            this.result = result;
        }

        public ResponseDetails withResult(String result) {
            this.result = result;
            return this;
        }
}
