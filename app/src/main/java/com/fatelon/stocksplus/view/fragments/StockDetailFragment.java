package com.fatelon.stocksplus.view.fragments;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.stockinfo.StockInfoDTO;
import com.fatelon.stocksplus.model.dto.stockinfo.StockInfoFirstResponseDTO;
import com.fatelon.stocksplus.view.DynamicChartActivity;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.CustomTitle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.Constants.SERVER_MESSAGE_NO_ERROR;

/**
 * Created by Fatelon on 26.01.2017.
 */

public class StockDetailFragment extends BaseFragment {

    private static String TAG = StockDetailFragment.class.getSimpleName();

    private CustomTitle stockDetailTitle;

    private CustomTextView stockName;
    private CustomTextView stockPrice;
    private CustomTextView companyName;
    private CustomTextView change;

    private List<CustomTextView> timePeriodButtons = new ArrayList<CustomTextView>();
    private List<CustomTextView> chartTypeButtons = new ArrayList<CustomTextView>();

    private ImageView indicator;
    private ImageView yahooChartContainer;

    private int currentTimePeriod = 0;
    private int currentChartType = 0;

    private String[] timePeriods = {"1d", "5d", "1m", "3m", "6m", "1y", "2y", "5y"};
    private String[] chartTypes = {"l", "c", "b"};

    private FrameLayout loadingIndicator;
    private FrameLayout chartLoadingIndicator;

    private String currentStockName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        init(view);

