package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.ErrorHelper;
import com.fatelon.stocksplus.helpers.MyHorizontalViewHelper;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;
import com.fatelon.stocksplus.model.dto.calendar.WeekCalendarDTO;
import com.fatelon.stocksplus.model.dto.quotes.AddNewQuoteDTO;
import com.fatelon.stocksplus.model.dto.indexes.IndexesDTO;
import com.fatelon.stocksplus.model.dto.LoginDTO;
import com.fatelon.stocksplus.model.dto.news.NewsDTO;
import com.fatelon.stocksplus.model.dto.news.OneNewsDTO;
import com.fatelon.stocksplus.model.dto.quotes.OneQuoteDTO;
import com.fatelon.stocksplus.model.dto.quotes.UserDataDTO;
import com.fatelon.stocksplus.view.Settings;
import com.fatelon.stocksplus.view.callbacks.DialogMultiResponse;
import com.fatelon.stocksplus.view.callbacks.MyHorizontalViewCallBack;
import com.fatelon.stocksplus.view.callbacks.OpenNewFragmentCallBack;
import com.fatelon.stocksplus.view.customviews.CustomCalendarBox;
import com.fatelon.stocksplus.view.customviews.CustomIndex;
import com.fatelon.stocksplus.view.customviews.CustomMarketItem;
import com.fatelon.stocksplus.view.customviews.CustomNewsItem;
import com.fatelon.stocksplus.view.customviews.CustomQuoteView;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.dialogs.SimpleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static com.fatelon.stocksplus.Constants.DIVIDENDS_TRIGGER;
import static com.fatelon.stocksplus.Constants.STOCK_DETAIL_TRIGGER;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class Market extends BaseMenuFragment implements MyHorizontalViewCallBack {

    private static String TAG = Market.class.getSimpleName();

    private OpenNewFragmentCallBack openNewFragmentCallBack;

    private ImageView settingsButton;

    private FrameLayout newsLoader;
    private FrameLayout quotesLoader;

    private LinearLayout myHorizontalRowOne;
    private LinearLayout myHorizontalRowTwo;
    private LinearLayout myHorizontalViewBox;

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

    private CustomNewsItem customNewsItem1;
    private CustomNewsItem customNewsItem2;
    private CustomNewsItem customNewsItem3;

    private CustomCalendarBox marketCustomCalendarBox;

    private CustomTextView marketEditButton;

    private List<OneNewsDTO> marketScreenNews = new ArrayList<OneNewsDTO>();

    private Subscription indexUpdateSubscription;

    private MyHorizontalViewHelper horizontalViewHelper;

    private Map<String, CalendarDTO> calendar = new HashMap<String, CalendarDTO>();

    private final PublishSubject<String> onClickDayBox = PublishSubject.create();

    private boolean editMode = false;

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
        getNews(3);
        getUserData();

        getWeekCalendar();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        indexUpdateSubscription.unsubscribe();
    }

    public Observable<String> getDayBoxClick(){
        return onClickDayBox.asObservable();
    }

    public Map<String, CalendarDTO> getCalendar() {
        return calendar;
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
        marketItemEarnings.setOnClickListener(v->onClickEventsItem(7));
        marketItemConferenceCalls = (CustomMarketItem) view.findViewById(R.id.market_item_conference_calls_elem);
        marketItemConferenceCalls.setOnClickListener(v->onClickEventsItem(8));
        marketItemDividends = (CustomMarketItem) view.findViewById(R.id.market_item_dividends_elem);
        marketItemDividends.setOnClickListener(v->onClickEventsItem(DIVIDENDS_TRIGGER));
        marketItemSplits = (CustomMarketItem) view.findViewById(R.id.market_item_splits_elem);
        marketItemSplits.setOnClickListener(v->onClickEventsItem(10));
        marketItemIpo = (CustomMarketItem) view.findViewById(R.id.market_item_ipo_elem);
        marketItemIpo.setOnClickListener(v->onClickEventsItem(11));

        marketEditButton = (CustomTextView) view.findViewById(R.id.market_edit_button);
        marketEditButton.setOnClickListener(v->changeMode());

        customIndex1 = (CustomIndex) view.findViewById(R.id.index_type_1);
        customIndex1.setIndexTitle(context.getResources().getString(R.string.index_type_1_name));
        customIndex2 = (CustomIndex) view.findViewById(R.id.index_type_2);
        customIndex2.setIndexTitle(context.getResources().getString(R.string.index_type_2_name));
        customIndex3 = (CustomIndex) view.findViewById(R.id.index_type_3);
        customIndex3.setIndexTitle(context.getResources().getString(R.string.index_type_3_name));

        customNewsItem1 = (CustomNewsItem) view.findViewById(R.id.market_custom_news_1);
        customNewsItem1.setOnClickListener(v->onClickCustomNewsItem(0));
        customNewsItem2 = (CustomNewsItem) view.findViewById(R.id.market_custom_news_2);
        customNewsItem2.setOnClickListener(v->onClickCustomNewsItem(1));
        customNewsItem3 = (CustomNewsItem) view.findViewById(R.id.market_custom_news_3);
        customNewsItem3.setOnClickListener(v->onClickCustomNewsItem(2));

        marketCustomCalendarBox = (CustomCalendarBox) view.findViewById(R.id.market_custom_calendar_box);

        marketCustomCalendarBox.getDayCalendarBoxesClick().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                onClickDayBox.onNext(s);
            }
        });

        newsLoader = (FrameLayout) view.findViewById(R.id.market_news_loader);
        quotesLoader = (FrameLayout) view.findViewById(R.id.quotes_loader);


        myHorizontalRowOne = (LinearLayout) view.findViewById(R.id.my_horizontal_row_one);
        myHorizontalRowTwo = (LinearLayout) view.findViewById(R.id.my_horizontal_row_two);
        myHorizontalViewBox = (LinearLayout) view.findViewById(R.id.my_horizontal_view_box);

        horizontalViewHelper = new MyHorizontalViewHelper(myHorizontalRowOne, myHorizontalRowTwo, context);
        horizontalViewHelper.setGlobalViewSize(myHorizontalViewBox);
    }

    private void onClickSettingsButton(View v) {
        getActivity().startActivity(new Intent(getActivity(), Settings.class));
    }

    private void onClickCustomNewsItem(Integer itemNumber) {
        try {
            openNewFragmentCallBack.
                    openNewFragmentWithString(itemNumber, marketScreenNews.get(itemNumber).getUrl());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onClickStockDetailItem(String actionName) {
        try {
            openNewFragmentCallBack.openNewFragmentWithString(STOCK_DETAIL_TRIGGER, actionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onClickEventsItem(Integer itemNumber) {
        try {
            openNewFragmentCallBack.openNewFragmentWithWeekCalendar(itemNumber, calendar);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private void getNews(Integer count) {
        newsLoader.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getNews(count).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<NewsDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                    }
                    @Override
                    public void onNext(NewsDTO newses) {
                        Log.d(TAG, "onNext - ");
                        try {
                            marketScreenNews.clear();
                            marketScreenNews.addAll(newses.getNews());
                            customNewsItem1.setTextAndTime(newses.getNews().get(0).getTitle(),
                                    newses.getNews().get(0).getTime());
                            customNewsItem2.setTextAndTime(newses.getNews().get(1).getTitle(),
                                    newses.getNews().get(1).getTime());
                            customNewsItem3.setTextAndTime(newses.getNews().get(2).getTitle(),
                                    newses.getNews().get(2).getTime());
                            newsLoader.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void getUserData() {
        quotesLoader.setVisibility(View.VISIBLE);
        Integer userId = PreferencesHelper.getUserId(context);
        if (userId != -1) {
            ApiInterface apiInterface = ApiModule.getApiInterface();
            apiInterface.getUserData(userId).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Subscriber<UserDataDTO>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error - " + e.toString());
                        }

                        @Override
                        public void onNext(UserDataDTO userDataDTO) {
                            Log.d(TAG, "onNext - ");
                            try {
                                Map<String, OneQuoteDTO> m = userDataDTO.getQuotes();
                                setQuotes(m);
                                checkEditMode();
                                quotesLoader.setVisibility(View.GONE);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void setQuotes(Map<String, OneQuoteDTO> m) {
        horizontalViewHelper.clear();
        horizontalViewHelper.setActionCallBack((MyHorizontalViewCallBack)this);
        for (Map.Entry<String, OneQuoteDTO> entry : m.entrySet()) {
            CustomQuoteView cq = new CustomQuoteView(context);
            cq.setQuote(entry.getValue());
            cq.setOnClickListener(v->onClickStockDetailItem(entry.getValue().get2()));
            horizontalViewHelper.addView(cq);
        }
        horizontalViewHelper.addEmptyQuote();
    }

    private void changeMode() {
        if (editMode) editMode = false;
        else editMode = true;
        checkEditMode();
    }

    private void checkEditMode() {
        if (editMode) {
            marketEditButton.setText(context.getResources().getString(R.string.tab_done));
        } else {
            marketEditButton.setText(context.getResources().getString(R.string.tab_edit));
        }
        horizontalViewHelper.setDeleteVisibility(editMode);
    }

    @Override
    public void onPressDeleteCross(String crossId) {
        quotesLoader.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.deleteQuote(crossId, PreferencesHelper.getUserSessionId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<LoginDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        quotesLoader.setVisibility(View.GONE);
                        Log.d(TAG, "error - " + e.toString());
                        ErrorHelper.failedToConnectSimpleDialog(context, e);
                    }

                    @Override
                    public void onNext(LoginDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            getUserData();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void onPressAddNew() {
        SimpleDialog.showAddQuoteDialogWithCallback(context, new DialogMultiResponse() {
            @Override
            public void dialogMultiResponse(String... params) {
                if (params[0] != null && !params[0].equals("")) {
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("instrument_name", params[0].toUpperCase());
                    quotesLoader.setVisibility(View.VISIBLE);
                    ApiInterface apiInterface = ApiModule.getApiInterface();
                    apiInterface.postNewQuote(m, PreferencesHelper.getUserSessionId(context)).subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(new Subscriber<AddNewQuoteDTO>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(TAG, "onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    quotesLoader.setVisibility(View.GONE);
                                    Log.d(TAG, "error - " + e.toString());
                                    ErrorHelper.failedToConnectSimpleDialog(context, e);
                                }

                                @Override
                                public void onNext(AddNewQuoteDTO response) {
                                    Log.d(TAG, "onNext - ");
                                    try {
                                        getUserData();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void getWeekCalendar() {
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getWeekCalendar().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<WeekCalendarDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                    }

                    @Override
                    public void onNext(WeekCalendarDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            calendar = response.getCalendar();
                            marketCustomCalendarBox.setDates(calendar);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
}
