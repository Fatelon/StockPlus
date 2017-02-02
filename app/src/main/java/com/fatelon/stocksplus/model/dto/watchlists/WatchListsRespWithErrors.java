package com.fatelon.stocksplus.model.dto.watchlists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Fatelon on 02.02.2017.
 */

public class WatchListsRespWithErrors {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("errors")
    @Expose
    private Map<String, String> errors;
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

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
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
