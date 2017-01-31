package com.fatelon.stocksplus.view.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.search.StockSearchDTO;
import com.fatelon.stocksplus.model.dto.search.TickerDTO;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;
import com.fatelon.stocksplus.view.callbacks.SimpleDialogCallback;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static com.fatelon.stocksplus.view.dialogs.SimpleDialog.showSimpleDialogWithCallback;

/**
 * Created by User on 21.01.2017.
 */

public class Search extends BaseMenuFragment {

    private static String TAG = Search.class.getSimpleName();

    private final PublishSubject<String> onClickSearchItem = PublishSubject.create();

    private SearchView mSearchView;

    private FrameLayout loadingIndicator;

    private SearchView.OnQueryTextListener searchTextListener;

    private ListView searchListView;

    private List<TickerDTO> searchData = new ArrayList<TickerDTO>();

    private SearchListViewAdapter searchListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        init(view);

        return view;
    }

    private void init (View view) {

        searchListView = (ListView) view.findViewById(R.id.search_answer_list_view);
        searchListViewAdapter = new SearchListViewAdapter(context, R.layout.search_list_view_item, searchData);
        searchListView.setAdapter(searchListViewAdapter);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickSearchItem.onNext(searchData.get(position).getTicker());
            }
        });

        loadingIndicator = (FrameLayout) view.findViewById(R.id.search_loading_indicator);
        mSearchView = (SearchView) view.findViewById(R.id.my_search_view);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, newText);

                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, query);

                if (mSearchView != null) {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                }
                getSearch(query);
                return true;
            }
        };
        mSearchView.setOnQueryTextListener(searchTextListener);
    }

    public Observable<String> getSearchClicks(){
        return onClickSearchItem.asObservable();
    }

    private void getSearch(String query) {
        loadingIndicator.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getStockSearch(query).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<StockSearchDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                        String title = context.getResources().getString(R.string.forgot_pass_error_title);
                        String message = context.getResources().getString(R.string.forgot_pass_error_message);
                        showSimpleDialogWithCallback(context, title, message, new SimpleDialogCallback() {
                            @Override
                            public void simpleDialogReaction() {
                                ((PressBackCallBack)context).onPressBack();
                            }
                        });
                        loadingIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(StockSearchDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            loadingIndicator.setVisibility(View.GONE);
                            searchData.clear();
                            searchData.addAll(response.getSearchResult().getTickers());
                            searchListViewAdapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    public class SearchListViewAdapter extends ArrayAdapter<TickerDTO> {

        private final Context context;

        private List<TickerDTO> searchData;

        private final Integer resource;

        public SearchListViewAdapter (Context context, int resource, List<TickerDTO> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.searchData = objects;
        }

        @Override
        public int getCount() {
            return searchData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            CustomTextView ticker;
            CustomTextView companyName;
            CustomTextView price;
            CustomTextView changeNum;
            CustomTextView changePercent;
            ImageView changeImage;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View rowView = view;
            final ViewHolder viewHolder;
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(resource, null, true);
                viewHolder = new ViewHolder();
                viewHolder.ticker = (CustomTextView) rowView.findViewById(R.id.search_list_view_item_ticker);
                viewHolder.companyName = (CustomTextView) rowView.findViewById(R.id.search_list_view_item_company_name);
                viewHolder.price = (CustomTextView) rowView.findViewById(R.id.search_list_view_item_price);
                viewHolder.changeNum = (CustomTextView) rowView.findViewById(R.id.search_list_view_item_change_num);
                viewHolder.changePercent = (CustomTextView) rowView.findViewById(R.id.search_list_view_item_change_percent);
                viewHolder.changeImage = (ImageView) rowView.findViewById(R.id.search_list_view_item_change_image);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            String chNum = searchData.get(position).getChageNum();
            viewHolder.ticker.setText(searchData.get(position).getTicker());
            viewHolder.companyName.setText(searchData.get(position).getCompanyName());
            viewHolder.price.setText(searchData.get(position).getPrice());
            viewHolder.changeNum.setText(chNum);
            viewHolder.changePercent.setText(searchData.get(position).getChangePercent());
            if (chNum != null && chNum.length() > 0 && chNum.charAt(0) == '-') {
                viewHolder.changeImage.setImageResource(R.mipmap.down_search);
                viewHolder.changeNum.setTextColor(ContextCompat.getColor(context, R.color.red));
                viewHolder.changePercent.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                viewHolder.changeImage.setImageResource(R.mipmap.up_search);
                viewHolder.changeNum.setTextColor(ContextCompat.getColor(context, R.color.green));
                viewHolder.changePercent.setTextColor(ContextCompat.getColor(context, R.color.green));
            }
            return rowView;
        }
    }

}
