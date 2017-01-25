package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatelon on 22.01.2017.
 */

public class RegistrationDTO {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("errors")
    @Expose
    private RegistrationErrorsDTO errors;
    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public RegistrationErrorsDTO getErrors() {
        return errors;
    }

    public void setErrors(RegistrationErrorsDTO errors) {
        this.errors = errors;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}