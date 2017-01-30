package com.fatelon.stocksplus.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;
import com.fatelon.stocksplus.view.callbacks.OpenNewFragmentCallBack;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;
import com.fatelon.stocksplus.view.customviews.TabCustomButton;
import com.fatelon.stocksplus.view.fragments.BaseFragmentActivity;
import com.fatelon.stocksplus.view.fragments.DayEvents;
import com.fatelon.stocksplus.view.fragments.IndicatorsFragment;
import com.fatelon.stocksplus.view.fragments.Market;
import com.fatelon.stocksplus.view.fragments.News;
import com.fatelon.stocksplus.view.fragments.NewsesWebViewFragment;
import com.fatelon.stocksplus.view.fragments.Portfolio;
import com.fatelon.stocksplus.view.fragments.Search;
import com.fatelon.stocksplus.view.fragments.SignalsFragment;
import com.fatelon.stocksplus.view.fragments.StockDetailFragment;
import com.fatelon.stocksplus.view.fragments.Watchlists;

import java.util.Map;

import rx.functions.Action1;

import static com.fatelon.stocksplus.Constants.STOCK_DETAIL_TRIGGER;

/**
 * Created by Fatelon on 21.01.2017.
 */

    public class MenuActivity extends BaseFragmentActivity implements OpenNewFragmentCallBack, PressBackCallBack {

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
        tabMarketButtonClick(tabMarketButton);
    }

    public void onBackPressed() {

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
        v.setClickable(false);
        if (marketFrag == null) {
            marketFrag = getNewMarketInstance();
            replaceFragment(marketFrag, false, false);
        } else {
            popFragment();
        }
        setBlue((TabCustomButton)v);
    }

    private Market getNewMarketInstance() {
        Market mMarketFrag = new Market();
        mMarketFrag.getDayBoxClick().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (marketFrag != null) {
                    DayEvents dayEvents = new DayEvents();
                    dayEvents.setCalendar(s, mMarketFrag.getCalendar());
                    dayEvents.getEventClick().subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            openNewFragmentWithString(STOCK_DETAIL_TRIGGER, s);
                        }
                    });
                    replaceFragment(dayEvents, true, false);
                }
            }
        });;
        return mMarketFrag;
    }

    private void tabPortfolioButtonClick(View v) {
        v.setClickable(false);
        if (portfolioFrag == null) portfolioFrag = new Portfolio();
        replaceFragment(portfolioFrag, !tabMarketButton.isClickable(), false);
        setBlue((TabCustomButton)v);
    }

    private void tabWatchlistsButtonClick(View v) {
        v.setClickable(false);
        if (watchlistsFrag == null) watchlistsFrag = new Watchlists();
        replaceFragment(watchlistsFrag, !tabMarketButton.isClickable(), false);
        setBlue((TabCustomButton)v);
    }

    private void tabSearchButtonClick(View v) {
        v.setClickable(false);
        if (searchFrag == null) searchFrag = new Search();
        replaceFragment(searchFrag, !tabMarketButton.isClickable(), false);
        setBlue((TabCustomButton)v);
    }

    private void tabNewsButtonClick(View v) {
        v.setClickable(false);
        if (newsFrag == null) newsFrag = new News();
        replaceFragment(newsFrag, !tabMarketButton.isClickable(), false);
        setBlue((TabCustomButton)v);
    }

    private void setBlue(TabCustomButton tabCustomButton) {
        updateAllButtons();
        tabCustomButton.setClickable(false);
        tabCustomButton.makeBlue(this);
    }

    private boolean needPopPrevSate() {
        return tabMarketButton.isClickable();
    }

    private void updateAllButtons() {
        tabMarketButton.setClickable(true);
        tabMarketButton.makeWhite(this);
        tabPortfolioButton.setClickable(true);
        tabPortfolioButton.makeWhite(this);
        tabWatchlistsButton.setClickable(true);
        tabWatchlistsButton.makeWhite(this);
        tabSearchButton.setClickable(true);
        tabSearchButton.makeWhite(this);
        tabNewsButton.setClickable(true);
        tabNewsButton.makeWhite(this);
    }

    @Override
    public void openNewFragment(Integer number) {
        SignalsFragment signalsFragment = new SignalsFragment();
        Bundle args = new Bundle();
        args.putInt("number", number);
        signalsFragment.setArguments(args);
        replaceFragment(signalsFragment, true, false);
    }

    @Override
    public void openNewFragmentWithString(Integer number, String param) {
        if (number >= 0 && number <= 2) {
            NewsesWebViewFragment newsesWebViewFragment = new NewsesWebViewFragment();
            Bundle args = new Bundle();
            args.putString("url", param);
            newsesWebViewFragment.setArguments(args);
            replaceFragment(newsesWebViewFragment, true, false);
        } else if (number == STOCK_DETAIL_TRIGGER) {
            StockDetailFragment stockDetailFragment = new StockDetailFragment();
            Bundle args = new Bundle();
            args.putString("stock_name", param);
            stockDetailFragment.setArguments(args);
            stockDetailFragment.getIndicatorsClicks().subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer type) {
                    IndicatorsFragment indicatorsFragment = new IndicatorsFragment();
                    Bundle args = new Bundle();
                    args.putInt("indicator_type", type);
                    indicatorsFragment.setArguments(args);
                    replaceFragment(indicatorsFragment, true, false);
                }
            });
            replaceFragment(stockDetailFragment, true, false);
        }
    }

    @Override
    public void openNewFragmentWithWeekCalendar(Integer number, Map<String, CalendarDTO> calendar) {
        SignalsFragment signalsFragment = new SignalsFragment();
        Bundle args = new Bundle();
        args.putInt("number", number);
        signalsFragment.setArguments(args);
        signalsFragment.setCalendar(calendar);
        replaceFragment(signalsFragment, true, false);
    }

}
