package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 23.01.2017.
 */

public class NewlowDTO {

    @SerializedName("newlow")
    @Expose
    private List<OneSignalDTO> newlow = null;

    public List<OneSignalDTO> getNewlow() {
        return newlow;
    }

    public void setNewlow(List<OneSignalDTO> newlow) {
        this.newlow = newlow;
    }
}
