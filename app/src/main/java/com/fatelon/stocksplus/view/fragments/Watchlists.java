package com.fatelon.stocksplus.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.watchlists.AddWatchlistDTO;
import com.fatelon.stocksplus.model.dto.watchlists.DeleteStockWLDTO;
import com.fatelon.stocksplus.model.dto.watchlists.GetWatchListsDTO;
import com.fatelon.stocksplus.model.dto.watchlists.OneTickerDTO;
import com.fatelon.stocksplus.model.dto.watchlists.WatchlistsDTO;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static com.fatelon.stocksplus.Constants.TITLE_TEXT_MAX_LENGTH;
import static com.fatelon.stocksplus.view.dialogs.SimpleDialog.showAddStockWatchListDialogWithCallback;
import static com.fatelon.stocksplus.view.dialogs.SimpleDialog.showAddWatchListDialogWithCallback;
import static com.fatelon.stocksplus.view.dialogs.SimpleDialog.showSimpleDialog;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class Watchlists extends BaseMenuFragment {

    private static String TAG = Watchlists.class.getSimpleName();

    private final PublishSubject<String> onClickStock = PublishSubject.create();
    private final PublishSubject<String> onClickWLSelectButton = PublishSubject.create();

    private  Map<String, WatchlistsDTO> watchlists = new HashMap<String, WatchlistsDTO>();

    private  List<OneTickerDTO> tickersData = new ArrayList<OneTickerDTO>();

    private OneTickerListViewAdapter oneTickerListViewAdapter;

    private ListView tickersListView;

    private ImageView addNewWatchListButton;
    private ImageView selectList;
    private ImageView addStock;

    private CustomTextView editButton;
    private CustomTextView watchListsTitle;

    private FrameLayout mainLoader;

    private boolean editMode = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlists, container, false);

        init(view);

        getUserWatchLists();

        return view;
    }

    public Observable<String> getNewsClicks(){
        return onClickStock.asObservable();
    }

    public Observable<String> getWLSelectButtonClicks(){
        return onClickWLSelectButton.asObservable();
    }

    private void init(View view) {
        addNewWatchListButton = (ImageView) view.findViewById(R.id.add_new_watchlist);
        addNewWatchListButton.setOnClickListener(v->showAddWatchListDialogWithCallback(context, p -> addNewWatchListOnServer(p[0])));
        selectList = (ImageView) view.findViewById(R.id.select_list_watchlist);
        selectList.setOnClickListener(v -> {onClickWLSelectButton.onNext("");});
        addStock = (ImageView) view.findViewById(R.id.add_stock_watchlist);
        addStock.setOnClickListener(v->showAddStockWatchListDialogWithCallback(context, p -> addNewStockOnServer(p[0], PreferencesHelper.getCurrentWatchlistId(context))));
        mainLoader = (FrameLayout) view.findViewById(R.id.watch_lists_loader);
        editButton = (CustomTextView) view.findViewById(R.id.edit_watch_list_button);
        editButton.setOnClickListener(v -> onClickEditButton());
        watchListsTitle = (CustomTextView) view.findViewById(R.id.watch_lists_title);
        tickersListView = (ListView) view.findViewById(R.id.watch_lists_tickers_list_view);
        oneTickerListViewAdapter = new OneTickerListViewAdapter(context, R.layout.one_ticker_list_view_item, tickersData);
        tickersListView.setAdapter(oneTickerListViewAdapter);

    }

    private void onClickEditButton() {
        editMode = !editMode;
        if (editMode) {
            editButton.setText(context.getResources().getString(R.string.tab_done));
        } else {
            editButton.setText(context.getResources().getString(R.string.tab_edit));
        }
        oneTickerListViewAdapter.changeEdit();
    }

    private void setCurrentWatchlist() {
        try {
            String currentWatchlistId = PreferencesHelper.getCurrentWatchlistId(context);
            String name = "";
            if (watchlists.containsKey(currentWatchlistId)) {
                name = watchlists.get(currentWatchlistId).getName();
            } else if (!watchlists.isEmpty()) {
                name = watchlists.entrySet().iterator().next().getValue().getName();
                PreferencesHelper.storeCurrentWatchlistId(context, watchlists.entrySet().iterator().next().getValue().getId());
            } else {
                watchListsTitle.setText(context.getResources().getString(R.string.watchlists_no_lists));
                PreferencesHelper.storeCurrentWatchlistId(context, "");
                return;
            }
            Integer n = TITLE_TEXT_MAX_LENGTH;
            if (name.length() > n) {
                name = name.substring(0, n);
                name += "..";
            }
            watchListsTitle.setText(name);
            Map<String, OneTickerDTO> m = watchlists.get(PreferencesHelper.getCurrentWatchlistId(context)).getTickers();
            if (m != null) {
                for (Map.Entry<String, OneTickerDTO> entry : m.entrySet()) {
                    tickersData.add(entry.getValue());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addNewWatchListOnServer(String name) {
        oneTickerListViewAdapter.setAppear(-1);
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
                            getUserWatchLists();
//                            mainLoader.setVisibility(View.GONE);
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
                            tickersData.clear();
                            watchlists.clear();
                            oneTickerListViewAdapter.setAppear(-1);
                            oneTickerListViewAdapter.setAppear(-1);
                            if (response != null && response.getWatchlists() != null) {
                                watchlists.putAll(response.getWatchlists());
                            }
                            setCurrentWatchlist();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        oneTickerListViewAdapter.notifyDataSetChanged();
                        mainLoader.setVisibility(View.GONE);
                    }
                });
    }

    private void addNewStockOnServer(String ticker, String wlId) {
        if (wlId.equals("")) {
            showSimpleDialog(context,
                    context.getResources().getString(R.string.watch_lists_no_watch_list_dialog_title),
                    context.getResources().getString(R.string.watch_lists_no_watch_list_dialog_message));
        } else {
            mainLoader.setVisibility(View.VISIBLE);
            Map<String, String> m = new HashMap<>();
            m.put(context.getResources().getString(R.string.watch_lists_ticker), ticker.toUpperCase());
            m.put(context.getResources().getString(R.string.watch_lists_wl_id), wlId);
            ApiInterface apiInterface = ApiModule.getApiInterface();
            apiInterface.postAddStockFromWatchList(m, PreferencesHelper.getUserSessionId(context)).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Subscriber<DeleteStockWLDTO>() {
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
                        public void onNext(DeleteStockWLDTO response) {
                            Log.d(TAG, "onNext - ");
                            tickersData.clear();
                            watchlists.clear();
                            oneTickerListViewAdapter.setAppear(-1);
                            oneTickerListViewAdapter.setAppear(-1);

                            oneTickerListViewAdapter.notifyDataSetChanged();
                            try {
                                getUserWatchLists();
//                            mainLoader.setVisibility(View.GONE);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void deleteStock(String tickerId, String wlId) {
        mainLoader.setVisibility(View.VISIBLE);
        Map<String, String> m = new HashMap<>();
        m.put(context.getResources().getString(R.string.watch_lists_ticker_id), tickerId);
        m.put(context.getResources().getString(R.string.watch_lists_wl_id), wlId);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.postDeleteStockFromWatchList(m, PreferencesHelper.getUserSessionId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<DeleteStockWLDTO>() {
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
                    public void onNext(DeleteStockWLDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            getUserWatchLists();
//                            mainLoader.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    public class OneTickerListViewAdapter extends ArrayAdapter<OneTickerDTO> {

        private final Context context;

        private float dpHeight;
        private float dpWidth;

        private List<OneTickerDTO> tickersData;

        private final Integer resource;

        private boolean addMinus = false;

        private Integer deleteAppear = -1;
        private Integer deleteDisappear = -1;
        private Integer widthShift = 0;

        public OneTickerListViewAdapter (Context context, int resource, List<OneTickerDTO> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.tickersData = objects;
            getScreenSize(context);
        }

        @Override
        public int getCount() {
            return tickersData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void changeEdit() {
            addMinus = !addMinus;
            setAppear(-1);
            notifyDataSetChanged();
        }

        public String getTicker(int position) {
            String ticker = "";
            try {
                ticker = tickersData.get(position).getTicker();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return ticker;
        }

        public void setAppear(Integer position) {
            deleteDisappear = deleteAppear;
            if (deleteAppear != -1) {
                deleteAppear = -1;
            } else {
                deleteAppear = position;
            }
            notifyDataSetChanged();
        }

        public class ViewHolder {

            RelativeLayout mainContainer;
            ImageView minus;
            CustomTextView tickerText;
            CustomTextView priceText;
            CustomTextView changePercentText;
            CustomTextView delete;
        }


        private void getScreenSize(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            dpHeight = displayMetrics.heightPixels;
            dpWidth = displayMetrics.widthPixels;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            View rowView = view;
            final ViewHolder viewHolder;
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(resource, null, true);
                viewHolder = new ViewHolder();
                rowView.setTag(viewHolder);
                viewHolder.mainContainer = (RelativeLayout) rowView.findViewById(R.id.watch_list_one_ticker_l_v_main_cont);
                viewHolder.mainContainer.setTag(position);
                viewHolder.mainContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addMinus) {
                            setAppear(-1);
                        } else {
                            Integer pos = (Integer)v.getTag();
                            onClickStock.onNext(oneTickerListViewAdapter.getTicker(pos));
                        }
                    }
                });
                viewHolder.mainContainer.getLayoutParams().width = (int) dpWidth;
                viewHolder.minus = (ImageView) rowView.findViewById(R.id.watch_list_one_ticker_l_v_minus);
                viewHolder.minus.setTag(position);
                viewHolder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAppear((Integer)v.getTag());
                    }
                });
                viewHolder.tickerText = (CustomTextView) rowView.findViewById(R.id.watch_list_one_ticker_l_v_ticker);
                viewHolder.priceText = (CustomTextView) rowView.findViewById(R.id.watch_list_one_ticker_l_v_price);
                viewHolder.changePercentText = (CustomTextView) rowView.findViewById(R.id.watch_list_one_ticker_l_v_change_percent);
                viewHolder.delete = (CustomTextView) rowView.findViewById(R.id.watch_list_one_ticker_l_v_delete);
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAppear(-1);
                        if (deleteDisappear != -1) {
                            String tickerId = tickersData.get(deleteDisappear).getId();
                            String wlId = tickersData.get(deleteDisappear).getWlId();
                            deleteStock(tickerId, wlId);
                        }
                    }
                });
                widthShift = viewHolder.delete.getLayoutParams().width;
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            String cPercent = tickersData.get(position).getChangePercent();
            viewHolder.tickerText.setText(tickersData.get(position).getTicker());
            viewHolder.priceText.setText(tickersData.get(position).getPrice());
            viewHolder.changePercentText.setText(cPercent);
            if (cPercent != null && cPercent.length() > 0 && cPercent.charAt(0) == '-') {
                viewHolder.changePercentText.setBackgroundResource(R.drawable.watch_list_ticker_red_style);
            } else {
                viewHolder.changePercentText.setBackgroundResource(R.drawable.watch_list_ticker_green_style);
            }
            if (addMinus) {
                viewHolder.minus.setVisibility(View.VISIBLE);
            } else {
                viewHolder.minus.setVisibility(View.GONE);
            }
            if (deleteAppear == position) {
                final View animView = viewHolder.mainContainer;
                final View delView = viewHolder.delete;
                Animation animation = new TranslateAnimation(0, -widthShift, 0, 0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                animation.setAnimationListener(new TranslateAnimation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        viewHolder.delete.setVisibility(View.VISIBLE);
//                        animView.layout(newleft,
//                                animView.getTop(),
//                                newleft + animView.getMeasuredWidth(),
//                                animView.getTop() + animView.getMeasuredHeight());

                    }
                });
                animView.startAnimation(animation);
            }
            if (deleteDisappear == position) {
                final View animView = viewHolder.mainContainer;
                viewHolder.delete.setVisibility(View.GONE);
                Animation animation = new TranslateAnimation(-widthShift, 0, 0, 0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                animation.setAnimationListener(new TranslateAnimation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
//                        animView.layout(animView.getLeft() + widthShift,
//                                animView.getTop(),
//                                animView.getLeft() + widthShift + animView.getMeasuredWidth(),
//                                animView.getTop() + animView.getMeasuredHeight());
                    }
                });
                animView.startAnimation(animation);
            }
            return rowView;
        }

        public class ResizeWidthAnimation extends Animation {
            private int mWidth;
            private int mStartWidth;
            private View mView;

            public ResizeWidthAnimation(View view, int width) {
                mView = view;
                mWidth = width;
                mStartWidth = view.getWidth();
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int newWidth = (int) (mWidth * interpolatedTime);
                mView.getLayoutParams().width = newWidth;
                mView.requestLayout();
            }

            @Override
            public void initialize(int width, int height, int parentWidth, int parentHeight) {
                super.initialize(width, height, parentWidth, parentHeight);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        }
    }

}