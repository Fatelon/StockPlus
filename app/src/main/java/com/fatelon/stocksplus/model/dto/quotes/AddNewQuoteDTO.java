package com.fatelon.stocksplus.model.dto.quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 26.01.2017.
 */

public class AddNewQuoteDTO {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("quote_id")
    @Expose
    private Integer quoteId;
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

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
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
