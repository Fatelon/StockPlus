package com.fatelon.stocksplus.model.dto.watchlists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Fatelon on 31.01.2017.
 */

public class GetWatchListsDTO {

    @SerializedName("watchlists")
    @Expose
    private Map<String, WatchlistsDTO> watchlists;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("is_login")
    @Expose
    private String isLogin;
    @SerializedName("execution_time")
    @Expose
    private Double executionTime;

    public Map<String, WatchlistsDTO> getWatchlists() {
        return watchlists;
    }

    public void setWatchlists(Map<String, WatchlistsDTO> watchlists) {
        this.watchlists = watchlists;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
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
