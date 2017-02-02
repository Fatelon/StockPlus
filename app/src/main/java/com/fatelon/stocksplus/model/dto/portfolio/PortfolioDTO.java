package com.fatelon.stocksplus.model.dto.portfolio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Fatelon on 02.02.2017.
 */

public class PortfolioDTO {


    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("3")
    @Expose
    private String _3;
    @SerializedName("4")
    @Expose
    private String _4;
    @SerializedName("5")
    @Expose
    private String _5;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("dc")
    @Expose
    private String dc;
    @SerializedName("sd")
    @Expose
    private String sd;
    @SerializedName("items")
    @Expose
    private Map<String, PortfolioItemsDTO> items;
    @SerializedName("total_profitlose_num")
    @Expose
    private Integer totalProfitloseNum;
    @SerializedName("total_profitlose_percent")
    @Expose
    private String totalProfitlosePercent;
    @SerializedName("totalCost")
    @Expose
    private Integer totalCost;
    @SerializedName("todayPnL")
    @Expose
    private Integer todayPnL;
    @SerializedName("totalPnL")
    @Expose
    private Integer totalPnL;
    @SerializedName("cashValue")
    @Expose
    private Integer cashValue;
    @SerializedName("stockValue")
    @Expose
    private Integer stockValue;

    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

    public String get3() {
        return _3;
    }

    public void set3(String _3) {
        this._3 = _3;
    }

    public String get4() {
        return _4;
    }

    public void set4(String _4) {
        this._4 = _4;
    }

    public String get5() {
        return _5;
    }

    public void set5(String _5) {
        this._5 = _5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public Map<String, PortfolioItemsDTO> getItems() {
        return items;
    }

    public void setItems(Map<String, PortfolioItemsDTO> items) {
        this.items = items;
    }

    public Integer getTotalProfitloseNum() {
        return totalProfitloseNum;
    }

    public void setTotalProfitloseNum(Integer totalProfitloseNum) {
        this.totalProfitloseNum = totalProfitloseNum;
    }

    public String getTotalProfitlosePercent() {
        return totalProfitlosePercent;
    }

    public void setTotalProfitlosePercent(String totalProfitlosePercent) {
        this.totalProfitlosePercent = totalProfitlosePercent;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTodayPnL() {
        return todayPnL;
    }

    public void setTodayPnL(Integer todayPnL) {
        this.todayPnL = todayPnL;
    }

    public Integer getTotalPnL() {
        return totalPnL;
    }

    public void setTotalPnL(Integer totalPnL) {
        this.totalPnL = totalPnL;
    }

    public Integer getCashValue() {
        return cashValue;
    }

    public void setCashValue(Integer cashValue) {
        this.cashValue = cashValue;
    }

    public Integer getStockValue() {
        return stockValue;
    }

    public void setStockValue(Integer stockValue) {
        this.stockValue = stockValue;
    }
}
