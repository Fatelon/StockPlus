package com.fatelon.stocksplus.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.OpenNewFragmentCallBack;
import com.fatelon.stocksplus.view.customviews.TabCustomButton;
import com.fatelon.stocksplus.view.fragments.BaseFragmentActivity;
import com.fatelon.stocksplus.view.fragments.Market;
import com.fatelon.stocksplus.view.fragments.News;
import com.fatelon.stocksplus.view.fragments.Portfolio;
import com.fatelon.stocksplus.view.fragments.Search;
import com.fatelon.stocksplus.view.fragments.SignalsFragment;
import com.fatelon.stocksplus.view.fragments.Watchlists;

/**
 * Created by User on 21.01.2017.
 */

    public class MenuActivity extends BaseFragmentActivity implements OpenNewFragmentCallBack {

    public static Activity menuActivity;

    private Market marketFrag;
    private Portfolio portfolioFrag;
    private Watchlists watchlistsFrag;
    private Search searchFrag;
    private News newsFrag;

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
        if (marketFrag == null) {
            marketFrag = new Market();
            replaceFragment(marketFrag, false, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuActivity = null;
    }

    private void init() {
        container = R.id.menu_container;
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
        replaceFragment(marketFrag, false, true);
    }

    private void tabPortfolioButtonClick(View v) {
        if (portfolioFrag == null) portfolioFrag = new Portfolio();
        replaceFragment(portfolioFrag, false, true);
    }

    private void tabWatchlistsButtonClick(View v) {
        if (watchlistsFrag == null) watchlistsFrag = new Watchlists();
        replaceFragment(watchlistsFrag, false, true);
    }

    private void tabSearchButtonClick(View v) {
        if (searchFrag == null) searchFrag = new Search();
        replaceFragment(searchFrag, false, true);
    }

    private void tabNewsButtonClick(View v) {
        if (newsFrag == null) newsFrag = new News();
        replaceFragment(newsFrag, false, true);
    }

    @Override
    public void openNewFragment(Integer number) {
        SignalsFragment signalsFragment = new SignalsFragment();
        Bundle args = new Bundle();
        args.putInt("number", number);
        signalsFragment.setArguments(args);
        replaceFragment(signalsFragment, false, true);
    }
}
