package com.fatelon.stocksplus.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.TabCustomButton;
import com.fatelon.stocksplus.view.fragments.Market;
import com.fatelon.stocksplus.view.fragments.News;
import com.fatelon.stocksplus.view.fragments.Portfolio;
import com.fatelon.stocksplus.view.fragments.Search;
import com.fatelon.stocksplus.view.fragments.Watchlists;

/**
 * Created by User on 21.01.2017.
 */

public class MenuActivity extends FragmentActivity {

    public static Activity menuActivity;

    private Market marketFrag;
    private Portfolio portfolioFrag;
    private Watchlists watchlistsFrag;
    private Search searchFrag;
    private News newsFrag;

    private FragmentManager fragmentManager;

    private TabCustomButton tabMarketButton;
    private TabCustomButton tabPortfolioButton;
    private TabCustomButton tabWatchlistsButton;
    private TabCustomButton tabSearchButton;
    private TabCustomButton tabNewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuActivity = this;
        init();
        replaceFragment(new Market(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuActivity = null;
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        tabMarketButton = (TabCustomButton) findViewById(R.id.tab_market_button);
        tabMarketButton.setOnClickListener(v -> tabMarketButtonClick(v));
        tabPortfolioButton = (TabCustomButton) findViewById(R.id.tab_portfolio_button);
        tabPortfolioButton.setOnClickListener(v -> tabPortfolioButtonClick(v));
        tabWatchlistsButton = (TabCustomButton) findViewById(R.id.tab_watchlists_button);
        tabWatchlistsButton.setOnClickListener(v -> tabWatchlistsButtonClick(v));
        tabSearchButton = (TabCustomButton) findViewById(R.id.tab_search_button);
        tabSearchButton.setOnClickListener(v -> tabSearchButtonClick(v));
        tabNewsButton = (TabCustomButton) findViewById(R.id.tab_news_button);
        tabNewsButton.setOnClickListener(v -> tabNewsButtonClick(v));
    }

    private void tabMarketButtonClick(View v) {
        if (marketFrag == null) marketFrag = new Market();
        replaceFragment(marketFrag, false);
    }

    private void tabPortfolioButtonClick(View v) {
        if (portfolioFrag == null) portfolioFrag = new Portfolio();
        replaceFragment(portfolioFrag, false);
    }

    private void tabWatchlistsButtonClick(View v) {
        if (watchlistsFrag == null) watchlistsFrag = new Watchlists();
        replaceFragment(watchlistsFrag, false);
    }

    private void tabSearchButtonClick(View v) {
        if (searchFrag == null) searchFrag = new Search();
        replaceFragment(searchFrag, false);
    }

    private void tabNewsButtonClick(View v) {
        if (newsFrag == null) newsFrag = new News();
        replaceFragment(newsFrag, false);
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.menu_container, fragment);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }
}
