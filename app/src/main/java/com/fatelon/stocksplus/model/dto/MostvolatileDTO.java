package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 23.01.2017.
 */

public class MostvolatileDTO {

    @SerializedName("mostvolatile")
    @Expose
    private List<OneSignalDTO> mostvolatile = null;

    public List<OneSignalDTO> getMostvolatile() {
        return mostvolatile;
    }

    public void setMostvolatile(List<OneSignalDTO> mostvolatile) {
        this.mostvolatile = mostvolatile;
    }
}
