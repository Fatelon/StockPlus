package com.fatelon.stocksplus.model.dto.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 30.01.2017.
 */

public class NewsFinvizDTO {

    @SerializedName("dt")
    @Expose
    private String dt = "";
    @SerializedName("news_title")
    @Expose
    private String newsTitle = "";
    @SerializedName("news_url")
    @Expose
    private String newsUrl = "";

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

}
