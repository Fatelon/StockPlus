package com.fatelon.stocksplus.model.dto.portfolio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 02.02.2017.
 */

public class PortfolioItemsDTO {

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
    @SerializedName("6")
    @Expose
    private String _6;
    @SerializedName("7")
    @Expose
    private String _7;
    @SerializedName("8")
    @Expose
    private String _8;
    @SerializedName("9")
    @Expose
    private String _9;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("portfolio_id")
    @Expose
    private String portfolioId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("cnt")
    @Expose
    private String cnt;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("user_price")
    @Expose
    private String userPrice;
    @SerializedName("item_status")
    @Expose
    private String itemStatus;
    @SerializedName("dc")
    @Expose
    private String dc;
    @SerializedName("sd")
    @Expose
    private String sd;
    @SerializedName("today_price")
    @Expose
    private Object todayPrice;
    @SerializedName("today_profitlose_num")
    @Expose
    private Object todayProfitloseNum;
    @SerializedName("today_profitlose_percent")
    @Expose
    private Object todayProfitlosePercent;
    @SerializedName("total_profitlose_num")
    @Expose
    private Integer totalProfitloseNum;
    @SerializedName("total_profitlose_percent")
    @Expose
    private String totalProfitlosePercent;
    @SerializedName("totalCost")
    @Expose
    private String totalCost;
    @SerializedName("todayPnLNum")
    @Expose
    private Integer todayPnLNum;
    @SerializedName("totalPnLPercent")
    @Expose
    private Integer totalPnLPercent;

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

    public String get6() {
        return _6;
    }

    public void set6(String _6) {
        this._6 = _6;
    }

    public String get7() {
        return _7;
    }

    public void set7(String _7) {
        this._7 = _7;
    }

    public String get8() {
        return _8;
    }

    public void set8(String _8) {
        this._8 = _8;
    }

    public String get9() {
        return _9;
    }

    public void set9(String _9) {
        this._9 = _9;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(String userPrice) {
        this.userPrice = userPrice;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
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

    public Object getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(Object todayPrice) {
        this.todayPrice = todayPrice;
    }

    public Object getTodayProfitloseNum() {
        return todayProfitloseNum;
    }

    public void setTodayProfitloseNum(Object todayProfitloseNum) {
        this.todayProfitloseNum = todayProfitloseNum;
    }

    public Object getTodayProfitlosePercent() {
        return todayProfitlosePercent;
    }

    public void setTodayProfitlosePercent(Object todayProfitlosePercent) {
        this.todayProfitlosePercent = todayProfitlosePercent;
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

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTodayPnLNum() {
        return todayPnLNum;
    }

    public void setTodayPnLNum(Integer todayPnLNum) {
        this.todayPnLNum = todayPnLNum;
    }

    public Integer getTotalPnLPercent() {
        return totalPnLPercent;
    }

    public void setTotalPnLPercent(Integer totalPnLPercent) {
        this.totalPnLPercent = totalPnLPercent;
    }
}
