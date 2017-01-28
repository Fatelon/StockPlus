package com.fatelon.stocksplus.model.dto.stockinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 28.01.2017.
 */

public class ParametersYahooDTO {

    @SerializedName("Previous Close ")
    @Expose
    private String previousClose;
    @SerializedName("Open ")
    @Expose
    private String open;
    @SerializedName("Bid ")
    @Expose
    private String bid;
    @SerializedName("Ask ")
    @Expose
    private String ask;
    @SerializedName("Day's Range ")
    @Expose
    private String daySRange;
    @SerializedName("52 Week Range ")
    @Expose
    private String _52WeekRange;
    @SerializedName("Volume ")
    @Expose
    private String volume;
    @SerializedName("Avg. Volume ")
    @Expose
    private String avgVolume;
    @SerializedName("Market Cap ")
    @Expose
    private String marketCap;
    @SerializedName("Beta ")
    @Expose
    private String beta;
    @SerializedName("PE Ratio (TTM) ")
    @Expose
    private String pERatioTTM;
    @SerializedName("EPS (TTM) ")
    @Expose
    private String ePSTTM;
    @SerializedName("Earnings Date ")
    @Expose
    private String earningsDate;
    @SerializedName("Dividend & Yield ")
    @Expose
    private String dividendYield;
    @SerializedName("Ex-Dividend Date ")
    @Expose
    private String exDividendDate;
    @SerializedName("1y Target Est ")
    @Expose
    private String _1yTargetEst;

    public String getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getDaySRange() {
        return daySRange;
    }

    public void setDaySRange(String daySRange) {
        this.daySRange = daySRange;
    }

    public String get52WeekRange() {
        return _52WeekRange;
    }

    public void set52WeekRange(String _52WeekRange) {
        this._52WeekRange = _52WeekRange;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAvgVolume() {
        return avgVolume;
    }

    public void setAvgVolume(String avgVolume) {
        this.avgVolume = avgVolume;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getPERatioTTM() {
        return pERatioTTM;
    }

    public void setPERatioTTM(String pERatioTTM) {
        this.pERatioTTM = pERatioTTM;
    }

    public String getEPSTTM() {
        return ePSTTM;
    }

    public void setEPSTTM(String ePSTTM) {
        this.ePSTTM = ePSTTM;
    }

    public String getEarningsDate() {
        return earningsDate;
    }

    public void setEarningsDate(String earningsDate) {
        this.earningsDate = earningsDate;
    }

    public String getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(String dividendYield) {
        this.dividendYield = dividendYield;
    }

    public String getExDividendDate() {
        return exDividendDate;
    }

    public void setExDividendDate(String exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    public String get1yTargetEst() {
        return _1yTargetEst;
    }

    public void set1yTargetEst(String _1yTargetEst) {
        this._1yTargetEst = _1yTargetEst;
    }
}
