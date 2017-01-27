package com.fatelon.stocksplus.model.dto.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class WeekCalendarDTO {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("calendar")
    @Expose
    private Map<String, CalendarDTO> calendar;
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

    public Map<String, CalendarDTO> getCalendar() {
        return calendar;
    }

    public void setCalendar(Map<String, CalendarDTO> calendar) {
        this.calendar = calendar;
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
