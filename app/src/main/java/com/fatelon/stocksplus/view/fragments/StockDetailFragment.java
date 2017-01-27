package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.CustomTitle;
import com.squareup.picasso.Picasso;

/**
 * Created by Fatelon on 26.01.2017.
 */

public class StockDetailFragment extends BaseFragment {

    private CustomTitle stockDetailTitle;

    private CustomTextView stockName;

    private ImageView yahooChartContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        init(view);

        Bundle args = getArguments();
        String stock = args.getString("stock_name");
        if (stock != null) {
            stockName.setText(stock);
        }

        String url = getUrl(stock, "1d", "l");

        Picasso.with(context)
                .load(url)
                .into(yahooChartContainer);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init(View view) {
        stockDetailTitle = (CustomTitle) view.findViewById(R.id.stock_detail_title);
        stockDetailTitle.setPressBackCallBack(context);
        stockName = (CustomTextView) view.findViewById(R.id.stock_name);
        yahooChartContainer = (ImageView) view.findViewById(R.id.yahoo_chart_container);
    }

    private String getUrl(String s, String t, String q) {
        String url = "http://chart.finance.yahoo.com/z?s=" + s +"&t=" + t + "&q=" + q;

        return url;
    }
}
