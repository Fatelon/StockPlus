package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 23.01.2017.
 */

public class TopgainersDTO {

    @SerializedName("topgainers")
    @Expose
    private List<OneSignalDTO> topgainers = null;

    public List<OneSignalDTO> getToplosers() {
        return topgainers;
    }

    public void setToplosers(List<OneSignalDTO> toplosers) {
        this.topgainers = toplosers;
    }
}
