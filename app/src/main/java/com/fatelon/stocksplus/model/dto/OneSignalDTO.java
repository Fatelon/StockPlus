package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 23.01.2017.
 */

public class OneSignalDTO {

    @SerializedName("ticker")
    @Expose
    private String ticker = "";
    @SerializedName("company")
    @Expose
    private String company = "";
    @SerializedName("sector")
    @Expose
    private String sector = "";
    @SerializedName("industry")
    @Expose
    private String industry = "";
    @SerializedName("country")
    @Expose
    private String country = "";
    @SerializedName("market_cap")
    @Expose
    private String marketCap = "";
    @SerializedName("pe")
    @Expose
    private String pe = "";
    @SerializedName("price")
    @Expose
    private String price = "";
    @SerializedName("change")
    @Expose
    private String change = "";
    @SerializedName("volume")
    @Expose
    private String volume = "";

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

}