        Bundle args = getArguments();
        currentStockName = args.getString("stock_name");
        if (currentStockName != null) {
            stockName.setText(currentStockName);
            getStockInfo(currentStockName);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init(View view) {
        stockDetailTitle = (CustomTitle) view.findViewById(R.id.stock_detail_title);
        stockDetailTitle.setPressBackCallBack(context);
        stockName = (CustomTextView) view.findViewById(R.id.stock_detail_ticker);
        stockPrice = (CustomTextView) view.findViewById(R.id.stock_detail_price);
        companyName = (CustomTextView) view.findViewById(R.id.stock_detail_company_name);
        change = (CustomTextView) view.findViewById(R.id.stock_detail_change);
        indicator = (ImageView) view.findViewById(R.id.stock_detail_indicator);
        yahooChartContainer = (ImageView) view.findViewById(R.id.yahoo_chart_container);
        yahooChartContainer.setOnClickListener(v -> {startActivity(new Intent(context, DynamicChartActivity.class));});

        loadingIndicator = (FrameLayout) view.findViewById(R.id.stock_detail_loading_indicator);
        chartLoadingIndicator = (FrameLayout) view.findViewById(R.id.stock_detail_chart_loading_indicator);

        CustomTextView _1dButton = (CustomTextView) view.findViewById(R.id.stock_detail_1d_button);
        timePeriodButtons.add(_1dButton);
        CustomTextView _5dButton = (CustomTextView) view.findViewById(R.id.stock_detail_5d_button);
        timePeriodButtons.add(_5dButton);
        CustomTextView _1mButton = (CustomTextView) view.findViewById(R.id.stock_detail_1m_button);
        timePeriodButtons.add(_1mButton);
        CustomTextView _3mButton = (CustomTextView) view.findViewById(R.id.stock_detail_3m_button);
        timePeriodButtons.add(_3mButton);
        CustomTextView _6mButton = (CustomTextView) view.findViewById(R.id.stock_detail_6m_button);
        timePeriodButtons.add(_6mButton);
        CustomTextView _1yButton = (CustomTextView) view.findViewById(R.id.stock_detail_1y_button);
        timePeriodButtons.add(_1yButton);
        CustomTextView _2yButton = (CustomTextView) view.findViewById(R.id.stock_detail_2y_button);
        timePeriodButtons.add(_2yButton);
        CustomTextView _5yButton = (CustomTextView) view.findViewById(R.id.stock_detail_5y_button);
        timePeriodButtons.add(_5yButton);
        setTimeButtonsClickListener();

        CustomTextView stock_detail_chart_type_line_button = (CustomTextView) view.findViewById(R.id.stock_detail_chart_type_line_button);
        chartTypeButtons.add(stock_detail_chart_type_line_button);
        CustomTextView stock_detail_chart_type_candle_button = (CustomTextView) view.findViewById(R.id.stock_detail_chart_type_candle_button);
        chartTypeButtons.add(stock_detail_chart_type_candle_button);
        CustomTextView stock_detail_chart_type_bar_button = (CustomTextView) view.findViewById(R.id.stock_detail_chart_type_bar_button);
        chartTypeButtons.add(stock_detail_chart_type_bar_button);
        setChartTypeButtonsClickListener();

    }

    private void loadNewChart() {
        chartLoadingIndicator.setVisibility(View.VISIBLE);
        String url = getUrl();

        Picasso.with(context)
                .load(url)
                .into(yahooChartContainer, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        chartLoadingIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        chartLoadingIndicator.setVisibility(View.GONE);
                    }
                });
    }

    private String getUrl() {
        String ticker = currentStockName;
        String timeframe = timePeriods[currentTimePeriod];
        String chartType = chartTypes[currentChartType];
        String logScaleMode = "on";
        String chartSize = "l";
        String url = "http://chart.finance.yahoo.com/z?s=" + ticker +"&t=" + timeframe + "&q=" + chartType + "&l=" + logScaleMode + "&z=" + chartSize;

        return url;
    }

    private void setChartTypeButtonsClickListener() {
        for (CustomTextView item : chartTypeButtons) {
            item.setOnClickListener(v -> onChartTypeButtonClick(true, v));
        }
    }

    private void onChartTypeButtonClick(boolean user, View view) {
        for (int i = 0; i < chartTypeButtons.size(); i++) {
            makeButtonBlack(chartTypeButtons.get(i));
            chartTypeButtons.get(i).setClickable(true);
            if (chartTypeButtons.get(i).getId() == view.getId()) {
                currentChartType = i;
            }
        }
        makeButtonRed(view);
        view.setClickable(false);
        if (user) loadNewChart();
    }

    private void setTimeButtonsClickListener() {
        for (CustomTextView item : timePeriodButtons) {
            item.setOnClickListener(v -> onTimeButtonClick(true, v));
        }
    }

    private void onTimeButtonClick(boolean user, View view) {
        for (int i = 0; i < timePeriodButtons.size(); i++) {
            makeButtonBlack(timePeriodButtons.get(i));
            timePeriodButtons.get(i).setClickable(true);
            if (timePeriodButtons.get(i).getId() == view.getId()) {
                currentTimePeriod = i;
            }
        }
        makeButtonRed(view);
        view.setClickable(false);
        if (user) loadNewChart();
    }

    private void makeButtonBlack(View view) {
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setColor(ContextCompat.getColor(context, R.color.mainBackground));
    }

    private void makeButtonRed(View view) {
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setColor(ContextCompat.getColor(context, R.color.red));
    }

    private void setStockInfo(StockInfoDTO newInfo) {
        if (newInfo != null) {

            stockPrice.setText(newInfo.getPrice());
            companyName.setText(newInfo.getCompanyName());
            change.setText(newInfo.getChageNum() + "(" + newInfo.getChangePercent() + ")");
            if (newInfo.getChageNum().length() > 1 && newInfo.getChageNum().charAt(0) == '-') {
                indicator.setBackgroundResource(R.mipmap.down_indicator);
                change.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                indicator.setBackgroundResource(R.mipmap.up_indicator);
                change.setTextColor(ContextCompat.getColor(context, R.color.green));
            }
        }
    }

    private void getStockInfo(String ticker) {
        loadingIndicator.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getStockInfo(ticker).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<StockInfoFirstResponseDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingIndicator.setVisibility(View.GONE);
                        Log.d(TAG, "error - " + e.toString());
                    }

                    @Override
                    public void onNext(StockInfoFirstResponseDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            int error = response.getError();
                            if (error == SERVER_MESSAGE_NO_ERROR) {
                                setStockInfo(response.getStock());
                                if (timePeriodButtons.size() > 0) {
                                    onTimeButtonClick(false, timePeriodButtons.get(0));
                                    onChartTypeButtonClick(false, chartTypeButtons.get(0));
                                }
                                loadNewChart();
                            }
                            loadingIndicator.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
}
