package com.fatelon.stocksplus.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.portfolio.GetPortfolioDTO;
import com.fatelon.stocksplus.model.dto.portfolio.PortfolioDTO;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.PortfolioItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class Portfolio extends BaseMenuFragment {

    private static String TAG = Portfolio.class.getSimpleName();

    private FrameLayout portfolioLoader;

    private PortfolioItem summaryItem;

    private Map<String, PortfolioDTO> allPortfolios;

    private ListView portfolioListView;
    private List<PortfolioDTO> portfolioData;
    private PortfolioListViewAdapter portfolioListViewAdapter;

    private CustomTextView pEditButton;

    private boolean portfolioEditMode = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        init(view);

        getUserPortfolios();

        return view;
    }

    private void init(View view) {

        portfolioLoader = (FrameLayout) view.findViewById(R.id.portfolio_loader);

        summaryItem = (PortfolioItem) view.findViewById(R.id.portfolio_summary_item);
        summaryItem.setSummary("", "", "");
        pEditButton = (CustomTextView) view.findViewById(R.id.portfolio_edit_button);
        pEditButton.setOnClickListener(v -> onClickEditButton());

        allPortfolios = new HashMap<String, PortfolioDTO>();

        portfolioData = new ArrayList<>();
        portfolioListViewAdapter = new PortfolioListViewAdapter(context, R.layout.portfolio_list_view_simple_item, portfolioData);
        portfolioListView = (ListView) view.findViewById(R.id.portfolio_main_list_view);
        portfolioListView.setAdapter(portfolioListViewAdapter);
    }

    private void onClickEditButton() {
        portfolioEditMode = !portfolioEditMode;
        if (portfolioEditMode) {
            pEditButton.setText(context.getResources().getString(R.string.tab_done));
        } else {
            pEditButton.setText(context.getResources().getString(R.string.tab_edit));
        }
        portfolioListViewAdapter.changeEdit();
    }

    private void getUserPortfolios() {
        portfolioLoader.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getUserPortfolio(PreferencesHelper.getUserId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<GetPortfolioDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                        portfolioLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(GetPortfolioDTO response) {
                        Log.d(TAG, "onNext - ");
                        allPortfolios.clear();
                        try {
                            Map<String, PortfolioDTO> m = response.getPortfolio();
                            if (m != null) {
                                for (Map.Entry<String, PortfolioDTO> entry : m.entrySet()) {
                                    portfolioData.add(entry.getValue());
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        portfolioListViewAdapter.notifyDataSetChanged();
                        portfolioLoader.setVisibility(View.GONE);
                    }
                });
    }

    public class PortfolioListViewAdapter extends ArrayAdapter<PortfolioDTO> {

        private Context context;

        private Integer resource;

        private List<PortfolioDTO> portfolioData;

        private boolean addMinus = false;

        private Integer deleteAppear = -1;
        private Integer deleteDisappear = -1;
        private Integer widthShift = 0;

        public PortfolioListViewAdapter(Context context, int resource, List<PortfolioDTO> data) {
            super(context, resource);
            this.context = context;
            this.resource = resource;
            this.portfolioData = data;
        }

        @Override
        public int getCount() {
            return portfolioData.size();
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
            PortfolioItem portfolioItem;

            ViewHolder(View view) {
                portfolioItem = (PortfolioItem) view.findViewById(R.id.portfolio_simple_item);
            }
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            View rowView = view;
            final ViewHolder viewHolder;
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(resource, null, true);
                viewHolder = new ViewHolder(rowView);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            viewHolder.portfolioItem.setMinus(addMinus);
            viewHolder.portfolioItem.setPortfolioView(portfolioData.get(position));

            return rowView;
        }
    }
}
