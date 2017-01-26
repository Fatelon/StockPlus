package com.fatelon.stocksplus.model.dto.signals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fatelon on 24.01.2017.
 */

public class Signals {

    @SerializedName("topgainers")
    @Expose
    private List<OneSignalDTO> topgainers = null;
    @SerializedName("toplosers")
    @Expose
    private List<OneSignalDTO> toplosers = null;
    @SerializedName("newhigh")
    @Expose
    private List<OneSignalDTO> newhigh = null;
    @SerializedName("newlow")
    @Expose
    private List<OneSignalDTO> newlow = null;
    @SerializedName("unusualvolume")
    @Expose
    private List<OneSignalDTO> unusualvolume = null;
    @SerializedName("mostvolatile")
    @Expose
    private List<OneSignalDTO> mostvolatile = null;

    public List<OneSignalDTO> getTopgainers() {
        return topgainers;
    }

    public void setTopgainers(List<OneSignalDTO> topgainers) {
        this.topgainers = topgainers;
    }

    public List<OneSignalDTO> getToplosers() {
        return toplosers;
    }

    public void setToplosers(List<OneSignalDTO> toplosers) {
        this.toplosers = toplosers;
    }

    public List<OneSignalDTO> getNewhigh() {
        return newhigh;
    }

    public void setNewhigh(List<OneSignalDTO> newhigh) {
        this.newhigh = newhigh;
    }

    public List<OneSignalDTO> getNewlow() {
        return newlow;
    }

    public void setNewlow(List<OneSignalDTO> newlow) {
        this.newlow = newlow;
    }

    public List<OneSignalDTO> getUnusualvolume() {
        return unusualvolume;
    }

    public void setUnusualvolume(List<OneSignalDTO> unusualvolume) {
        this.unusualvolume = unusualvolume;
    }

    public List<OneSignalDTO> getMostvolatile() {
        return mostvolatile;
    }

    public void setMostvolatile(List<OneSignalDTO> mostvolatile) {
        this.mostvolatile = mostvolatile;
    }
}
