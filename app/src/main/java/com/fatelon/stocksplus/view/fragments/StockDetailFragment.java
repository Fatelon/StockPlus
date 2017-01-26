package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.CustomTitle;

/**
 * Created by Fatelon on 26.01.2017.
 */

public class StockDetailFragment extends BaseFragment {

    private CustomTitle stockDetailTitle;

    private CustomTextView stockName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        init(view);

        Bundle args = getArguments();
        String stock = args.getString("stock_name");
        if (stock != null) {
            stockName.setText(stock);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init(View view) {
        stockDetailTitle = (CustomTitle) view.findViewById(R.id.stock_detail_title);
        stockDetailTitle.setPressBackCallBack(context);
        stockName = (CustomTextView) view.findViewById(R.id.stock_name);
    }
}
