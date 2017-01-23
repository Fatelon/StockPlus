package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 23.01.2017.
 */

public class ToploserDTO {

    @SerializedName("toplosers")
    @Expose
    private List<OneSignalDTO> toplosers = null;

    public List<OneSignalDTO> getToplosers() {
        return toplosers;
    }

    public void setToplosers(List<OneSignalDTO> toplosers) {
        this.toplosers = toplosers;
    }
}
