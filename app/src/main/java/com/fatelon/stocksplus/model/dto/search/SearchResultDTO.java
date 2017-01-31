package com.fatelon.stocksplus.model.dto.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fatelon on 31.01.2017.
 */

public class SearchResultDTO {

    @SerializedName("tickers")
    @Expose
    private List<TickerDTO> tickers = null;

    public List<TickerDTO> getTickers() {
        return tickers;
    }

    public void setTickers(List<TickerDTO> tickers) {
        this.tickers = tickers;
    }

}
