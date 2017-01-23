package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 23.01.2017.
 */

public class SignalsDTO {

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("topgainers")
    @Expose
    private TopgainersDTO topgainersDTO;

    @SerializedName("toploser")
    @Expose
    private ToploserDTO toploserDTO;
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

    public TopgainersDTO getTopgainers() {
        return topgainersDTO;
    }

    public void setTopgainers(TopgainersDTO topgainers) {
        this.topgainersDTO = topgainers;
    }

    public ToploserDTO getToploser() {
        return toploserDTO;
    }

    public void setToploser(ToploserDTO toploser) {
        this.toploserDTO = toploser;
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
