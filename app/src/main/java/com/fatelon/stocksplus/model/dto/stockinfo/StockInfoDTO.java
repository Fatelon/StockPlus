package com.fatelon.stocksplus.model.dto.stockinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 28.01.2017.
 */

public class StockInfoDTO {

    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("chage_num")
    @Expose
    private String chageNum;
    @SerializedName("change_percent")
    @Expose
    private String changePercent;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("parameters_yahoo")
    @Expose
    private ParametersYahooDTO parametersYahoo;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ParametersYahooDTO getParametersYahoo() {
        return parametersYahoo;
    }

    public void setParametersYahoo(ParametersYahooDTO parametersYahoo) {
        this.parametersYahoo = parametersYahoo;
    }

}
