package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.ErrorHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;
import com.fatelon.stocksplus.model.dto.signals.OneSignalDTO;
import com.fatelon.stocksplus.model.dto.signals.SignalsDTO;
import com.fatelon.stocksplus.view.callbacks.OpenNewFragmentCallBack;
import com.fatelon.stocksplus.view.customviews.adapters.CustomEventsListViewAdapter;
import com.fatelon.stocksplus.view.customviews.adapters.CustomSplitListViewAdapter;
import com.fatelon.stocksplus.view.customviews.CustomTitle;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.Constants.STOCK_DETAIL_TRIGGER;

/**
 * Created by Fatelon on 23.01.2017.
 */

public class SignalsFragment extends BaseSignalsFragment {

    private static String TAG = SignalsFragment.class.getSimpleName();

    private FrameLayout signalsLoadingIndicator;

    private CustomTitle signalsTitle;

    private ListView signalsListView;

    private CustomSplitListViewAdapter customSplitListViewAdapter;

    private List<OneSignalDTO> splitItemObjects = new ArrayList<OneSignalDTO>();

    private OpenNewFragmentCallBack openNewFragmentCallBack;

    private Map<String, CalendarDTO> calendar = new HashMap<String, CalendarDTO>();

    private RecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        init(view);

        Bundle args = getArguments();
        Integer number = args.getInt("number", 0);
        if (number > 0 && number < 7) {
            signalsTitle.setRightTextVisible();
            showSignals(number);
        } else if (number > 6 && number < 12) {
            showEvents(number);
        }
        signalsTitle.setCustomText(getThisTitle(number));

        return view;
    }

    public void setCalendar(Map<String, CalendarDTO> calendar) {
        this.calendar = calendar;
    }

    private void init(View view) {
        signalsTitle = (CustomTitle) view.findViewById(R.id.signals_fragment_title);
        signalsTitle.setPressBackCallBack(context);
        signalsLoadingIndicator = (FrameLayout) view.findViewById(R.id.signals_loading_indicator);
        signalsListView = (ListView) view.findViewById(R.id.signals_list_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void showEvents(Integer number) {
        signalsListView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        CustomEventsListViewAdapter customEventsListViewAdapter = new CustomEventsListViewAdapter(number, calendar);
        recyclerView.setAdapter(customEventsListViewAdapter);
        customEventsListViewAdapter.getTickerClicks().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                openNewFragmentCallBack.openNewFragmentWithString(STOCK_DETAIL_TRIGGER, s);
            }
        });
    }

    private String getThisTitle(Integer number) {
        String title = "Title";
        switch (number) {
            case 1: title = context.getResources().getString(R.string.market_item_top_gainers); break;
            case 2: title = context.getResources().getString(R.string.market_item_top_losers); break;
            case 3: title = context.getResources().getString(R.string.market_item_new_high); break;
            case 4: title = context.getResources().getString(R.string.market_item_new_low); break;
            case 5: title = context.getResources().getString(R.string.market_item_unusual_volume); break;
            case 6: title = context.getResources().getString(R.string.market_item_most_volatile); break;
            case 7: title = context.getResources().getString(R.string.market_item_earnings); break;
            case 8: title = context.getResources().getString(R.string.market_item_conference_calls); break;
            case 9: title = context.getResources().getString(R.string.market_item_dividends); break;
            case 10: title = context.getResources().getString(R.string.market_item_splits); break;
            case 11: title = context.getResources().getString(R.string.market_item_ipo); break;
        }
        return title;
    }

    private String getSignalApiText(Integer number) {
        String text = "";
        switch (number) {
            case 1: text = context.getResources().getString(R.string.signal_type_topgainers); break;
            case 2: text = context.getResources().getString(R.string.signal_type_toplosers); break;
            case 3: text = context.getResources().getString(R.string.signal_type_newhigh); break;
            case 4: text = context.getResources().getString(R.string.signal_type_newlow); break;
            case 5: text = context.getResources().getString(R.string.signal_type_unusualvolume); break;
            case 6: text = context.getResources().getString(R.string.signal_type_mostvolatile); break;
        }
        return text;
    }

    private void showSignals(final Integer number) {
        showLoadingIndicator();
        customSplitListViewAdapter = new CustomSplitListViewAdapter(context, R.layout.custom_split_item, splitItemObjects);
        signalsListView.setAdapter(customSplitListViewAdapter);
        signalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openNewFragmentCallBack.openNewFragmentWithString(STOCK_DETAIL_TRIGGER, splitItemObjects.get(position).getTicker());
            }
        });
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getSignals(getSignalApiText(number)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<SignalsDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHelper.failedToConnectSimpleDialog(context, e);
                    }

                    @Override
                    public void onNext(SignalsDTO signal) {
                        Log.d(TAG, "onNext - ");
                        recognizeSignal(signal, number);
                    }
                });
    }

    private void recognizeSignal(SignalsDTO signal, Integer number) {
        List<OneSignalDTO> oneSignalList = new ArrayList<OneSignalDTO>();
        try {
            switch (number) {
                case 1: oneSignalList = signal.getSignals().getTopgainers(); break;
                case 2: oneSignalList = signal.getSignals().getToplosers(); break;
                case 3: oneSignalList = signal.getSignals().getNewhigh(); break;
                case 4: oneSignalList = signal.getSignals().getNewlow(); break;
                case 5: oneSignalList = signal.getSignals().getUnusualvolume(); break;
                case 6: oneSignalList = signal.getSignals().getMostvolatile(); break;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        splitItemObjects.clear();
        if (oneSignalList != null) splitItemObjects.addAll(oneSignalList);
        customSplitListViewAdapter.notifyDataSetChanged();
        hideLoadingIndicator();
    }

    private void showLoadingIndicator() {
        signalsLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        signalsLoadingIndicator.setVisibility(View.GONE);
    }
}
