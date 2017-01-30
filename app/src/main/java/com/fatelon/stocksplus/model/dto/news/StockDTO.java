package com.fatelon.stocksplus.model.dto.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fatelon on 30.01.2017.
 */

public class StockDTO {

    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("news_finviz")
    @Expose
    private List<NewsFinvizDTO> newsFinviz = null;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<NewsFinvizDTO> getNewsFinviz() {
        return newsFinviz;
    }

    public void setNewsFinviz(List<NewsFinvizDTO> newsFinviz) {
        this.newsFinviz = newsFinviz;
    }
}
