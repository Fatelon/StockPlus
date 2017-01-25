package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 24.01.2017.
 */

public class IndexesItem {

    @SerializedName("instrument_name")
    @Expose
    private String instrumentName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("chage_num")
    @Expose
    private String chageNum;
    @SerializedName("change_percent")
    @Expose
    private String changePercent;

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChageNum() {
        return chageNum;
    }

    public void setChageNum(String chageNum) {
        this.chageNum = chageNum;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }
}
