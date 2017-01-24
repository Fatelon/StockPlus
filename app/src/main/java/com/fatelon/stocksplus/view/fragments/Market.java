package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.IndexesDTO;
import com.fatelon.stocksplus.view.Settings;
import com.fatelon.stocksplus.view.callbacks.OpenNewFragmentCallBack;
import com.fatelon.stocksplus.view.customviews.CustomIndex;
import com.fatelon.stocksplus.view.customviews.CustomMarketItem;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by User on 21.01.2017.
 */

public class Market extends BaseMenuFragment {

    private static String TAG = Market.class.getSimpleName();

    private OpenNewFragmentCallBack openNewFragmentCallBack;

    private ImageView settingsButton;

    private CustomMarketItem marketItemTopGainers;
    private CustomMarketItem marketItemTopLosers;
    private CustomMarketItem marketItemNewHigh;
    private CustomMarketItem marketItemNewLow;
    private CustomMarketItem marketItemUnusualVolume;
    private CustomMarketItem marketItemMostVolatile;
    private CustomMarketItem marketItemEarnings;
    private CustomMarketItem marketItemConferenceCalls;
    private CustomMarketItem marketItemDividends;
    private CustomMarketItem marketItemSplits;
    private CustomMarketItem marketItemIpo;

    private CustomIndex customIndex1;
    private CustomIndex customIndex2;
    private CustomIndex customIndex3;

    private Subscription indexUpdateSubscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            openNewFragmentCallBack = (OpenNewFragmentCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        init(view);

        updateIndexes();
        indexUpdateSubscription = Observable.interval(10, TimeUnit.SECONDS).subscribe(l -> {
            Log.d("indexUpdateSubscription", " - " + l);
            updateIndexes();
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        indexUpdateSubscription.unsubscribe();
    }

    private void init(View view) {
        settingsButton = (ImageView) view.findViewById(R.id.open_settings_button);
        settingsButton.setOnClickListener(v -> onClickSettingsButton(v));
        marketItemTopGainers = (CustomMarketItem) view.findViewById(R.id.market_item_top_gainers_elem);
        marketItemTopGainers.setOnClickListener(v->onMarketItemClick(1));
        marketItemTopLosers = (CustomMarketItem) view.findViewById(R.id.market_item_top_losers_elem);
        marketItemTopLosers.setOnClickListener(v->onMarketItemClick(2));
        marketItemNewHigh = (CustomMarketItem) view.findViewById(R.id.market_item_new_high_elem);
        marketItemNewHigh.setOnClickListener(v->onMarketItemClick(3));
        marketItemNewLow = (CustomMarketItem) view.findViewById(R.id.market_item_new_low_elem);
        marketItemNewLow.setOnClickListener(v->onMarketItemClick(4));
        marketItemUnusualVolume = (CustomMarketItem) view.findViewById(R.id.market_item_unusual_volume_elem);
        marketItemUnusualVolume.setOnClickListener(v->onMarketItemClick(5));
        marketItemMostVolatile = (CustomMarketItem) view.findViewById(R.id.market_item_most_volatile_elem);
        marketItemMostVolatile.setOnClickListener(v->onMarketItemClick(6));
        marketItemEarnings = (CustomMarketItem) view.findViewById(R.id.market_item_earnings_elem);
        marketItemEarnings.setOnClickListener(v->onMarketItemClick(7));
        marketItemConferenceCalls = (CustomMarketItem) view.findViewById(R.id.market_item_conference_calls_elem);
        marketItemConferenceCalls.setOnClickListener(v->onMarketItemClick(8));
        marketItemDividends = (CustomMarketItem) view.findViewById(R.id.market_item_dividends_elem);
        marketItemDividends.setOnClickListener(v->onMarketItemClick(9));
        marketItemSplits = (CustomMarketItem) view.findViewById(R.id.market_item_splits_elem);
        marketItemSplits.setOnClickListener(v->onMarketItemClick(10));
        marketItemIpo = (CustomMarketItem) view.findViewById(R.id.market_item_ipo_elem);
        marketItemIpo.setOnClickListener(v->onMarketItemClick(11));

        customIndex1 = (CustomIndex) view.findViewById(R.id.index_type_1);
        customIndex1.setIndexTitle(context.getResources().getString(R.string.index_type_1_name));
        customIndex2 = (CustomIndex) view.findViewById(R.id.index_type_2);
        customIndex2.setIndexTitle(context.getResources().getString(R.string.index_type_2_name));
        customIndex3 = (CustomIndex) view.findViewById(R.id.index_type_3);
        customIndex3.setIndexTitle(context.getResources().getString(R.string.index_type_3_name));
    }

    private void onClickSettingsButton(View v) {
        startActivity(new Intent(context, Settings.class));
    }

    private void onMarketItemClick(Integer itemNumber) {
        if (openNewFragmentCallBack != null) openNewFragmentCallBack.openNewFragment(itemNumber);
    }

    private void updateIndexes() {
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getIndexes().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<IndexesDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                    }
                    @Override
                    public void onNext(IndexesDTO indexes) {
                        Log.d(TAG, "onNext - ");
                        try {
                            customIndex1.setIndex(indexes.getItems().get(0));
                            customIndex2.setIndex(indexes.getItems().get(1));
                            customIndex3.setIndex(indexes.getItems().get(2));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

}
