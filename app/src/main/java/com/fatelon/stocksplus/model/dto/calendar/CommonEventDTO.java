package com.fatelon.stocksplus.model.dto.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class CommonEventDTO {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("time")
    @Expose
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
