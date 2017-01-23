package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 23.01.2017.
 */

public class NewhighDTO {

    @SerializedName("newhigh")
    @Expose
    private List<OneSignalDTO> newhigh = null;

    public List<OneSignalDTO> getNewhigh() {
        return newhigh;
    }

    public void setNewhigh(List<OneSignalDTO> newhigh) {
        this.newhigh = newhigh;
    }
}
