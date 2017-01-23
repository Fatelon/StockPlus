package com.fatelon.stocksplus.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 23.01.2017.
 */

public class UnusualvolumeDTO {

    @SerializedName("unusualvolume")
    @Expose
    private List<OneSignalDTO> unusualvolume = null;

    public List<OneSignalDTO> getUnusualvolume() {
        return unusualvolume;
    }

    public void setUnusualvolume(List<OneSignalDTO> unusualvolume) {
        this.unusualvolume = unusualvolume;
    }
}
