package com.fatelon.stocksplus.model.dto.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class CalendarDTO {

    @SerializedName("earnings")
    @Expose
    private List<EarningDTO> earnings = null;
    @SerializedName("confcalls")
    @Expose
    private List<ConfcallDTO> confcalls = null;
    @SerializedName("splits")
    @Expose
    private List<SplitsDTO> splits = null;
    @SerializedName("ipo")
    @Expose
    private List<IpoDTO> ipo = null;
    @SerializedName("dividents")
    @Expose
    private List<DividentDTO> dividents = null;
    @SerializedName("dt")
    @Expose
    private String dt;

    public List<EarningDTO> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<EarningDTO> earnings) {
        this.earnings = earnings;
    }

    public List<ConfcallDTO> getConfcalls() {
        return confcalls;
    }

    public void setConfcalls(List<ConfcallDTO> confcalls) {
        this.confcalls = confcalls;
    }

    public List<SplitsDTO> getSplits() {
        return splits;
    }

    public void setSplits(List<SplitsDTO> splits) {
        this.splits = splits;
    }

    public List<IpoDTO> getIPO() {
        return ipo;
    }

    public void setIPO(List<IpoDTO> ipo) {
        this.ipo = ipo;
    }

    public List<DividentDTO> getDividents() {
        return dividents;
    }

    public void setDividents(List<DividentDTO> dividents) {
        this.dividents = dividents;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

}
