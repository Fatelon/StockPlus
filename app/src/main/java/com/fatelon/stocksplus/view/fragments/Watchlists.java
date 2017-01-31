package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.watchlists.AddWatchlistDTO;
import com.fatelon.stocksplus.model.dto.watchlists.GetWatchListsDTO;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.view.dialogs.SimpleDialog.showAddStockWatchListDialogWithCallback;
import static com.fatelon.stocksplus.view.dialogs.SimpleDialog.showAddWatchListDialogWithCallback;

/**
 * Created by User on 21.01.2017.
 */

public class Watchlists extends BaseMenuFragment {

    private static String TAG = Watchlists.class.getSimpleName();

    private ImageView addNewWatchListButton;
    private ImageView selectList;
    private ImageView addStock;

    private FrameLayout mainLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlists, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        addNewWatchListButton = (ImageView) view.findViewById(R.id.add_new_watchlist);
        addNewWatchListButton.setOnClickListener(v->showAddWatchListDialogWithCallback(context, p -> addNewWatchListOnServer(p[0])));
        selectList = (ImageView) view.findViewById(R.id.select_list_watchlist);
        addStock = (ImageView) view.findViewById(R.id.add_stock_watchlist);
        addStock.setOnClickListener(v->showAddStockWatchListDialogWithCallback(context, p -> {}));
        mainLoader = (FrameLayout) view.findViewById(R.id.watch_lists_loader);
    }

    private void addNewWatchListOnServer(String name) {
        mainLoader.setVisibility(View.VISIBLE);
        Map<String, String> m = new HashMap<>();
        m.put(context.getResources().getString(R.string.simple_name), name);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.postNewWatchList(m, PreferencesHelper.getUserSessionId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<AddWatchlistDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                        mainLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(AddWatchlistDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            mainLoader.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void getUserWatchLists() {
        mainLoader.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getWatchLists(PreferencesHelper.getUserId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<GetWatchListsDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                        mainLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(GetWatchListsDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            mainLoader.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
}