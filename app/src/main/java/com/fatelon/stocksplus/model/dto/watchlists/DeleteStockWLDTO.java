package com.fatelon.stocksplus.model.dto.watchlists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 01.02.2017.
 */

public class DeleteStockWLDTO {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("watchlist_ticker_id")
    @Expose
    private String watchlistTickerId;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("is_login")
    @Expose
    private String isLogin;
    @SerializedName("execution_time")
    @Expose
    private Double executionTime;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getWatchlistTickerId() {
        return watchlistTickerId;
    }

    public void setWatchlistTickerId(String watchlistTickerId) {
        this.watchlistTickerId = watchlistTickerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }
}
