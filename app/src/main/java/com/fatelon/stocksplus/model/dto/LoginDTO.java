package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 20.01.2017.
 */

public class LoginDTO {

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("errors")
    @Expose
    private String errors;

    @SerializedName("session_id")
    @Expose
    private String sessionId;

    @SerializedName("is_login")
    @Expose
    private Integer isLogin;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
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

}
