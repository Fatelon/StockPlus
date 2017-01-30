package com.fatelon.stocksplus.model.dto.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 30.01.2017.
 */

public class StockNewsDTO {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("stock")
    @Expose
    private StockDTO stock;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("is_login")
    @Expose
    private Integer isLogin;
    @SerializedName("execution_time")
    @Expose
    private Double executionTime;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public StockDTO getStock() {
        return stock;
    }

    public void setStock(StockDTO stock) {
        this.stock = stock;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }
}
