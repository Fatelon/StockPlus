package com.fatelon.stocksplus.model.dto.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class DividentDTO {

    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("divident")
    @Expose
    private String divident;
    @SerializedName("Anouncement Date")
    @Expose
    private String anouncementDate;
    @SerializedName("Record Date")
    @Expose
    private String recordDate;
    @SerializedName("Ex-Date")
    @Expose
    private String exDate;
    @SerializedName("Pay Date")
    @Expose
    private String payDate;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getDivident() {
        return divident;
    }

    public void setDivident(String divident) {
        this.divident = divident;
    }

    public String getAnouncementDate() {
        return anouncementDate;
    }

    public void setAnouncementDate(String anouncementDate) {
        this.anouncementDate = anouncementDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
